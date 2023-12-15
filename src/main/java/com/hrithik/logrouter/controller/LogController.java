package com.hrithik.logrouter.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.hrithik.logrouter.service.LogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LogController {

    @Autowired
    LogService logService;

    private static final Logger logger = LogManager.getLogger(LogController.class);

    @GetMapping("/")
    public ResponseEntity<String> welcome(){
        logger.info("Welcome to LogRoute server");
        return ResponseEntity.ok("Welcome to Log Router");
    }

    @PostMapping("/")
    public ResponseEntity<String> webhook(@RequestBody String payloadMap) throws JsonProcessingException {
        Map<String, Object> payload = logService.getPayloadMap(payloadMap);
        if (payload.isEmpty()) {
            logger.error("Payload processing failed: Empty payload received");
            // TODO: Handle the case where the payloadMap is Empty
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing payload");
        }

        logService.sendPayload(payload);
        return ResponseEntity.status(HttpStatus.OK).body("Webhook verified and saved successful");
    }


}

