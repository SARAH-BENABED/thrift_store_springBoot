package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.* ;
import org.springframework.web.server.ResponseStatusException;

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

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable long id) {

        if(!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found !") ;
        }
        productRepository.deleteById(id);
        return "Product deleted" ;
    }

    @PutMapping("/update/{id}")
    public Product updateProduct(@PathVariable long id, @RequestBody Product updatedProduct) {
        Product product = productRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found !"))  ;
        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setSize(updatedProduct.getSize());
        product.setImageURL(updatedProduct.getImageURL());
        return productRepository.save(product) ;
    }
}
