package com.bellisaidev.platform.document_api.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "document_status_history")
@Getter
@NoArgsConstructor
public class DocumentStatusHistory {

    @Id
    private UUID id;

    @Column(name = "document_id", nullable = false)
    private UUID documentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "from_status")
    private DocumentStatus fromStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "to_status", nullable = false)
    private DocumentStatus toStatus;

    @Column(name = "changed_at", nullable = false)
    private Instant changedAt;

    @Column(name = "changed_by", nullable = false, length = 128)
    private String changedBy;

    public DocumentStatusHistory(
            UUID id,
            UUID documentId,
            DocumentStatus fromStatus,
            DocumentStatus toStatus,
            String changedBy
    ) {
        this.id = id;
        this.documentId = documentId;
        this.fromStatus = fromStatus;
        this.toStatus = toStatus;
        this.changedBy = changedBy;
    }

    @PrePersist
    void onCreate() {
        this.changedAt = Instant.now();
    }
}
