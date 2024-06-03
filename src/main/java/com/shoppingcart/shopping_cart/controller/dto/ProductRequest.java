package com.shoppingcart.shopping_cart.controller.dto;

import java.util.List;

public class ProductRequest {
    private List<Long> productIds;

    public ProductRequest() {
    }

    public ProductRequest(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}