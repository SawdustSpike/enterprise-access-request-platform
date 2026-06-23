package com.spike.access_request_platform.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.spike.access_request_platform.model.AuditLog;
import com.spike.access_request_platform.repository.AuditLogRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Tag(name = "Audit Logs", description = "Review audit history for access request decisions")
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
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @GetMapping("/user/{performedBy}")
    public List<AuditLog> getAuditLogsByUser(@PathVariable String performedBy) {
        return auditLogRepository.findByPerformedBy(performedBy);
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @GetMapping("/action/{action}")
    public List<AuditLog> getAuditLogsByAction(@PathVariable String action) {
        return auditLogRepository.findByAction(action);
    }
    @Operation(
            summary = "Get audit logs by date range",
            description = "Returns audit log entries performed between the supplied start and end timestamps."
    )
    @GetMapping("/date-range")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public List<AuditLog> getAuditLogsByDateRange(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {

        return auditLogRepository.findByActionDateBetween(start, end);
    }
}