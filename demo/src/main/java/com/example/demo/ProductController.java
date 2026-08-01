package com.example.demo;

import org.springframework.web.bind.annotation.* ;
import java.util.List ;

@RestController
@RequestMapping("/products")
@CrossOrigin

public class ProductController {

    private final ProductRepository productRepository ;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository ;
    }

    @GetMapping("/get")
    public List<Product> getProducts() {
        return productRepository.findAll() ;
    }

    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product) ;
    }
}
