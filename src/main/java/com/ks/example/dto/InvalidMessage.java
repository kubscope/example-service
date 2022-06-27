package com.ks.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvalidMessage {

    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;
    private List<String> validationMessage;
}
