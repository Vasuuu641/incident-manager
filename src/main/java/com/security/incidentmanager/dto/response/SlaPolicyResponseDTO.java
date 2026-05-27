package com.security.incidentmanager.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SlaPolicyResponseDTO {
    private Long id;
    private String severity;
    private int resolutionHours;
    private int escalationHours;
    private LocalDateTime createdAt;
    private int incidentCount;
}