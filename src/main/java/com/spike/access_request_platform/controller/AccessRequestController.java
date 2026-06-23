package com.spike.access_request_platform.controller;
import com.spike.access_request_platform.dto.AccessRequestResponseDto;
import com.spike.access_request_platform.dto.CreateAccessRequestDto;
import com.spike.access_request_platform.model.AccessRequest;
import com.spike.access_request_platform.model.AuditLog;
import com.spike.access_request_platform.model.RequestStatus;
import com.spike.access_request_platform.service.AccessRequestService;
import com.spike.access_request_platform.repository.AuditLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/access-requests")
public class AccessRequestController {

    private final AccessRequestService accessRequestService;
    private final AuditLogRepository auditLogRepository;
    public AccessRequestController(
            AccessRequestService service,
            AuditLogRepository auditLogRepository) {

        this.accessRequestService = service;
        this.auditLogRepository = auditLogRepository;
    }

    @PostMapping
    public AccessRequestResponseDto createAccessRequest(@Valid @RequestBody CreateAccessRequestDto dto) {
        return accessRequestService.createAccessRequest(dto);
    }
    @GetMapping
    public Page<AccessRequestResponseDto> getAllAccessRequests(Pageable pageable) {
        return accessRequestService.getPagedAccessRequests(pageable);
    }
    @PutMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public AccessRequest approveAccessRequest(@PathVariable Long id) {
        return accessRequestService.approveAccessRequest(id);
    }

    @PutMapping("/{id}/deny")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public AccessRequest denyAccessRequest(@PathVariable Long id) {
        return accessRequestService.denyAccessRequest(id);
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
    @GetMapping("/audit")
    public List<AuditLog> getAuditLog() {
        return auditLogRepository.findAll();
    }
}