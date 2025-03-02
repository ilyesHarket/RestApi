package com.example.exerciceREST.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")


public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    public ResponseEntity<Object> CreateProduct(@RequestBody Product product){
        return this.productService.newProduct(product);
    }

    @GetMapping("/")
    public List<Product> getAllProducts(){
        return this.productService.getProducts();
    }

    //get user by id
    @GetMapping("/{id}") // ? what is this id
    public ResponseEntity<Object> getProductById(@PathVariable Integer id){
        return this.productService.getProductById(id);
    }
}
