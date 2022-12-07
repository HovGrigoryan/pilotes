package com.shop.pilotes.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Getter
@Component
public class GlobalConfig {

    @Value("${app.pilotes.single-price}")
    private BigDecimal singlePrice;

}
