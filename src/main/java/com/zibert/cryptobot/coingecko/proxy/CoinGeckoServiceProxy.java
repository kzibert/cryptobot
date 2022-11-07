package com.zibert.cryptobot.coingecko.proxy;

import com.zibert.cryptobot.coingecko.model.RatesResponse;
import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${coingecko-service.name}", url = "${coingecko-service.url}")
public interface CoinGeckoServiceProxy {

    @GetMapping("/simple/price")
    RatesResponse getRates(@RequestParam String[] ids,
                              @RequestParam String[] vs_currencies) throws FeignException;

    @GetMapping("/simple/supported_vs_currencies")
    String[] getCurrencies() throws FeignException;
}
