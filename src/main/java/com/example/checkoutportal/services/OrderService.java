package com.example.checkoutportal.services;


import com.example.checkoutportal.data.dto.OrderItemDetailDTO;
import com.example.checkoutportal.data.dto.OrderResponseDTO;
import com.example.checkoutportal.data.model.Item;
import com.example.checkoutportal.data.model.Order;
import com.example.checkoutportal.data.model.Product;
import com.example.checkoutportal.data.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PromotionService promotionService;

    public OrderResponseDTO getOrderDetailsByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        List<OrderItemDetailDTO> itemDetails = new ArrayList<>();
        double totalPrice = 0;

        for (Item item : order.getItems()) {
            String discount = "None";
            Product product = item.getProduct();
            double itemTotalPrice = switch (product.getType()) {
                case "Bread" -> {
                    double price = promotionService.calculateBreadPrice(product, item.getQuantity());
                    discount = promotionService.getDiscountAppliedOnBread();
                    yield price;
                }
                case "Vegetable" -> {
                    double price = promotionService.calculateVegetablePrice(product, item.getQuantity());
                    discount = promotionService.getDiscountAppliedOnVegetable();
                    yield price;
                }
                case "Beer" -> {
                    double price = promotionService.calculateBeerPrice(product, item.getQuantity());
                    discount = promotionService.getDiscountAppliedOnBeer();
                    yield price;
                }
                default -> 0;
            };

            totalPrice += itemTotalPrice ;

            itemDetails.add(
                    new OrderItemDetailDTO(
                            product,
                            item.getQuantity(),
                            itemTotalPrice,
                            discount
                    )
            );
        }
        totalPrice = BigDecimal.valueOf(totalPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
        OrderResponseDTO response = new OrderResponseDTO();
        response.setOrderId(orderId);
        response.setItems(itemDetails);
        response.setTotalPrice(totalPrice);

        return response;
    }

    public List<OrderResponseDTO> getAllOrders(){
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDTO> orderList = new ArrayList<>();

        orders.forEach(order -> orderList.add(getOrderDetailsByOrderId(order.getId())));
        return orderList;
    }
}