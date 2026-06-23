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
import com.spike.access_request_platform.dto.DecisionDto;
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
    @GetMapping("/{id}")
    public AccessRequestResponseDto getAccessRequestById(@PathVariable Long id) {
        return accessRequestService.getAccessRequestById(id);
    }
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @PutMapping("/{id}/approve")
    public AccessRequest approveAccessRequest(
            @PathVariable Long id,
            @RequestBody DecisionDto dto) {

        return accessRequestService.approveAccessRequest(id, dto.getComments());
    }

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

}