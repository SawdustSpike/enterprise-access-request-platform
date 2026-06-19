package com.spike.access_request_platform.repository;

import com.spike.access_request_platform.model.AccessRequest;
import com.spike.access_request_platform.model.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessRequestRepository extends JpaRepository<AccessRequest, Long> {

    List<AccessRequest> findByStatus(RequestStatus status);

    List<AccessRequest> findByRequesterNameContainingIgnoreCase(String requesterName);

    List<AccessRequest> findBySystemNameContainingIgnoreCase(String systemName);
}