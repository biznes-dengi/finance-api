package com.finance.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {
    private final static DateTimeFormatter LOCAL_DATE = DateTimeFormatter.ISO_LOCAL_DATE;
    private final static DateTimeFormatter LOCAL_DATE_TIME = DateTimeFormatter.ISO_LOCAL_DATE;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();

        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        module.addSerializer(LocalDate.class, new LocalDateSerializer(LOCAL_DATE));
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(LOCAL_DATE_TIME));
        objectMapper.registerModule(module);

        return objectMapper;
    }
}
