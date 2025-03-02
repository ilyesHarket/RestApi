package com.example.exerciceREST.category;

import com.example.exerciceREST.product.Product;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="category")

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}

