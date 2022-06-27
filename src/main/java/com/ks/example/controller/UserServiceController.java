package com.ks.example.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.ks.example.dto.User;
import com.ks.example.service.UserService;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Timed
public class UserServiceController {

    @Autowired
    private UserService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ArrayNode users() {
        return service.getUsers();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getById(@PathVariable("id") Long id) {
        return service.getById(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRefMetadata(@PathVariable("id") Long id) {
        service.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody JsonNode data) {
        return service.save(data);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ArrayNode edit(@RequestBody JsonNode data) {
        return service.edit(data);
    }

}
