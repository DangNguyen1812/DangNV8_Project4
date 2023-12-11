package com.example.demo;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootTest
public class UserControllerTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final CartRepository cartRepository = Mockito.mock(CartRepository.class);
    private final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);

    private UserController userController;

    @Before
    public void setUp() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", bCryptPasswordEncoder);
    }

    @Test
    public void findById() {
        User user = TestUtils.getUser();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        ResponseEntity<User> response = userController.findById(1L);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());

        User userResponse = response.getBody();
        assertNotNull(userResponse);
        assertEquals(userResponse.getUsername(), user.getUsername());
    }

    @Test
    public void createUser() {
        CreateUserRequest cur = new CreateUserRequest();
        cur.setUsername("DangNV8");
        cur.setPassword("123456789");
        cur.setConfirmPassword("123456789");
        ResponseEntity<User> response = userController.createUser(cur);

        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());

        User user = response.getBody();
        assertNotNull(user);
        assertEquals(user.getUsername(), cur.getUsername());
    }

}
