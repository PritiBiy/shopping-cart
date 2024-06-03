package com.shoppingcart.shopping_cart.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingcart.shopping_cart.domain.Cart;
import com.shoppingcart.shopping_cart.domain.Product;
import com.shoppingcart.shopping_cart.repository.CartRepository;
import com.shoppingcart.shopping_cart.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {

    public static final long CART_ID = 345L;
    public static final long MOBILE_ID = 1L;
    public static final long CHARGER_ID = 2L;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;


    @Test
    public void shouldReturnEmptyCartContentWithSuccess() throws Exception {
        cartRepository.save(new Cart(CART_ID));

        mockMvc.perform(get("/cart/345"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":345}"));
    }


    @Test
    public void shouldAddProductsToTheCart() throws Exception {
        Product mobile = new Product(MOBILE_ID, "Mobile", 1000.0);
        Product charger = new Product(CHARGER_ID, "Charge", 50.0);
        productRepository.save(mobile);
        productRepository.save(charger);

        cartRepository.save(new Cart(CART_ID));

        Map<String, Object> requestBody = new HashMap<>();
        List<Long> productIds = Arrays.asList(MOBILE_ID, CHARGER_ID);
        requestBody.put("productIds", productIds);


        mockMvc.perform(post("/cart/" + CART_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(status().isOk());
    }


}