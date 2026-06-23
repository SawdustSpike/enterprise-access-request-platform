package com.spike.access_request_platform.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "access_requests")
@Data
public class AccessRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String requesterName;

    private String systemName;

    private String accessLevel;

    @Column(length = 1000)
    private String businessJustification;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private LocalDateTime createdDate;
    private String decidedBy;
    private LocalDateTime DecisionDate;
    @Column(length = 2000)
    private String decisionComments;

}