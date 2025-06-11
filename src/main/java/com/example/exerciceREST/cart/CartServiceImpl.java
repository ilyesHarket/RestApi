package com.example.exerciceREST.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.exerciceREST.product.ProductRepository;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void addToCart(CartItem item) {
        if (!productRepository.existsById(item.getProductId())) {
            throw new RuntimeException("Produit non trouvé");
        }
        // Optionnel : met à jour le nom et le prix depuis la base
        productRepository.findById(item.getProductId()).ifPresent(product -> {
            item.setProductName(product.getName());
            item.setPrice(product.getPrice());
        });
        cartItemRepository.save(item);
    }

    @Override
    public List<CartItem> getCart() {
        return cartItemRepository.findAll();
    }

    @Override
    public void updateCartItem(CartItem item) {
        if (!productRepository.existsById(item.getProductId())) {
            throw new RuntimeException("Produit non trouvé");
        }
        List<CartItem> items = cartItemRepository.findByProductId(item.getProductId());
        if (!items.isEmpty()) {
            CartItem existingItem = items.get(0);
            existingItem.setQuantity(item.getQuantity());
            cartItemRepository.save(existingItem);
        }
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
