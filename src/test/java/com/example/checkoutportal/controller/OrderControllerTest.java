package com.example.checkoutportal.controller;

import com.example.checkoutportal.controllers.OrderController;
import com.example.checkoutportal.data.dto.OrderResponseDTO;
import com.example.checkoutportal.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void testGetAllOrders() throws Exception {
        OrderResponseDTO order1 = new OrderResponseDTO();
        order1.setOrderId(1L);
        order1.setTotalPrice(22.50);
        OrderResponseDTO order2 = new OrderResponseDTO();
        order2.setOrderId(2L);
        order2.setTotalPrice(10.30);
        when(orderService.getAllOrders()).thenReturn(Arrays.asList(order1, order2));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(view().name("allOrders"))
                .andExpect(model().attributeExists("allOrders"))
                .andExpect(model().attribute("allOrders", Arrays.asList(order1, order2)));
    }

    @Test
    public void testGetOrderDetail() throws Exception {
        OrderResponseDTO order1 = new OrderResponseDTO();
        order1.setOrderId(1L);
        order1.setTotalPrice(22.50);
        when(orderService.getOrderDetailsByOrderId(1L)).thenReturn(order1);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("orderDetail"))
                .andExpect(model().attributeExists("order"))
                .andExpect(model().attribute("order", order1));
    }
}
