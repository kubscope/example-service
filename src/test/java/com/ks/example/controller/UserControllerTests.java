package com.ks.example.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ks.example.dto.User;
import com.ks.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.mockito.BDDMockito.when;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UserControllerTests {
    @Mock
    private UserService service;
    @InjectMocks
    private UserServiceController controller;

    @BeforeEach
    void setMockOutput() { }

    static JsonNode buildUserJsonNode() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("id", 1);
        node.put("name", "Sample User");
        node.put("active", false);
        return node;
    }
    @Test
    public void shouldSaveUser() {
        User user = User.builder().id(1L).name("Sample User").active(false).build();
        when(service.save(buildUserJsonNode())).thenReturn(user);
        User resp = controller.save(buildUserJsonNode());
        assertThat(resp.getId()).isEqualTo(1L);
    }

    @Test
    public void shouldEditUser() {

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode nodes = mapper.createArrayNode();
        nodes.add(buildUserJsonNode());

        when(service.edit(buildUserJsonNode())).thenReturn(nodes);
        ArrayNode resp = controller.edit(buildUserJsonNode());
        assertThat(resp.get(0).get("name").asText()).isEqualTo("Sample User");
    }

    @Test
    public void shouldRetrieveUser() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode nodes = mapper.createArrayNode();
        nodes.add(buildUserJsonNode());

        when(service.getUsers()).thenReturn(nodes);
        ArrayNode resp = controller.users();
        assertThat(resp.size()).isEqualTo(1);
        assertThat(resp.get(0).get("name").asText()).isEqualTo("Sample User");
    }


    @Test
    public void shouldGetById() {
        User obj = User.builder().id(5L).name("Test").active(false).build();
        when(service.getById(5L)).thenReturn(obj);
        User resp = controller.getById(5L);
        assertThat(resp.getId()).isEqualTo(5L);
        assertThat(resp.getName()).isEqualTo("Test");
    }
    @Test
    public void shouldDeleteMetaData() {
        doNothing().when(service).delete( 5L);
        controller.deleteRefMetadata(5L);
    }
}
