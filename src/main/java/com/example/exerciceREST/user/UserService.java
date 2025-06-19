package com.example.exerciceREST.user;

import com.example.exerciceREST.product.Product;
import com.example.exerciceREST.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@Service


public class UserService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    //autowired for dependency injection
    @Autowired
    //constructeur ?
    public UserService(UserRepository userRepository, ProductRepository productRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
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
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
//
//        user.getProducts().add(product); // Si User a une collection de produits
//        userRepository.save(user);
//
//        return user;
        return null;
    }

    //supprimer un utilisateur
    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }

    //register un nouvel utilisateur
    public ResponseEntity<Object> register(User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        // Hash the password and set role
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");

        userRepository.save(user);

        // Generate token and return user info (same as login)
        final String token = jwtUtil.generateToken(user);
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("username", user.getUsername());
        response.put("role", user.getRole());
        response.put("email", user.getEmail());
        response.put("name", user.getName());
        response.put("userId", user.getId());

        return ResponseEntity.status(201).body(response);
    }

    //login un utilisateur
    public ResponseEntity<Object> login(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        final String token = jwtUtil.generateToken(user);
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("username", user.getUsername());
        response.put("role", user.getRole());
        response.put("email", user.getEmail());
        response.put("name", user.getName());

        return ResponseEntity.ok(response);
    }

    //create admin user
    public ResponseEntity<Object> createAdmin(String username, String password, String email) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        User admin = new User();
        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRole("ADMIN");
        admin.setEmail(email);

        userRepository.save(admin);
        return ResponseEntity.status(201).body("Admin registered successfully");
    }
}
