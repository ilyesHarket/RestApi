package com.example.exerciceREST.config;

import com.example.exerciceREST.category.Category;
import com.example.exerciceREST.category.CategoryRepository;
import com.example.exerciceREST.product.Product;
import com.example.exerciceREST.product.ProductRepository;
import com.example.exerciceREST.user.User;
import com.example.exerciceREST.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(CategoryRepository categoryRepository, ProductRepository productRepository, UserRepository userRepository) {
        return args -> {

            // // Clear existing data
            // System.out.println("Clearing existing data...");
            // productRepository.deleteAll();
            // categoryRepository.deleteAll();
            // System.out.println("Database cleared!");

            // Check if data already exists
            if (categoryRepository.count() > 0) {
                System.out.println("Database already seeded, skipping...");
                return;
            }

            System.out.println("Seeding database with initial data...");

            // Create Electronics category
            Category electronics = new Category();
            electronics.setName("Electronics");
            categoryRepository.save(electronics);

            // Create Electronics products
            Product laptop = new Product();
            laptop.setName("Laptop");
            laptop.setDescription("High-performance laptop with latest specs");
            laptop.setPrice(999.99);
            laptop.setCategory(electronics);
            productRepository.save(laptop);

            Product smartphone = new Product();
            smartphone.setName("Smartphone");
            smartphone.setDescription("Latest model smartphone with advanced features");
            smartphone.setPrice(699.99);
            smartphone.setCategory(electronics);
            productRepository.save(smartphone);

            // Create Clothing category
            Category clothing = new Category();
            clothing.setName("Clothing");
            categoryRepository.save(clothing);

            // Create Clothing products
            Product tshirt = new Product();
            tshirt.setName("T-Shirt");
            tshirt.setDescription("Comfortable cotton t-shirt");
            tshirt.setPrice(29.99);
            tshirt.setCategory(clothing);
            productRepository.save(tshirt);

            Product jeans = new Product();
            jeans.setName("Jeans");
            jeans.setDescription("Classic blue jeans");
            jeans.setPrice(59.99);
            jeans.setCategory(clothing);
            productRepository.save(jeans);

            // Create admin user
            if (userRepository.findByUsername("admin").isEmpty()) {
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword(passwordEncoder.encode("admin"));
                adminUser.setRole("ADMIN");
                adminUser.setEmail("admin@admin.com");
                adminUser.setName("Administrator");
                userRepository.save(adminUser);
                System.out.println("Admin user created: username=admin, password=admin");
            }

            System.out.println("Database seeding completed!");
        };
    }
} 