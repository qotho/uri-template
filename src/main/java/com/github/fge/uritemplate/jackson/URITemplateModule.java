package com.github.fge.uritemplate.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.fge.uritemplate.URITemplate;

/**
 * Uri Template module for Jackson. To use it, simpley register it with an
 * {@link ObjectMapper} like so:
 *
 * <pre>
 * ObjectMapper mapper = new ObjectMapper();
 * mapper.registerModule(new UriTemplateModule());
 * </pre>
 *
 * Any mapped JSON property that is a {@link UriTemplate} will be serialized or
 * deserialized properly.
 */
public class URITemplateModule extends SimpleModule {

    /** The serialVersionUID */
    private static final long serialVersionUID = 20L;

    /**
     *
     * Create a new UriTemplateModule.
     *
     */
    public URITemplateModule() {
        super("URI-Templates", Version.unknownVersion());
        addDeserializer(URITemplate.class, new URITemplateDeserializer());
        addSerializer(URITemplate.class, new URITemplateSerializer());
    }

}