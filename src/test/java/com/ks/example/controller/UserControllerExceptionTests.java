package com.ks.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ks.example.dto.User;
import com.ks.example.exception.InternalException;
import com.ks.example.exception.InvalidException;
import com.ks.example.exception.NotFoundException;
import com.ks.example.exception.SampleExceptionHandler;
import com.ks.example.service.UserService;
import com.networknt.schema.ValidationMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.BDDMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class UserControllerExceptionTests {
    private MockMvc mvc;
    @Mock
    private UserService service;
    @InjectMocks
    private UserServiceController controller;
    @BeforeEach
    void setMockOutput() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new SampleExceptionHandler())
                .build();
    }
    @Test
    public void shouldThrowExceptionOnGet() throws Exception {

        when(service.getById(2L)).thenThrow(new NotFoundException("Unable to find 2"));

        MockHttpServletResponse response = mvc.perform( get("/api/v1/users/2")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void shouldThrowExceptionOnSave() throws Exception {

        Set<ValidationMessage> validationResult = new HashSet<>();
        ValidationMessage message = new ValidationMessage.Builder().customMessage("Validation Failed").build();
        validationResult.add(message);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("id", 1);
       // when(service.save(node)).thenThrow(new InvalidException("Unable to find 2", validationResult));

        MockHttpServletResponse response = mvc.perform( post("/api/v1/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(node.asText())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        )
                .andReturn().getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
    }
    @Test
    public void shouldThrowException() throws Exception {

        when(service.getById(2L)).thenThrow(new InternalException("Some exception"));

        MockHttpServletResponse response = mvc.perform( get("/api/v1/users/2")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        Assertions.assertEquals(response.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    public void shouldThrowInvalidException() throws Exception {

        when(service.getUsers()).thenThrow(new InvalidException("Some exception"));
        MockHttpServletResponse invalid = mvc.perform( get("/api/v1/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        Assertions.assertEquals(invalid.getStatus(), HttpStatus.BAD_REQUEST.value());
    }
}
