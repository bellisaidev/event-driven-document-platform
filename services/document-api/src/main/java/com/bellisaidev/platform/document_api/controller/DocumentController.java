package com.bellisaidev.platform.document_api.controller;

import com.bellisaidev.platform.document_api.controller.dto.CreateDocumentRequest;
import com.bellisaidev.platform.document_api.controller.dto.CreateDocumentResponse;
import com.bellisaidev.platform.document_api.service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<CreateDocumentResponse> create(@Valid @RequestBody CreateDocumentRequest request) {
        return ResponseEntity.ok(documentService.create(request));
    }
}
