package com.ironhack.controllerDemo.controller.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

public class ProductPriceDTO {
    @Digits(integer = 6, fraction = 2, message =
            "The price must have a maximum of 6 integer digits and 2 decimal digits")
    @DecimalMin(value = "0.0", inclusive = true, message = "The price can not be negative")
    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
