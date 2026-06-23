package com.spike.access_request_platform.controller;
import com.spike.access_request_platform.dto.*;
import com.spike.access_request_platform.model.AccessRequest;
import com.spike.access_request_platform.model.AuditLog;
import com.spike.access_request_platform.model.RequestStatus;
import com.spike.access_request_platform.service.AccessRequestService;
import com.spike.access_request_platform.repository.AuditLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Tag(name = "Access Requests", description = "Create, review, approve, deny, and search enterprise access requests")
@RequestMapping("/access-requests")
public class AccessRequestController {

    private final AccessRequestService accessRequestService;

    public AccessRequestController(
            AccessRequestService service) {
        this.accessRequestService = service;
    }

    @PostMapping
    public AccessRequestResponseDto createAccessRequest(@Valid @RequestBody CreateAccessRequestDto dto) {
        return accessRequestService.createAccessRequest(dto);
    }
    @GetMapping
    public Page<AccessRequestResponseDto> getAllAccessRequests(Pageable pageable) {
        return accessRequestService.getPagedAccessRequests(pageable);
    }
    @GetMapping("/{id}")
    public AccessRequestResponseDto getAccessRequestById(@PathVariable Long id) {
        return accessRequestService.getAccessRequestById(id);
    }
    @Operation(summary = "Approve an access request", description = "Approves a pending request and records an audit log entry with decision comments.")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @PutMapping("/{id}/approve")
    public AccessRequest approveAccessRequest(
            @PathVariable Long id,
            @RequestBody DecisionDto dto) {

        return accessRequestService.approveAccessRequest(id, dto.getComments());
    }
    @Operation(summary = "Deny an access request", description = "Denies a pending request and records an audit log entry with decision comments.")
    @PutMapping("/{id}/deny")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public AccessRequest denyAccessRequest(
            @PathVariable Long id,
            @RequestBody DecisionDto dto) {

        return accessRequestService.denyAccessRequest(id, dto.getComments());
    }

    @GetMapping("/pending")
    public List<AccessRequestResponseDto> getPendingRequests() {
        return accessRequestService.getPendingRequests();
    }
    @GetMapping("/status/{status}")
    public List<AccessRequestResponseDto> getRequestsByStatus(@PathVariable RequestStatus status) {
        return accessRequestService.getRequestsByStatus(status);
    }

    @GetMapping("/requester/{requesterName}")
    public List<AccessRequestResponseDto> getRequestsByRequester(@PathVariable String requesterName) {
        return accessRequestService.getRequestsByRequester(requesterName);
    }

    @GetMapping("/system/{systemName}")
    public List<AccessRequestResponseDto> getRequestsBySystem(@PathVariable String systemName) {
        return accessRequestService.getRequestsBySystem(systemName);
    }
    @Operation(summary = "Get access request metrics", description = "Returns totals for pending, approved, denied, and all access requests.")
    @GetMapping("/metrics")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public AccessRequestMetricsDto getMetrics() {
        return accessRequestService.getMetrics();
    }
    @GetMapping("/dashboard")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public DashboardDto getDashboard() {
        return accessRequestService.getDashboard();
    }

}