package com.shoppingcart.shopping_cart.service;

import com.shoppingcart.shopping_cart.controller.dto.CartResponse;
import com.shoppingcart.shopping_cart.domain.Cart;
import com.shoppingcart.shopping_cart.domain.Product;
import com.shoppingcart.shopping_cart.repository.CartRepository;
import com.shoppingcart.shopping_cart.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartService = new CartService(cartRepository, productRepository);
    }

    @Test
    void shouldReturnCartWithValidCartId() {
        Mockito.when(cartRepository.findById(1L)).thenReturn(of(new Cart(1L)));

        CartResponse cartResponse = cartService.getBy(1L);

        assertEquals(new CartResponse(1L), cartResponse);
    }

    @Test
    void shouldThrowExceptionWithInvalidCartId() {
        CartNotFoundException exception = assertThrows(CartNotFoundException.class, () -> cartService.getBy(1L));
        assertEquals("Cart with id 1 not found", exception.getMessage());
    }


    @Test
    void shouldAddProductToCartProvidedValidCartId() {
        Mockito.when(cartRepository.findById(1L)).thenReturn(of(new Cart(1L)));

        cartService.addProduct(1L, asList(1L, 2L));

        Mockito.verify(cartRepository).findById(1L);
    }

    @Test
    void shouldThrowErrorOnAddProductProvidedInvalidCartId() {
        Mockito.when(cartRepository.findById(1L)).thenReturn(empty());

        CartNotFoundException exception = assertThrows(CartNotFoundException.class, () -> cartService.addProduct(1L, asList(1L, 2L)));

        assertEquals("Cart with id 1 not found", exception.getMessage());
    }

    @Test
    void shouldReturnCartResponseWithProductIdAndTotal() {
        Cart cart = new Cart(123L);
        cart.addProducts(asList(1L, 2L));

        Mockito.when(cartRepository.findById(123L)).thenReturn(of(cart));
        Product product1 = new Product(1L, "Product 1", 500.0);
        Product product2 = new Product(2L, "Product 2", 550.0);
        Mockito.when(productRepository.findAllById(asList(1L, 2L))).thenReturn(asList(
                product1, product2));

        CartResponse cartResponse = cartService.getBy(123L);

        assertEquals(new CartResponse(123L, cart.getProductIds(), cart.getTotalCost()), cartResponse);
        assertEquals(2, cartResponse.getProductIds().size());
        assertEquals(1050.0, cartResponse.getTotalCost(), 0.01);
    }
}