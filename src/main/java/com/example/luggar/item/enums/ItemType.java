package com.example.luggar.item.enums;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum ItemType {
    BAG(new BigDecimal("0.5")),
    SUITCASE(new BigDecimal("1.0")),
    ODD_SIZED(new BigDecimal("1.5"));

    private final BigDecimal coefficient;

    ItemType(BigDecimal coefficient) {
        this.coefficient = coefficient;
    }

}
