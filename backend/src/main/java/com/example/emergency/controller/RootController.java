package com.example.emergency.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RootController {

    @GetMapping("/")
    public Map<String, String> root() {
        return Map.of(
                "message", "Emergency backend is running.",
                "api", "/api/emergencies"
        );
    }

    @GetMapping("/api")
    public Map<String, String> apiRoot() {
        return Map.of(
                "message", "Emergency backend API is available.",
                "endpoints", "/api/emergencies"
        );
    }
}
