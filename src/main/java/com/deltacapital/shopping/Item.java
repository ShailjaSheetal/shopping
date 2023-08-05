package com.deltacapital.shopping;


import lombok.Data;
import java.math.BigDecimal;

@Data
class Item {
    private String name;
    private Long quantity;
    private BigDecimal unitCost;
    private BigDecimal totalCost;

    Item(String name, Long quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
