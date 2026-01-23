package com.bellisaidev.platform.document_api;

import com.bellisaidev.platform.document_api.service.AppStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppStorageProperties.class)
public class DocumentApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(DocumentApiApplication.class, args);
    }
}
