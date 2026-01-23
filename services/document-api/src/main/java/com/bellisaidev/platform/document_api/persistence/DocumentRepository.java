package com.bellisaidev.platform.document_api.persistence;

import com.bellisaidev.platform.document_api.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {}
