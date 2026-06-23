package com.spike.access_request_platform.dto;

import com.spike.access_request_platform.model.RequestStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccessRequestResponseDto {

    private Long id;
    private String requesterName;
    private String systemName;
    private String accessLevel;
    private String businessJustification;
    private RequestStatus status;
    private LocalDateTime createdDate;
    private String decidedBy;
    private LocalDateTime decisionDate;
    private String decisionComments;
}