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

    @RequestMapping(value = { "/orders/{orderId}", "/orders"}, method = RequestMethod.GET)
    public List<OrderResponseDTO> getOrder(@PathVariable(name = "orderId", required = false) Long orderId) {
        if(orderId != null) {
            return List.of(orderService.getOrderDetailsByOrderId(orderId));
        }else{
            return orderService.getAllOrders();
        }
    }

}
