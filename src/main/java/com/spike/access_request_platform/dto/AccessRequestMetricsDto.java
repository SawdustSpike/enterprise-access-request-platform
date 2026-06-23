package com.spike.access_request_platform.dto;

import lombok.Getter;

@Getter
public class AccessRequestMetricsDto {
    private long totalRequests;
    private long pendingRequests;
    private long approvedRequests;
    private long deniedRequests;

    public AccessRequestMetricsDto(long totalRequests, long pendingRequests, long approvedRequests, long deniedRequests) {
        this.totalRequests = totalRequests;
        this.pendingRequests = pendingRequests;
        this.approvedRequests = approvedRequests;
        this.deniedRequests = deniedRequests;
    }

}