package com.ironhack.controllerDemo.controller.impl;

import com.ironhack.controllerDemo.controller.dto.ProductPriceDTO;
import com.ironhack.controllerDemo.enums.Category;
import com.ironhack.controllerDemo.enums.Department;
import com.ironhack.controllerDemo.controller.interfaces.ProductController;
import com.ironhack.controllerDemo.model.Product;
import com.ironhack.controllerDemo.repository.ProductRepository;
import com.ironhack.controllerDemo.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductControllerImpl implements ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product findById(@PathVariable Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.get();
    }

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> findByCategoryAndDepartment(@RequestParam Optional<Category> category,
                                                     @RequestParam(defaultValue = "CLOTHING")
                                                     Department department) {
        List<Product> productList = new ArrayList<>();
        if (category.isPresent()) {
            productList = productRepository.findByCategoryAndDepartment(category, department);
        }else{
            productList = productRepository.findByDepartment(department);
        }
        return productList;
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Product store(@RequestBody @Valid Product product){
        return productRepository.save(product);
    }

    @PatchMapping("/products/{id}/price")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePrice(@PathVariable Long id, @RequestBody @Valid ProductPriceDTO productPriceDTO ){
        productService.updatePrice(id, productPriceDTO.getPrice());
    }
}
