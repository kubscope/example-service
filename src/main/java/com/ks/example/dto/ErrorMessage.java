package com.ks.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

    private int statusCode;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date timestamp;

    private String message;

    private String description;
}
