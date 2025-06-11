package com.example.exerciceREST.cart;

import java.util.List;

public interface CartService {
    void addToCart(CartItem item);
    List<CartItem> getCart();
    void updateCartItem(CartItem item);
    void removeFromCart(Integer productId);
    double getCartTotal();
}
