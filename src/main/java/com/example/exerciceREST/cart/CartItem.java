package com.example.exerciceREST.cart;

import jakarta.persistence.*;


@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ou Integer si tu veux, mais l'id du panier peut rester Long
    private Integer productId; // <-- Ici, Integer pour matcher Product
    private String productName;
    private double price;
    private int quantity;
    private Integer userId;

    // Constructeurs, getters et setters
    public CartItem() {}
    public CartItem(Integer productId, String productName, double price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
}
