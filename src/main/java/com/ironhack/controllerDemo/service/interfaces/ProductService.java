package com.ironhack.controllerDemo.service.interfaces;

import com.ironhack.controllerDemo.controller.dto.ProductPriceDTO;

import java.math.BigDecimal;

public interface ProductService {
    void updatePrice(Long id, BigDecimal price);
}
