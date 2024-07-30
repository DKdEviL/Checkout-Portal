package com.example.checkoutportal.webservice;

import com.example.checkoutportal.data.dto.OrderResponseDTO;
import com.example.checkoutportal.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WebServiceController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = { "/orders"}, method = RequestMethod.GET)
    public List<OrderResponseDTO> getOrders() {
        return orderService.getAllOrders();
    }

    @RequestMapping(value = { "/orders/{orderId}"}, method = RequestMethod.GET)
    public OrderResponseDTO getOrderById(@PathVariable(name = "orderId") Long orderId) {
        return orderService.getOrderDetailsByOrderId(orderId);
    }

}
