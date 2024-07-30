package com.example.checkoutportal.data.dto;

import com.example.checkoutportal.data.model.Product;

import java.time.LocalDate;

public class OrderItemDetailDTO {

    private final String productType;
    private final int quantity;
    private final double totalPrice;
    private final double pricePerItem;

    private final String origin;

    private final LocalDate manufactureDate;

    private final Integer weight;
    private String discount;


    public OrderItemDetailDTO(Product product, int quantity, double totalPrice, String discount) {
        this.productType = product.getType();
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.pricePerItem = product.getPricePerItem();
        this.origin = product.getOrigin();
        this.manufactureDate = product.getManufactureDate();
        this.weight = product.getWeight();
        this.discount = discount;
    }

    public String getProductType() {
        return productType;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getPricePerItem() {
        return pricePerItem;
    }

    public String getOrigin() {
        return origin;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public Integer getWeight() {
        return weight;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
