package com.example.checkoutportal.webservice;

import com.example.checkoutportal.data.dto.OrderResponseDTO;
import com.example.checkoutportal.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WebServiceController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = { "/orders"}, method = RequestMethod.GET)
    public ResponseEntity<List<OrderResponseDTO>> getOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @RequestMapping(value = { "/orders/{orderId}"}, method = RequestMethod.GET)
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable(name = "orderId") Long orderId) {
        try {
            return ResponseEntity.ok(orderService.getOrderDetailsByOrderId(orderId));
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.notFound().build();
            }
            throw e;
        }
    }

}
