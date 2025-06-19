package com.example.exerciceREST.cart;

import java.util.List;

public interface CartService {
    void addToCart(CartItem item, Integer userId);
    List<CartItem> getCart(Integer userId);
    void updateCartItem(CartItem item, Integer userId);
    void removeFromCart(Integer productId, Integer userId);
    double getCartTotal(Integer userId);
}
