package com.shoppingcart.shopping_cart.controller.dto;

import java.util.List;

public class AddProductToCartRequest {
    private List<Long> productIds;

    public AddProductToCartRequest() {
    }

    public AddProductToCartRequest(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}