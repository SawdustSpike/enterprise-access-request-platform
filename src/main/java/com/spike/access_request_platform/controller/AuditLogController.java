package com.spike.access_request_platform.controller;

import com.spike.access_request_platform.model.AuditLog;
import com.spike.access_request_platform.repository.AuditLogRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/audit-logs")
public class AuditLogController {

    private final AuditLogRepository auditLogRepository;

    public AuditLogController(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @GetMapping
    public List<AuditLog> getAllAuditLogs() {
        return auditLogRepository.findAll();
    }
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @GetMapping("/request/{requestId}")
    public List<AuditLog> getAuditLogsByRequestId(@PathVariable Long requestId) {
        return auditLogRepository.findByRequestId(requestId);
    }
}