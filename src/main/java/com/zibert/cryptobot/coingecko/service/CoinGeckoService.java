package com.zibert.cryptobot.coingecko.service;

import com.zibert.cryptobot.coingecko.model.RatesResponse;
import com.zibert.cryptobot.coingecko.proxy.CoinGeckoServiceProxy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CoinGeckoService {

    private final CoinGeckoServiceProxy coinGeckoServiceProxy;

    public RatesResponse getRates(String[] ids, String[] currencies) {
        return coinGeckoServiceProxy.getRates(ids, currencies);
    }

    public String[] printCurrencies() {
        return coinGeckoServiceProxy.getCurrencies();
    }
}
