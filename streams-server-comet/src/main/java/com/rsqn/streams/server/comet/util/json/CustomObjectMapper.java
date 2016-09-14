package com.rsqn.streams.server.comet.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper() {
    }
}