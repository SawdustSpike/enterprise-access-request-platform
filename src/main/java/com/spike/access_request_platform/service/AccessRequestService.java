package com.spike.access_request_platform.service;
import com.spike.access_request_platform.dto.AccessRequestResponseDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.spike.access_request_platform.dto.CreateAccessRequestDto;
import com.spike.access_request_platform.model.AccessRequest;
import com.spike.access_request_platform.service.AuditService;
import com.spike.access_request_platform.model.RequestStatus;
import com.spike.access_request_platform.repository.AccessRequestRepository;
import org.springframework.stereotype.Service;
import com.spike.access_request_platform.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccessRequestService {

    private final AccessRequestRepository accessRequestRepository;
    private final AuditService auditService;
    public AccessRequestService(
            AccessRequestRepository repository,
            AuditService auditService) {
        this.accessRequestRepository = repository;
        this.auditService = auditService;
    }

    public AccessRequestResponseDto  createAccessRequest(CreateAccessRequestDto dto) {
        AccessRequest accessRequest = new AccessRequest();

        accessRequest.setRequesterName(dto.getRequesterName());
        accessRequest.setSystemName(dto.getSystemName());
        accessRequest.setAccessLevel(dto.getAccessLevel());
        accessRequest.setBusinessJustification(dto.getBusinessJustification());
        accessRequest.setStatus(RequestStatus.PENDING);
        accessRequest.setCreatedDate(LocalDateTime.now());

        return mapToResponseDto(accessRequestRepository.save(accessRequest));
    }
    public List<AccessRequestResponseDto> getAllAccessRequests() {
        return accessRequestRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    public AccessRequest approveAccessRequest(Long id, String comments) {
        AccessRequest accessRequest = accessRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Access request not found"));
        if (accessRequest.getStatus() != RequestStatus.PENDING) {
            throw new RuntimeException(
                    "Only pending requests can be approved");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException(
                    "No authenticated user found");
        }
        String managerName = authentication.getName();
        accessRequest.setDecidedBy(managerName);
        accessRequest.setDecisionDate(LocalDateTime.now());
        accessRequest.setStatus(RequestStatus.APPROVED);
        accessRequest.setDecisionComments(comments);
        auditService.log(
                accessRequest.getId(),
                "APPROVED",
                "system",
                comments
        );
        return accessRequestRepository.save(accessRequest);
    }

    public AccessRequest denyAccessRequest(Long id, String comments) {
        AccessRequest accessRequest = accessRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Access request not found"));
        if (accessRequest.getStatus() != RequestStatus.PENDING) {
            throw new RuntimeException(
                    "Only pending requests can be denied");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException(
                    "No authenticated user found");
        }
        String managerName = authentication.getName();
        accessRequest.setDecidedBy(managerName);
        accessRequest.setDecisionDate(LocalDateTime.now());
        accessRequest.setStatus(RequestStatus.DENIED);
        accessRequest.setDecisionComments(comments);
        auditService.log(
                accessRequest.getId(),
                "DENIED",
                "system",
                comments
        );
        return accessRequestRepository.save(accessRequest);
    }
    public List<AccessRequestResponseDto> getPendingRequests() {
        return accessRequestRepository.findByStatus(RequestStatus.PENDING)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    public AccessRequestResponseDto getAccessRequestById(Long id) {
        AccessRequest accessRequest = accessRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Access request not found"));

        return mapToResponseDto(accessRequest);
    }
    private AccessRequestResponseDto mapToResponseDto(AccessRequest accessRequest) {
        AccessRequestResponseDto dto = new AccessRequestResponseDto();

        dto.setId(accessRequest.getId());
        dto.setRequesterName(accessRequest.getRequesterName());
        dto.setSystemName(accessRequest.getSystemName());
        dto.setAccessLevel(accessRequest.getAccessLevel());
        dto.setBusinessJustification(accessRequest.getBusinessJustification());
        dto.setStatus(accessRequest.getStatus());
        dto.setCreatedDate(accessRequest.getCreatedDate());
        dto.setDecisionComments(accessRequest.getDecisionComments());
        dto.setDecidedBy(accessRequest.getDecidedBy());
        dto.setDecisionDate(accessRequest.getDecisionDate());

        return dto;
    }
    public List<AccessRequestResponseDto> getRequestsByStatus(RequestStatus status) {
        return accessRequestRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    public List<AccessRequestResponseDto> getRequestsByRequester(String requesterName) {
        return accessRequestRepository.findByRequesterNameContainingIgnoreCase(requesterName)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    public List<AccessRequestResponseDto> getRequestsBySystem(String systemName) {
        return accessRequestRepository.findBySystemNameContainingIgnoreCase(systemName)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    public Page<AccessRequestResponseDto> getPagedAccessRequests(Pageable pageable) {
        return accessRequestRepository.findAll(pageable)
                .map(this::mapToResponseDto);
    }
}