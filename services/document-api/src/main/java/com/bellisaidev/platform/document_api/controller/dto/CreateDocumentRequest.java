package com.bellisaidev.platform.document_api.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateDocumentRequest(
        @NotBlank String filename,
        @NotBlank String contentType
) {}
