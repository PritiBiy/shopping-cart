package com.shoppingcart.shopping_cart.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shoppingcart.shopping_cart.domain.Cart;

import java.util.List;
import java.util.Objects;

public class CartResponse {
    Long id;

    private List<Long> productIds;

    private Double totalCost;

    public CartResponse(Long id) {
        this.id = id;
        this.productIds = List.of();
    }

    public CartResponse(@JsonProperty("id") Long id, @JsonProperty("productIds") List<Long> productIds,   @JsonProperty("totalCost") Double totalCost) {
        this.id = id;
        this.productIds = productIds;
        this.totalCost = totalCost;
    }

    public Long getId() {
        return id;
    }

    public static CartResponse from(Cart cart) {
        return new CartResponse(cart.getId(), cart.getProductIds(), cart.getTotalCost());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartResponse that = (CartResponse) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public List<Long> getProductIds() {
        return this.productIds;
    }

    public double getTotalCost() {
        return this.totalCost;
    }
}
