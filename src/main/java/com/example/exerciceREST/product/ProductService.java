package com.example.exerciceREST.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service

public class ProductService {
    private final ProductRepository productRepository;

    @Autowired

    //constructeur
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //créer un nouveau produit
    public ResponseEntity<Object> newProduct(Product product){
        productRepository.save(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    //récupérer tous les produits
    public List<Product> getProducts(){
        return this.productRepository.findAll();
        //select * from product
    }

    //récupérer un produit par son id
    public ResponseEntity<Object> getProductById(Integer id){
        Optional<Product> productOptional = productRepository.findById(id); //optional : objet qui peut être null ou non null
        if(!productOptional.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productOptional.get());
    }


}
