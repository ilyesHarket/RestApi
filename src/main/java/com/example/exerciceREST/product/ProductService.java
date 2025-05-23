package com.example.exerciceREST.product;

import com.example.exerciceREST.category.Category;
import com.example.exerciceREST.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
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
    private CategoryRepository categoryRepository;

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

    //supprimer un produit
    public void deleteProduct(Integer id){
        productRepository.deleteById(id);
    }

    //get product by category
    public List<Product> getProductsByCategoryId(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
        return productRepository.findByCategory(category);
    }
}
