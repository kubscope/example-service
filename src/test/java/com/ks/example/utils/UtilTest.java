package com.ks.example.utils;

import com.ks.example.exception.InternalException;
import com.ks.example.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UtilTest {

    @Test
    public void shouldThrowExceptionOnLoad() {
        InternalException exception = Assertions.assertThrows(InternalException.class, () -> {
                SchemaValidator.validateJsonData("invalid.json", null);
        });
        Assertions.assertEquals("unable to load/parse schema file invalid.json", exception.getMessage());
    }
}
