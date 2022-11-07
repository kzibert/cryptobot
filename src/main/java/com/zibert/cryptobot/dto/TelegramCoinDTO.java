package com.zibert.cryptobot.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TelegramCoinDTO {

    private String cryptocurrency;
    private String baseCurrency;
    private BigDecimal price;
}
