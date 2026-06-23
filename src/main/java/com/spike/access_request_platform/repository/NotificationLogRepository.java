package com.spike.access_request_platform.repository;

import com.spike.access_request_platform.model.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {
    List<NotificationLog> findByRequestId(Long requestId);
}