package com.ks.example.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.ks.example.exception.InternalException;
import com.ks.example.exception.InvalidException;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * This class validates core application schemas,
 * Schema will be loaded from class path and validated against to the payload
 * if payload is not compile with schema it will be returning Http 404 status
 */
@Slf4j
public class SchemaValidator {

    // validate schema with json payload
    public static void validateJsonData(String schemaName, JsonNode node) {

        try {
            InputStream stream = new ClassPathResource("schema/" + schemaName + ".json").getInputStream();
            JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);
            JsonSchema schema = schemaFactory.getSchema(stream);
            Set<ValidationMessage> validationResult = schema.validate(node);

            log.debug("schema  name {}  JSON \n{} ", schemaName, schema.toString());
            if (!validationResult.isEmpty()) {
                log.error("unable process the request , required fields are missing {} ", validationResult.toString());
                throw new InvalidException("required fields are missing", validationResult);
            }
        } catch (IOException e) {
            log.error("unable to load schema file check schema/{}.json is available in classpath ", schemaName);
            throw new InternalException("unable to load/parse schema file " + schemaName);
        }
    }
}
