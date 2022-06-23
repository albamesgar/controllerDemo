package com.ironhack.controllerDemo.service.impl;

import com.ironhack.controllerDemo.controller.dto.ProductPriceDTO;
import com.ironhack.controllerDemo.model.Product;
import com.ironhack.controllerDemo.repository.ProductRepository;
import com.ironhack.controllerDemo.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void updatePrice(Long id, BigDecimal price) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(!optionalProduct.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        optionalProduct.get().setPrice(price);
        productRepository.save(optionalProduct.get());

        //Alternative version to get the product
//        Product product = productRepository.findById(id).orElseThrow(() ->
//                new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
//        product.setPrice(price);
//        productRepository.save(product);
    }
}
