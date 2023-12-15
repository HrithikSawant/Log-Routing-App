package com.hrithik.logrouter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LogService {

    private static final Logger logger = LogManager.getLogger(LogService.class);

    public void sendPayload(Map<String, Object> payload) throws JsonProcessingException {
        String jsonString = new ObjectMapper().writeValueAsString(payload);
        logger.info(jsonString);
    }

    public Map<String, Object> getPayloadMap(String payload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(payload, new TypeReference<Map<String, Object>>() {
        });
    }
}
