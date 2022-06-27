package com.ks.example.exception;

import com.networknt.schema.ValidationMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Set;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Getter
public class InvalidException extends RuntimeException {
    Set<ValidationMessage> validationResult;
    public InvalidException(String error, Set<ValidationMessage> validationResult) {
        super(error);
        this.validationResult = validationResult;
    }

    public InvalidException(String error){ super(error);}
}