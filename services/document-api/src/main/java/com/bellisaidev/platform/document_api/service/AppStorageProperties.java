package com.bellisaidev.platform.document_api.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.storage")
public record AppStorageProperties(
        String bucket,
        long presignExpirySeconds
) {}
