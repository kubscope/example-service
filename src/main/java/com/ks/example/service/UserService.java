package com.ks.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ks.example.dto.User;
import com.ks.example.exception.NotFoundException;
import com.ks.example.utils.SchemaValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserService {

    public static final String ID = "id";

    private ArrayNode users;
    private ObjectMapper objectMapper;

    @Autowired
    public UserService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.users = populateData();
    }

    public ArrayNode getUsers() {
        return users;
    }

    public User getById(Long id) {


        for (int i = 0; i < users.size(); ++i) {
            JsonNode node = users.get(i);
            if (node.has(ID) && node.get(ID).asLong() == id) {
                log.info("get element {}  ", i);
                return objectMapper.convertValue(node, User.class);
            }
        }
        throw new NotFoundException("unable to fetch reference metadata  id " + id + " not found");
    }

    public User save(JsonNode data) {
      SchemaValidator.validateJsonData("user", data);
      users.add(data);
      return objectMapper.convertValue(data, User.class);
    }


    public ArrayNode edit(JsonNode data) {

        long id = data.get(UserService.ID).asLong();
        for (int i = 0; i < users.size(); ++i) {
            JsonNode node = users.get(i);
            if (node.has(ID) && node.get(ID).asLong() == id) {
                log.info("removing element {} for modification ", i);
                users.remove(i);
                users.add(data);
                return users;
            }
        }
        throw new NotFoundException("unable to fetch reference metadata  id " + id + " not found");
    }

    public void delete(Long id) {
        for (int i = 0; i < users.size(); ++i) {
            JsonNode node = users.get(i);
            if (node.has(ID) && node.get(ID).asLong() == id) {
                log.info("removing element {} for modification ", i);
                users.remove(i);
                return;
            }
        }
        throw new NotFoundException("unable to delete reference metadata  id " + id + " not found");
    }


    private ArrayNode populateData() {
        ObjectNode user = this.objectMapper.createObjectNode();
        user.put("id", 12L);
        user.put("name", "Test User");
        user.put("active", true);
        ArrayNode nodes = this.objectMapper.createArrayNode();
        nodes.add(user);
        return nodes;
    }
}
