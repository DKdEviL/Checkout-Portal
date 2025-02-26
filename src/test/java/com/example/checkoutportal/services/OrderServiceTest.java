package com.example.checkoutportal.services;


import com.example.checkoutportal.data.dto.OrderResponseDTO;
import com.example.checkoutportal.data.model.Item;
import com.example.checkoutportal.data.model.Order;
import com.example.checkoutportal.data.model.Product;
import com.example.checkoutportal.data.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PromotionService promotionService;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testGetOrderDetailsById() {
        Order order = new Order();
        order.setId(1L);

        Product bread = new Product();
        bread.setId(1L);
        bread.setType("Bread");
        bread.setPricePerItem(2.3);
        bread.setManufactureDate(LocalDate.now().minusDays(4));

        Item breadItem = new Item();
        breadItem.setOrder(order);
        breadItem.setProduct(bread);
        breadItem.setQuantity(5);

        order.setItems(List.of(breadItem));

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        when(promotionService.calculateBreadPrice(bread, 5)).thenReturn(6.9);

        OrderResponseDTO orderResponse = orderService.getOrderDetailsByOrderId(1L);

        assertEquals(1L, orderResponse.getOrderId());
        assertEquals(1, orderResponse.getItems().size());
        assertEquals(6.9, orderResponse.getTotalPrice());
        assertEquals(6.9, orderResponse.getItems().get(0).totalPrice());
    }

    @Test
    public void testGetOrderDetailsByOrderId_NoItems() {
        Order order = new Order();
        order.setId(1L);
        order.setItems(Collections.emptyList());

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        OrderResponseDTO orderResponse = orderService.getOrderDetailsByOrderId(1L);

        assertEquals(1L, orderResponse.getOrderId());
        assertEquals(0, orderResponse.getItems().size());
        assertEquals(0.0, orderResponse.getTotalPrice());
    }

    @Test
    public void testGetOrderDetailsByOrderId_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            orderService.getOrderDetailsByOrderId(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}
