package com.zibert.cryptobot.coingecko.controller;

import com.zibert.cryptobot.coingecko.model.RatesResponse;
import com.zibert.cryptobot.coingecko.service.CoinGeckoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("rates")
public class CoinGeckoController {

    private final CoinGeckoService coinGeckoService;

    @GetMapping
    RatesResponse getRates(@RequestParam String[] ids,
                              @RequestParam String[] currencies) {
        return coinGeckoService.getRates(ids, currencies);
    }

    @GetMapping("currencies")
    String[] getCurrencies() {
        return coinGeckoService.printCurrencies();
    }
}
