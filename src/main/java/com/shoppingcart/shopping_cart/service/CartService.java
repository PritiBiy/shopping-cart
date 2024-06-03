package com.shoppingcart.shopping_cart.service;

import com.shoppingcart.shopping_cart.controller.dto.CartResponse;
import com.shoppingcart.shopping_cart.domain.Cart;
import com.shoppingcart.shopping_cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public CartResponse getBy(Long id) {
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if (optionalCart.isPresent()) return CartResponse.from(optionalCart.get());
        throw new CartNotFoundException("Cart with id " + id + " not found");
    }

    public void addProduct(Long cartId, List<Long> productIds) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        Cart cart = optionalCart.orElseThrow(() -> new CartNotFoundException("Cart with id " + cartId + " not found"));
        cart.addProducts(productIds);
    }
}
