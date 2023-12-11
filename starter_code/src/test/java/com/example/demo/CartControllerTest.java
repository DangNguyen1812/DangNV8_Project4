package com.example.demo;

import java.util.Optional;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.example.demo.controllers.CartController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import com.example.demo.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@SpringBootTest
public class CartControllerTest {
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final CartRepository cartRepository = Mockito.mock(CartRepository.class);
    private final ItemRepository itemRepository = Mockito.mock(ItemRepository.class);

    private CartController cartController;

    @BeforeEach
    public void setUp() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
    }

    @Test
    public void addToCart() {

        Mockito.when(userRepository.findByUsername("DangNV8")).thenReturn(TestUtils.getUser());
        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(TestUtils.getItem()));

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("DangNV8");
        modifyCartRequest.setQuantity(1);
        modifyCartRequest.setItemId(1L);

        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }
}
