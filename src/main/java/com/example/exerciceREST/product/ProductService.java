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
import java.util.stream.Collectors;

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
    public ResponseEntity<Object> newProduct(Product product) {
        try {
            // If category is provided with an ID, fetch the actual category entity
            if (product.getCategory() != null && product.getCategory().getId() != null) {
                Optional<Category> categoryOpt = categoryRepository.findById(product.getCategory().getId());
                if (categoryOpt.isPresent()) {
                    product.setCategory(categoryOpt.get());
                } else {
                    return ResponseEntity.badRequest().body("Category not found with ID: " + product.getCategory().getId());
                }
            } else {
                return ResponseEntity.badRequest().body("Category ID is required");
            }
            
            Product savedProduct = productRepository.save(product);
            return new ResponseEntity<>(new ProductDTO(savedProduct), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating product: " + e.getMessage());
        }
    }

    //récupérer tous les produits
    public List<ProductDTO> getProducts(){
        return this.productRepository.findAll().stream()
            .map(ProductDTO::new)
            .collect(Collectors.toList());
    }

    //récupérer un produit par son id
    public ResponseEntity<Object> getProductById(Integer id){
        Optional<Product> productOptional = productRepository.findById(id); //optional : objet qui peut être null ou non null
        if(!productOptional.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ProductDTO(productOptional.get()));
    }

    //supprimer un produit
    public void deleteProduct(Integer id){
        productRepository.deleteById(id);
    }

    //get product by category
    public List<ProductDTO> getProductsByCategoryId(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
        return productRepository.findByCategory(category).stream()
            .map(ProductDTO::new)
            .collect(Collectors.toList());
    }

    public ResponseEntity<Object> updateProduct(Integer id, Product updatedProduct) {
        try {
            Optional<Product> existingProductOpt = productRepository.findById(id);
            if (!existingProductOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Product existingProduct = existingProductOpt.get();

            // Update basic fields
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPrice(updatedProduct.getPrice());

            // Handle category update
            if (updatedProduct.getCategory() != null && updatedProduct.getCategory().getId() != null) {
                Optional<Category> categoryOpt = categoryRepository.findById(updatedProduct.getCategory().getId());
                if (categoryOpt.isPresent()) {
                    existingProduct.setCategory(categoryOpt.get());
                } else {
                    return ResponseEntity.badRequest().body("Category not found with ID: " + updatedProduct.getCategory().getId());
                }
            } else {
                return ResponseEntity.badRequest().body("Category ID is required");
            }

            Product savedProduct = productRepository.save(existingProduct);
            return ResponseEntity.ok(new ProductDTO(savedProduct));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating product: " + e.getMessage());
        }
    }
}
