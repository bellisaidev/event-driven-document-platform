package com.bellisaidev.platform.document_api.controller.dto;

import java.util.UUID;

public record CompleteUploadResponse(UUID documentId, String status) {}
