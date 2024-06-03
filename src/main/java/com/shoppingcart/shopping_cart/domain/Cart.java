package com.shoppingcart.shopping_cart.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Cart {

    @Id
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> productIds;

    public Cart() {

    }

    public Cart(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void addProducts(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}
