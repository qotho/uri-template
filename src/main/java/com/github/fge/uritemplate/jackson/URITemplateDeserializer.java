package com.github.fge.uritemplate.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.github.fge.uritemplate.URITemplate;
import com.github.fge.uritemplate.URITemplateParseException;

/**
 * A {@link JsonDeserializer} that deserializes a string into a {@link UriTemplate}.
 */
public class URITemplateDeserializer extends JsonDeserializer<URITemplate> {

    @Override
    public URITemplate deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String value = parser.getText();

        try {
            return new URITemplate(value);
        }
        catch (URITemplateParseException e) {
            throw new JsonParseException("Error parsing the URI Template", parser.getCurrentLocation(), e);
        }
    }

}