package com.example.checkoutportal.integration;

import com.example.checkoutportal.data.model.Item;
import com.example.checkoutportal.data.model.Order;
import com.example.checkoutportal.data.model.Product;
import com.example.checkoutportal.data.repository.ItemRepository;
import com.example.checkoutportal.data.repository.OrderRepository;
import com.example.checkoutportal.data.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.time.LocalDate;
import java.util.Collections;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
public class WebServiceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ItemRepository itemRepository;

    private Long createdOrderId;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
        orderRepository.deleteAll();
        productRepository.deleteAll();

        Product bread = new Product();
        bread.setType("Bread");
        bread.setPricePerItem(2.0);
        bread.setManufactureDate(LocalDate.now().minusDays(3));
        Product savedBread = productRepository.save(bread);

        Order order = new Order();

        Item breadItem = new Item();
        breadItem.setOrder(order);
        breadItem.setProduct(savedBread);
        breadItem.setQuantity(5);

        order.setItems(Collections.singletonList(breadItem));
        Order savedOrder = orderRepository.save(order);

        createdOrderId = savedOrder.getId();
    }

    @Test
    public void testGetOrderById() throws Exception {
        mockMvc.perform(get("/api/orders/"+createdOrderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(createdOrderId))
                .andExpect(jsonPath("$.items[0].quantity").value(5))
                .andExpect(jsonPath("$.items[0].totalPrice").value(6.00))
                .andExpect(jsonPath("$.totalPrice").value(6.00));
    }

    @Test
    public void testGetAllOrders() throws Exception {
        mockMvc.perform(get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderId").value(createdOrderId))
                .andExpect(jsonPath("$[0].items[0].quantity").value(5))
                .andExpect(jsonPath("$[0].items[0].totalPrice").value(6.00))
                .andExpect(jsonPath("$[0].totalPrice").value(6.00));
    }
}