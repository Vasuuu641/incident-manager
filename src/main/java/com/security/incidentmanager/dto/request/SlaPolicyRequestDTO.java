package com.security.incidentmanager.dto.request;

import lombok.Data;

@Data
public class SlaPolicyRequestDTO {
    private String severity;
    private int resolutionHours;
    private int escalationHours;
}