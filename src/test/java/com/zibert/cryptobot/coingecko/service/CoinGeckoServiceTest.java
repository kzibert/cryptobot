package com.zibert.cryptobot.coingecko.service;


import com.zibert.cryptobot.coingecko.model.RatesResponse;
import com.zibert.cryptobot.coingecko.proxy.CoinGeckoServiceProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CoinGeckoServiceTest {

    @InjectMocks
    private CoinGeckoService coinGeckoService;

    @Mock
    private CoinGeckoServiceProxy coinGeckoServiceProxy;

    @Test
    void Should_Call_CoinGeckoServiceProxy() {
        String[] ids = new String[]{"bitcoin"};
        String[] currencies = new String[]{"usd"};
        when(coinGeckoService.getRates(ids, currencies)).thenReturn(new RatesResponse());

        coinGeckoServiceProxy.getRates(ids, currencies);

        verify(coinGeckoServiceProxy).getRates(ids, currencies);
    }
}
