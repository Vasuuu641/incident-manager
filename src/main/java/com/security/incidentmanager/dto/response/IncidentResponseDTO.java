package com.security.incidentmanager.dto.response;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class IncidentResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDateTime detectedAt;
    private LocalDateTime slaDeadline;
    private boolean escalated;
    private LocalDateTime createdAt;

    // flat references — no nested objects, no circular reference
    private Long analystId;
    private String analystName;

    private Long slaPolicyId;
    private String slaPolicySeverity;
    private Integer slaPolicyResolutionHours;

    private Long reportId;
    private String reportSeverity;

    private Set<String> tagNames;
    private List<String> assetHostnames;
}