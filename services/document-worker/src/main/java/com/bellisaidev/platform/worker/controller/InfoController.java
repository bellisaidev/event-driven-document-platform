package com.bellisaidev.platform.worker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class InfoController {

    @GetMapping("/info")
    public Map<String, String> info() {
        return Map.of(
                "service", "document-worker",
                "version", "0.0.1"
        );
    }
}

