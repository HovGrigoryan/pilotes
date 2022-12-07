package com.shop.pilotes.enums;

import java.util.Arrays;

public enum NumberOfPilotes {

    MIN(5),
    MID(10),
    MAX(15);

    public final Integer value;

    NumberOfPilotes(Integer v) {
        value = v;
    }

    public static NumberOfPilotes from(int number) {
        return Arrays.stream(NumberOfPilotes.values()).filter(numberOfPilotes -> numberOfPilotes.value == number)
                .findFirst().orElseThrow();
    }
}
