package com.bellisaidev.platform.document_api.security;

import org.springframework.stereotype.Component;

@Component
public class OwnerIdProvider {

    /**
     * Phase 1: no authentication yet.
     * This will be replaced with JWT subject extraction in a later step.
     */
    public String currentOwnerId() {
        return "demo-user";
    }
}
