package com.bellisaidev.platform.document_api.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class OwnerIdProvider {

    public String currentOwnerId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            // Phase 1: keep it usable before wiring JWT. Replace with JWT `sub` in Step 4.
            return "demo-user";
        }
        return auth.getName();
    }
}
