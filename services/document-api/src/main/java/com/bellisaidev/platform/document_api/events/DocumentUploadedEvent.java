package com.bellisaidev.platform.document_api.events;

import java.time.Instant;
import java.util.UUID;

public record DocumentUploadedEvent(
        UUID eventId,
        String eventType,
        int eventVersion,
        Instant occurredAt,
        DocumentPayload document
) {
    public static DocumentUploadedEvent of(UUID documentId, String ownerId, String bucket, String key, String contentType, String filename) {
        return new DocumentUploadedEvent(
                UUID.randomUUID(),
                "DocumentUploaded",
                1,
                Instant.now(),
                new DocumentPayload(documentId, ownerId, bucket, key, contentType, filename)
        );
    }

    public record DocumentPayload(
            UUID id,
            String ownerId,
            String s3Bucket,
            String s3Key,
            String contentType,
            String filename
    ) {}
}
