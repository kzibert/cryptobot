package com.zibert.cryptobot.telegram.bot;

import com.zibert.cryptobot.coingecko.model.RatesResponse;
import com.zibert.cryptobot.coingecko.service.CoinGeckoService;
import com.zibert.cryptobot.dto.TelegramCoinDTO;
import com.zibert.cryptobot.exception.RatesNotFoundException;
import com.zibert.cryptobot.mapper.RateResponseMapper;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Getter
@Component
public class TelegramBot extends TelegramLongPollingBot {

    public static final String BASE_CURRENCY = "usd";
    public static final String START_MSG = "Here we go: \n "
            + "Type 'get ***' to receive current rate \n";
    public static final String HELP_MSG = "Type 'get ***' to receive current rate \n"
            + "replace *** with the cryptocurrency you are looking for. \n\n";
    public static final String COIN_NOT_FOUND_MSG = "No such coin found! Try again: \n";
    public static final String UNKNOWN_COMMAND_MSG = "Unknown command, type '/help' for more info";
    public static final String GET = "get ";
    public static final String START_COMMAND = "/start";
    public static final String HELP_COMMAND = "/help";

    private Message requestMessage = new Message();
    private final SendMessage response = new SendMessage();

    private final String botUsername;
    private final String botToken;

    private final CoinGeckoService coinGeckoService;
    private final RateResponseMapper rateResponseMapper;

    public TelegramBot(
            @Value("${telegram-bot.name}") String botUsername,
            @Value("${telegram-bot.token}") String botToken,
            CoinGeckoService coinGeckoService,
            RateResponseMapper rateResponseMapper) {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.coinGeckoService = coinGeckoService;
        this.rateResponseMapper = rateResponseMapper;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update request) {
        requestMessage = request.getMessage();
        response.setChatId(requestMessage.getChatId().toString());

        String msg;

        if (request.hasMessage() && requestMessage.hasText()) {
            msg = requestMessage.getText();
            log.info("Working onUpdateReceived, request text[{}]", msg);
        } else {
            defaultMsg(response, UNKNOWN_COMMAND_MSG);
        }

        msg = requestMessage.getText();

        if (msg.equals(START_COMMAND)) {
            defaultMsg(response, START_MSG);
        } else if (msg.equals(HELP_COMMAND)) {
            onHelp(response);
        } else if (msg.startsWith(GET)) {
            String cryptocurrency = msg.substring(GET.length());
            RatesResponse ratesResponse = coinGeckoService.getRates(
                    new String[]{cryptocurrency},
                    new String[]{BASE_CURRENCY});
            try {
                TelegramCoinDTO telegramCoinDTO = rateResponseMapper.mapToSingleRate(ratesResponse);
                defaultMsg(response, String.format("%s is %s %s",
                        telegramCoinDTO.getCryptocurrency().toUpperCase(),
                        telegramCoinDTO.getPrice(),
                        telegramCoinDTO.getBaseCurrency().toUpperCase()));
            } catch (RatesNotFoundException ex) {
                defaultMsg(response, COIN_NOT_FOUND_MSG);
                return;
            }
        } else {
            defaultMsg(response, UNKNOWN_COMMAND_MSG);
        }

        log.info("Working, text[{}]", msg);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    private void onHelp(SendMessage response) throws TelegramApiException {
        defaultMsg(response, HELP_MSG);
    }

    private void defaultMsg(SendMessage response, String msg) throws TelegramApiException {
        response.setText(msg);
        execute(response);
    }
}
