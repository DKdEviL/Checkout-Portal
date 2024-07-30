package com.example.checkoutportal.data.dto;

import java.util.List;

public class OrderResponseDTO {
    private Long orderId;
    private List<OrderItemDetailDTO> items;
    private double totalPrice;


    public void setItems(List<OrderItemDetailDTO> items) {
        this.items = items;
    }


    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public List<OrderItemDetailDTO> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
