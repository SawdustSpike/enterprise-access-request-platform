package com.spike.access_request_platform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class CreateAccessRequestDto {

    @NotBlank
    @Size(max = 100)
    private String requesterName;

    @NotBlank
    @Size(max = 100)
    private String systemName;

    @NotBlank
    @Size(max = 50)
    private String accessLevel;

    @NotBlank
    @Size(max = 1000)
    private String businessJustification;
}