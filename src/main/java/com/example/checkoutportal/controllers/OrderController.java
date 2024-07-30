package com.example.checkoutportal.controllers;

import com.example.checkoutportal.data.dto.OrderResponseDTO;
import com.example.checkoutportal.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String getAllOrders(Model model) {
        List<OrderResponseDTO> orders = this.orderService.getAllOrders();
        model.addAttribute("allOrders", orders);
        return "allOrders";
    }

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
    public String getOrderDetail(@PathVariable("id") String orderId, Model model) {
        Long order_id = Long.parseLong(orderId);
        OrderResponseDTO order = this.orderService.getOrderDetailsByOrderId(order_id);
        model.addAttribute("order", order);
        return "orderDetail";
    }

}