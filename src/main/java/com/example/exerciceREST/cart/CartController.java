package com.example.exerciceREST.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    private Integer getUserIdFromAuth(Authentication authentication) {
        if (authentication == null) return null;
        Object details = authentication.getDetails();
        if (details instanceof Map<?, ?> map && map.get("userId") instanceof Integer id) {
            return id;
        }
        return null;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartItem item, Authentication authentication) {
        Integer userId = getUserIdFromAuth(authentication);
        if (userId == null) return ResponseEntity.status(401).body("Unauthorized");
        try {
            cartService.addToCart(item, userId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(Authentication authentication) {
        Integer userId = getUserIdFromAuth(authentication);
        if (userId == null) return ResponseEntity.status(401).build();
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCartItem(@RequestBody CartItem item, Authentication authentication) {
        Integer userId = getUserIdFromAuth(authentication);
        if (userId == null) return ResponseEntity.status(401).body("Unauthorized");
        try {
            cartService.updateCartItem(item, userId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFromCart(@RequestParam Integer productId, Authentication authentication) {
        Integer userId = getUserIdFromAuth(authentication);
        if (userId == null) return ResponseEntity.status(401).body("Unauthorized");
        cartService.removeFromCart(productId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/total")
    public ResponseEntity<Double> getCartTotal(Authentication authentication) {
        Integer userId = getUserIdFromAuth(authentication);
        if (userId == null) return ResponseEntity.status(401).build();
        return ResponseEntity.ok(cartService.getCartTotal(userId));
    }
}
