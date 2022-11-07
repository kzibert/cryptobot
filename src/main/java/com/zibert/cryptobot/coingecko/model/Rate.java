package com.zibert.cryptobot.coingecko.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rate {

    private Map<String, BigDecimal> rates = new LinkedHashMap<>();

    @JsonAnySetter
    public void setRates(String currency, BigDecimal rate) {
        rates.put(currency, rate);
    }
}
