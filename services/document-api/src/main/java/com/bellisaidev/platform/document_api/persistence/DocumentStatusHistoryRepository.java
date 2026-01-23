package com.bellisaidev.platform.document_api.persistence;

import com.bellisaidev.platform.document_api.domain.DocumentStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentStatusHistoryRepository extends JpaRepository<DocumentStatusHistory, UUID> {}
