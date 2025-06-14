package com.example.exerciceREST.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.exerciceREST.product.ProductRepository;
import java.util.List;
import java.util.Comparator;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void addToCart(CartItem item) {
        // Verify product exists in database
        if (!productRepository.existsById(item.getProductId())) {
            throw new RuntimeException("Produit non trouvé");
        }

        // Get product details
        productRepository.findById(item.getProductId()).ifPresent(product -> {
            item.setProductName(product.getName());
            item.setPrice(product.getPrice());
        });

        // Check if product already exists in cart
        List<CartItem> existingItems = cartItemRepository.findByProductId(item.getProductId());
        if (!existingItems.isEmpty()) {
            // Update quantity of existing item
            CartItem existingItem = existingItems.get(0);
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
            cartItemRepository.save(existingItem);
        } else {
            // Add new item to cart
            cartItemRepository.save(item);
        }
    }

    @Override
    public List<CartItem> getCart() {
        List<CartItem> items = cartItemRepository.findAll();
        items.sort(Comparator.comparing(CartItem::getProductName));
        return items;
    }

    @Override
    public void updateCartItem(CartItem item) {
        // Find the existing cart item
        List<CartItem> existingItems = cartItemRepository.findByProductId(item.getProductId());
        if (existingItems.isEmpty()) {
            throw new RuntimeException("Item not found in cart");
        }

        CartItem existingItem = existingItems.get(0);
        existingItem.setQuantity(item.getQuantity());
        cartItemRepository.save(existingItem);
    }

    @Override
    public void removeFromCart(Integer productId) {
        List<CartItem> items = cartItemRepository.findByProductId(productId);
        if (!items.isEmpty()) {
            cartItemRepository.delete(items.get(0));
        }
    }

    public double getCartTotal() {
        List<CartItem> items = cartItemRepository.findAll();
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}
