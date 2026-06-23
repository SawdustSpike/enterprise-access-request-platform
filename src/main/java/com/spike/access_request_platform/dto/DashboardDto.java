package com.spike.access_request_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DashboardDto {

    private long totalRequests;
    private long pendingRequests;
    private long approvedRequests;
    private long deniedRequests;

    private List<AccessRequestResponseDto> recentRequests;
}