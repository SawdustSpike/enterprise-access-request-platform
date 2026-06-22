package com.spike.access_request_platform.service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import com.spike.access_request_platform.dto.CreateAccessRequestDto;
import com.spike.access_request_platform.model.AccessRequest;
import com.spike.access_request_platform.model.RequestStatus;
import com.spike.access_request_platform.repository.AccessRequestRepository;
import com.spike.access_request_platform.repository.AuditLogRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccessRequestServiceTest {

    private final AccessRequestRepository repository =
            Mockito.mock(AccessRequestRepository.class);

    private final AuditService auditService =
            Mockito.mock(AuditService.class);

    private final AccessRequestService service =
            new AccessRequestService(repository, auditService);

    @Test
    void createAccessRequest_setsStatusToPending() {
        CreateAccessRequestDto dto = new CreateAccessRequestDto();
        dto.setRequesterName("Michael Cowell");
        dto.setSystemName("Claims Processing Portal");
        dto.setAccessLevel("READ_WRITE");
        dto.setBusinessJustification("Need access for production support.");

        when(repository.save(any(AccessRequest.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var result = service.createAccessRequest(dto);

        assertEquals(RequestStatus.PENDING, result.getStatus());
        assertEquals("Michael Cowell", result.getRequesterName());

        verify(repository, times(1)).save(any(AccessRequest.class));
    }
    @Test
    void approvedRequestCannotBeDenied() {

        AccessRequest request = new AccessRequest();
        request.setId(1L);
        request.setStatus(RequestStatus.APPROVED);

        when(repository.findById(1L))
                .thenReturn(Optional.of(request));

        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> service.denyAccessRequest(1L));

        assertEquals(
                "Only pending requests can be denied",
                exception.getMessage());
    }
    @Test
    void pendingRequestCanBeApproved() {
        setAuthenticatedUser("manager");

        AccessRequest request = new AccessRequest();
        request.setId(1L);
        request.setStatus(RequestStatus.PENDING);

        when(repository.findById(1L))
                .thenReturn(Optional.of(request));

        when(repository.save(any(AccessRequest.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var result = service.approveAccessRequest(1L);

        assertEquals(RequestStatus.APPROVED, result.getStatus());
        assertEquals("manager", result.getDecidedBy());
        assertNotNull(result.getDecisionDate());

        verify(repository, times(1)).save(request);
    }
    @Test
    void pendingRequestCanBeDenied() {
        setAuthenticatedUser("manager");

        AccessRequest request = new AccessRequest();
        request.setId(1L);
        request.setStatus(RequestStatus.PENDING);

        when(repository.findById(1L))
                .thenReturn(Optional.of(request));

        when(repository.save(any(AccessRequest.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var result = service.denyAccessRequest(1L);

        assertEquals(RequestStatus.DENIED, result.getStatus());
        assertEquals("manager", result.getDecidedBy());
        assertNotNull(result.getDecisionDate());

        verify(repository, times(1)).save(request);
    }
    @Test
    void deniedRequestCannotBeApproved() {
        AccessRequest request = new AccessRequest();
        request.setId(1L);
        request.setStatus(RequestStatus.DENIED);

        when(repository.findById(1L))
                .thenReturn(Optional.of(request));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.approveAccessRequest(1L)
        );

        assertEquals("Only pending requests can be approved", exception.getMessage());

        verify(repository, never()).save(any(AccessRequest.class));
    }
    @Test
    void approvingMissingRequestThrowsResourceNotFoundException() {
        when(repository.findById(999L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.approveAccessRequest(999L)
        );

        assertEquals("Access request not found", exception.getMessage());
        verify(repository, never()).save(any(AccessRequest.class));
    }

    @Test
    void denyingMissingRequestThrowsResourceNotFoundException() {
        when(repository.findById(999L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.denyAccessRequest(999L)
        );

        assertEquals("Access request not found", exception.getMessage());
        verify(repository, never()).save(any(AccessRequest.class));
    }
    private void setAuthenticatedUser(String username) {
        var authentication = new UsernamePasswordAuthenticationToken(username, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}