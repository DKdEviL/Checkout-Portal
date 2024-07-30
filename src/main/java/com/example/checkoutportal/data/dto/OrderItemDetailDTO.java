package com.example.checkoutportal.data.dto;

import com.example.checkoutportal.data.model.Product;

import java.time.LocalDate;

public record OrderItemDetailDTO(
        String productType,
        double pricePerItem,
        String origin,
        LocalDate manufactureDate,
        Integer weight,
        String discount,
        int quantity,
        double totalPrice) {

    public OrderItemDetailDTO(Product product, int quantity, double totalPrice, String discount) {
        this(product.getType(),
                product.getPricePerItem(),
                product.getOrigin(),
                product.getManufactureDate(),
                product.getWeight(),
                discount,
                quantity,
                totalPrice);
    }
}
