package com.spike.access_request_platform.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class NotificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long requestId;

    private String recipient;

    private String subject;

    @Column(length = 2000)
    private String message;

    private LocalDateTime sentAt;

    private String status;
}