package com.spike.access_request_platform.repository;
import java.time.LocalDateTime;
import com.spike.access_request_platform.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByRequestId(Long requestId);
    List<AuditLog> findByPerformedBy(String performedBy);
    List<AuditLog> findByActionDateBetween(LocalDateTime start, LocalDateTime end);
    List<AuditLog> findByAction(String action);
}
