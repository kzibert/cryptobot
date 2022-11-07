package com.zibert.cryptobot.coingecko.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatesResponse {

    private Map<String, Rate> coins = new LinkedHashMap<>();

    @JsonAnySetter
    public void setRates(String coinName, Rate rate) {
        coins.put(coinName, rate);
    }
}
