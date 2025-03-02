package com.example.exerciceREST.user;
import com.example.exerciceREST.product.Product;
import jakarta.persistence.*;

import java.util.List;

@Entity

@Table(name="userTable")
public class User {

    //getters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Product> getProducts() {
        return products;
    }

    //setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String email;


    // ??
    @OneToMany(mappedBy = "user")
    private List<Product> products;
}
