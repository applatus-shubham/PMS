package com.example.grappler;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String text = parser.getText();
        // Define your custom date-time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalDateTime.parse(text, formatter);
    }
}
