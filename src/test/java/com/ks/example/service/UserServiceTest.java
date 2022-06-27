package com.ks.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ks.example.dto.User;
import com.ks.example.exception.InvalidException;
import com.ks.example.exception.NotFoundException;
import com.ks.example.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldObjectHasUser() {
        ArrayNode users = userService.getUsers();
       assertThat(users.get(0).get("name").asText()).isEqualTo("Test User");
    }

    @Test
    public void shouldGetById() {
        User user = userService.getById(12L);
        assertThat(user.getName()).isEqualTo("Test User");
        assertThat(user.isActive()).isEqualTo(true);
    }

    @Test
    public void shouldThrowExceptionOnId() {
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            userService.getById(1L);
        });
        Assertions.assertEquals("unable to fetch reference metadata  id 1 not found", exception.getMessage());
    }

    @Test
    public void shouldSaveUser() {

        ObjectNode node = mapper.createObjectNode();
        node.put("id", 7);
        node.put("name", "Sample User");
        node.put("active", false);

        userService.save(node);
        User user = userService.getById(7L);
        assertThat(user.getId()).isEqualTo(7L);
        assertThat(user.getName()).isEqualTo("Sample User");
    }

    @Test
    public void shouldThrowExceptionOnSave() {
        InvalidException exception = Assertions.assertThrows(InvalidException.class, () -> {
            ObjectNode node = mapper.createObjectNode();
            node.put("id", 20);
            userService.save(node);
        });
        Assertions.assertEquals("required fields are missing", exception.getMessage());
        Assertions.assertEquals(2, exception.getValidationResult().size());
    }

    @Test
    public void shouldUpdateUser() {

        ObjectNode node = mapper.createObjectNode();
        node.put("id", 9);
        node.put("name", "Sample User");
        node.put("active", false);

        userService.save(node);
        User user = userService.getById(9L);
        assertThat(user.getId()).isEqualTo(9L);
        assertThat(user.getName()).isEqualTo("Sample User");

        node.put("id", 9);
        node.put("name", "Sample User Modified");
        node.put("active", true);

        userService.edit(node);
        User changed = userService.getById(9L);
        assertThat(changed.isActive()).isEqualTo(true);
        assertThat(changed.getName()).isEqualTo("Sample User Modified");
    }

    @Test
    public void shouldThrowExceptionOnEdit() {
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            ObjectNode node = mapper.createObjectNode();
            node.put("id", 20);
            userService.edit(node);
        });
        Assertions.assertEquals("unable to fetch reference metadata  id 20 not found", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionOnDelete() {
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            ObjectNode node = mapper.createObjectNode();
            node.put("id", 15);
            userService.delete(15L);
        });
        Assertions.assertEquals("unable to delete reference metadata  id 15 not found", exception.getMessage());
    }

    @Test
    public void shouldDeleteId() {
        ObjectNode node = mapper.createObjectNode();
        node.put("id", 10);
        node.put("name", "Sample User");
        node.put("active", false);

        userService.save(node);
        userService.delete(10L);
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            userService.getById(10L);
        });
        Assertions.assertEquals("unable to fetch reference metadata  id 10 not found", exception.getMessage());
    }
}
