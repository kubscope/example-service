package com.ks.example.exception;


import com.ks.example.dto.InvalidMessage;
import com.ks.example.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class SampleExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFound(NotFoundException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InternalException.class)
    public ResponseEntity<ErrorMessage> internalServerError(InternalException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<InvalidMessage> internalServerError(InvalidException exception, WebRequest request) {

        List<String> validationMessages = null;
        if (exception.getValidationResult() != null && !exception.getValidationResult().isEmpty()) {
            validationMessages = exception.getValidationResult().stream().map(e -> e.getMessage()).collect(Collectors.toList());
        }
        InvalidMessage message = new InvalidMessage(HttpStatus.BAD_REQUEST.value(), new Date(), exception.getMessage(), request.getDescription(false), validationMessages);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}

