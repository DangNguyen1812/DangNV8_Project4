package com.example.demo.utils;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

public class TestUtils {
    public static void injectObjects(Object targetObject, String fieldName, Object valueToInject) {
        try {
            Class<?> targetClass = targetObject.getClass();
            Field targetField = targetClass.getDeclaredField(fieldName);
            boolean wasAccessible = targetField.isAccessible();

            try {
                if (!wasAccessible) {
                    targetField.setAccessible(true);
                }
                targetField.set(targetObject, valueToInject);
            } finally {
                if (!wasAccessible) {
                    targetField.setAccessible(false);
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    public static User getUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("DangNV8");
        user.setPassword("123");
        user.setCart(new Cart());
        return user;
    }

    public static User getUserByCart() {
        User user = new User();
        user.setId(1L);
        user.setUsername("DangNV8");
        user.setPassword("123");
        Cart cart = new Cart();
        cart.setItems(Collections.singletonList(getItem()));
        user.setCart(cart);
        return user;
    }

    public static Item getItem() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Fish");
        item.setDescription("full gold");
        item.setPrice(BigDecimal.valueOf(120.0));
        return item;
    }

    public static UserOrder getUserOrder() {
        UserOrder userOrder = new UserOrder();
        userOrder.setId(1L);
        userOrder.setUser(getUserByCart());
        userOrder.setItems(new ArrayList<>());
        userOrder.setTotal(BigDecimal.valueOf(100.0));
        return userOrder;
    }
}
