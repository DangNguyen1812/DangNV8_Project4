package com.example.demo;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.example.demo.controllers.OrderController;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class OrderControllerTest {
    private OrderController orderController;
    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private OrderRepository orderRepository = Mockito.mock(OrderRepository.class);

    @BeforeEach
    public void setUp() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);
    }

    @Test
    public void getOrdersForUser() {
        Mockito.when(userRepository.findByUsername("Fish")).thenReturn(TestUtils.getUser());
        Mockito.when(orderRepository.findByUser(TestUtils.getUser()))
                .thenReturn(Collections.singletonList(TestUtils.getUserOrder()));

        ResponseEntity<List<UserOrder>> responseEntity = orderController.getOrdersForUser("Fish");
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    public void submit() {
        Mockito.when(userRepository.findByUsername("Fish")).thenReturn(TestUtils.getUserByCart());
        Mockito.when(orderRepository.save(TestUtils.getUserOrder()))
                .thenReturn(TestUtils.getUserOrder());

        ResponseEntity<UserOrder> responseEntity = orderController.submit("Fish");
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
    }
}
