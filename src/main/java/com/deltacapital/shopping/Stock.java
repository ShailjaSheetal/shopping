package com.deltacapital.shopping;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.function.Function;

@NoArgsConstructor
@Data
public class Stock {

    private String name;

    private BigDecimal unitCost;

    private Function<Long,Long> discount;

    public Stock(String name, BigDecimal unitCost, Function<Long, Long> discount) {
        this.name = name;
        this.unitCost = unitCost;
        this.discount = discount;
    }
}
