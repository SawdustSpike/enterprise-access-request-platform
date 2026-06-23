package com.spike.access_request_platform.controller;

import com.spike.access_request_platform.model.NotificationLog;
import com.spike.access_request_platform.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Operation(
            summary = "Get notifications",
            description = "Returns all notification history."
    )
    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public List<NotificationLog> getNotifications() {
        return notificationService.getAllNotifications();
    }
    @GetMapping("/request/{requestId}")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public List<NotificationLog> getNotificationsByRequestId(
            @PathVariable Long requestId) {

        return notificationService.getNotificationsByRequestId(requestId);
    }
}
