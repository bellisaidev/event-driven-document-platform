package com.bellisaidev.platform.document_api.controller.dto;

import java.util.Map;
import java.util.UUID;

public record CreateDocumentResponse(
        UUID documentId,
        String status,
        Upload upload
) {
    public record Upload(
            String method,
            String url,
            Map<String, String> headers,
            long expiresInSeconds
    ) {}
}
