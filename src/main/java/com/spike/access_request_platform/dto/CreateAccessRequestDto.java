package com.spike.access_request_platform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateAccessRequestDto {

    @NotBlank
    private String requesterName;

    @NotBlank
    private String systemName;

    @NotBlank
    private String accessLevel;

    @NotBlank
    @Size(max = 1000)
    private String businessJustification;
}