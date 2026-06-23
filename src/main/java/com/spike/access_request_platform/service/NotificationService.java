package com.spike.access_request_platform.service;

import com.spike.access_request_platform.dto.AccessRequestResponseDto;
import com.spike.access_request_platform.model.NotificationLog;
import com.spike.access_request_platform.repository.NotificationLogRepository;
import jakarta.persistence.Column;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationLogRepository repository;

    public NotificationService(NotificationLogRepository repository) {
        this.repository = repository;
    }
    public List<NotificationLog> getAllNotifications() {
        return repository.findAll()
                .stream()
                .toList();
    }
    public List<NotificationLog> getNotificationsByRequestId(long id) {
        return repository.findById(id)
                .stream()
                .toList();
    }
    public void notifyRequestCreated(Long requestId, String requesterName) {
        NotificationLog log = new NotificationLog();
        log.setRequestId(requestId);
        log.setRecipient(requesterName);
        log.setSubject("Access Request Created");
        log.setMessage(
                "Access request " + requestId + " has been created."
        );
        log.setSentAt(LocalDateTime.now());
        log.setStatus("SENT");

        repository.save(log);
    }

    public void notifyRequestApproved(Long requestId, String requesterName) {
        NotificationLog log = new NotificationLog();

        log.setRequestId(requestId);
        log.setRecipient(requesterName);
        log.setSubject("Access Request Approved");
        log.setMessage(
                "Access request " + requestId + " has been approved."
        );
        log.setSentAt(LocalDateTime.now());
        log.setStatus("SENT");

        repository.save(log);
    }

    public void notifyRequestDenied(Long requestId, String requesterName) {
        NotificationLog log = new NotificationLog();

        log.setRequestId(requestId);
        log.setRecipient(requesterName);
        log.setSubject("Access Request Denied");
        log.setMessage(
                "Access request " + requestId + " has been denied."
        );
        log.setSentAt(LocalDateTime.now());
        log.setStatus("SENT");

        repository.save(log);
    }
}