package com.github.fge.uritemplate.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.fge.uritemplate.URITemplate;

/**
 * A {@link JsonSerializer} that serializes a {@link UriTemplate} to a JSON
 * string value.
 */
public class URITemplateSerializer extends JsonSerializer<URITemplate> {

    @Override
    public void serialize(URITemplate value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeString(value.getTemplate());
    }

}