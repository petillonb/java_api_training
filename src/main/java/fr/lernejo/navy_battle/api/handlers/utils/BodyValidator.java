package fr.lernejo.navy_battle.api.handlers.utils;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

public abstract class BodyValidator {


    String path;

    public BodyValidator(String path) {
        this.path = path;
    }

    protected boolean _validate(InputStream body) throws IOException {
        try {


            ObjectMapper objectMapper = new ObjectMapper();
            JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);
            JsonNode json = objectMapper.readTree(body);


            String tmp = new String(Files.readAllBytes(Paths.get(path)));
            JsonSchema schema = schemaFactory.getSchema(tmp);
            Set<ValidationMessage> validationResult = schema.validate(json);
            if (validationResult.isEmpty()) {

                // show custom message if there is no validation error
                System.out.println("There is no validation errors");
                return true;

            } else {

                // show all the validation error
                validationResult.forEach(vm -> System.out.println(vm.getMessage()));

                return false;
            }

        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

}
