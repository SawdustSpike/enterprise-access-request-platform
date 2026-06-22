package com.spike.access_request_platform.repository;

import com.spike.access_request_platform.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository
        extends JpaRepository<AuditLog, Long> {
}