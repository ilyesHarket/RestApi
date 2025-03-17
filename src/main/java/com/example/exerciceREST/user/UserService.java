package com.example.exerciceREST.user;

import com.example.exerciceREST.product.Product;
import com.example.exerciceREST.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service


public class UserService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    //autowired for dependency injection
    @Autowired
    //constructeur ?
    public UserService(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    //créer un nouvel utilisateur
    public ResponseEntity<Object> newUser(User user){
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //récupérer tous les utilisateurs
    public List<User> getUsers(){
        return this.userRepository.findAll();
        //select * from user
    }

    //récupérer un utilisateur par son id
    public ResponseEntity<Object> getUserById(Integer id){
        Optional<User> userOptional = userRepository.findById(id); //optional : objet qui peut être null ou non null
        if(!userOptional.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userOptional.get());
    }

    //assigner un produit à un utilisateur
    public User assignProduct(Integer userId, Integer productId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        user.getProducts().add(product); // Si User a une collection de produits
        userRepository.save(user);

        return user;
    }

    //supprimer un utilisateur
    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }
}
