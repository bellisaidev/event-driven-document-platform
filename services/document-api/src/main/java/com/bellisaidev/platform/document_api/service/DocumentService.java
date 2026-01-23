package com.bellisaidev.platform.document_api.service;

import com.bellisaidev.platform.document_api.controller.dto.CreateDocumentRequest;
import com.bellisaidev.platform.document_api.controller.dto.CreateDocumentResponse;
import com.bellisaidev.platform.document_api.domain.Document;
import com.bellisaidev.platform.document_api.domain.DocumentStatus;
import com.bellisaidev.platform.document_api.domain.DocumentStatusHistory;
import com.bellisaidev.platform.document_api.persistence.DocumentRepository;
import com.bellisaidev.platform.document_api.persistence.DocumentStatusHistoryRepository;
import com.bellisaidev.platform.document_api.security.OwnerIdProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentStatusHistoryRepository historyRepository;
    private final S3PresignService presignService;
    private final AppStorageProperties storageProperties;
    private final OwnerIdProvider ownerIdProvider;

    public DocumentService(
            DocumentRepository documentRepository,
            DocumentStatusHistoryRepository historyRepository,
            S3PresignService presignService,
            AppStorageProperties storageProperties,
            OwnerIdProvider ownerIdProvider
    ) {
        this.documentRepository = documentRepository;
        this.historyRepository = historyRepository;
        this.presignService = presignService;
        this.storageProperties = storageProperties;
        this.ownerIdProvider = ownerIdProvider;
    }

    @Transactional
    public CreateDocumentResponse create(CreateDocumentRequest req) {
        String ownerId = ownerIdProvider.currentOwnerId();

        // MVP guardrails
        String contentType = normalizeContentType(req.contentType());
        if (!"text/csv".equals(contentType)) {
            throw new IllegalArgumentException("Only text/csv is supported in v1");
        }

        UUID documentId = UUID.randomUUID();
        String safeFilename = sanitizeFilename(req.filename());
        String bucket = storageProperties.bucket();
        String key = "raw/%s/%s/%s".formatted(ownerId, documentId, safeFilename);

        Document doc = new Document(
                documentId,
                ownerId,
                DocumentStatus.UPLOADING,
                safeFilename,
                contentType,
                bucket,
                key
        );

        documentRepository.save(doc);

        historyRepository.save(new DocumentStatusHistory(
                UUID.randomUUID(),
                documentId,
                null,
                DocumentStatus.UPLOADING,
                ownerId
        ));

        var presigned = presignService.presignPut(bucket, key, contentType, storageProperties.presignExpirySeconds());

        return new CreateDocumentResponse(
                documentId,
                doc.getStatus().name(),
                new CreateDocumentResponse.Upload(
                        "PUT",
                        presigned.url(),
                        Map.of("Content-Type", presigned.requiredContentType()),
                        presigned.expiresInSeconds()
                )
        );
    }

    private static String normalizeContentType(String ct) {
        return ct == null ? "" : ct.trim().toLowerCase(Locale.ROOT);
    }

    /**
     * Prevent path traversal and weird names. Keep it simple for v1.
     */
    static String sanitizeFilename(String filename) {
        if (filename == null) return "upload.csv";
        String f = filename.trim();
        f = f.replace("\\", "/");
        if (f.contains("/")) {
            f = f.substring(f.lastIndexOf("/") + 1);
        }
        // allow basic chars; replace others with underscore
        f = f.replaceAll("[^a-zA-Z0-9._-]", "_");
        if (f.isBlank()) f = "upload.csv";
        if (!f.toLowerCase(Locale.ROOT).endsWith(".csv")) f = f + ".csv";
        return f;
    }
}
