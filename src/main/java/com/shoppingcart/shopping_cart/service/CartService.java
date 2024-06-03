package com.shoppingcart.shopping_cart.service;

import com.shoppingcart.shopping_cart.controller.dto.CartResponse;
import com.shoppingcart.shopping_cart.domain.Cart;
import com.shoppingcart.shopping_cart.repository.CartRepository;
import com.shoppingcart.shopping_cart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public CartResponse getBy(Long cartId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        Cart cart = optionalCart.orElseThrow(() -> new CartNotFoundException("Cart with id " + cartId + " not found"));

        if (cart.isEmpty()) return new CartResponse(cart.getId(), List.of(), 0.0);
        return new CartResponse(cart.getId(),
                cart.getProductIds(),
                cart.totalCost(productRepository.findAllById(cart.getProductIds())));
    }

    public void addProduct(Long cartId, List<Long> productIds) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        Cart cart = optionalCart.orElseThrow(() -> new CartNotFoundException("Cart with id " + cartId + " not found"));
        cart.addProducts(productIds);
        cartRepository.save(cart);
    }
}
