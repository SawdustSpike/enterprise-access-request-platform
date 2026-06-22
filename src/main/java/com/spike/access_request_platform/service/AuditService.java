package com.spike.access_request_platform.service;

import com.spike.access_request_platform.model.AuditLog;
import com.spike.access_request_platform.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditService {

    private final AuditLogRepository repository;

    public AuditService(AuditLogRepository repository) {
        this.repository = repository;
    }

    public void log(
            Long requestId,
            String action,
            String user,
            String comments) {

        AuditLog entry = new AuditLog();

        entry.setRequestId(requestId);
        entry.setAction(action);
        entry.setPerformedBy(user);
        entry.setComments(comments);
        entry.setActionDate(LocalDateTime.now());

        repository.save(entry);
    }
}