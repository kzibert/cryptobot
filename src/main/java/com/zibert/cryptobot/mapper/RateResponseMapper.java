package com.zibert.cryptobot.mapper;

import com.zibert.cryptobot.coingecko.model.Rate;
import com.zibert.cryptobot.coingecko.model.RatesResponse;
import com.zibert.cryptobot.dto.TelegramCoinDTO;
import com.zibert.cryptobot.exception.RatesNotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class RateResponseMapper {

    public TelegramCoinDTO mapToSingleRate(RatesResponse ratesResponse) {
        String cryptoCurrency;
        String baseCurrency;
        BigDecimal price;
        Map<String, Rate> coins = ratesResponse.getCoins();
        if (!coins.isEmpty()) {
            Map.Entry<String, Rate> coin = coins.entrySet().iterator().next();
            cryptoCurrency = coin.getKey();
            Map.Entry<String, BigDecimal> rate = coin.getValue().getRates().entrySet().iterator().next();
            baseCurrency = rate.getKey();
            price = rate.getValue();
        } else {
            throw new RatesNotFoundException();
        }
        return TelegramCoinDTO.builder()
                .cryptocurrency(cryptoCurrency)
                .baseCurrency(baseCurrency)
                .price(price)
                .build();
    }
}
