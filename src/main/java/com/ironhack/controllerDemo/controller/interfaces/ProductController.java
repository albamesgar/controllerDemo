package com.ironhack.controllerDemo.controller.interfaces;

import com.ironhack.controllerDemo.controller.dto.ProductPriceDTO;
import com.ironhack.controllerDemo.enums.Category;
import com.ironhack.controllerDemo.enums.Department;
import com.ironhack.controllerDemo.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductController {
    Product findById(Long id);
    List<Product> findByCategoryAndDepartment(Optional<Category> category, Department department);
    Product store(Product product);
    void updatePrice(Long id, ProductPriceDTO productPriceDTO );
}
