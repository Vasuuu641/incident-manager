package com.security.incidentmanager.dto.request;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class IncidentRequestDTO {
    private String title;
    private String description;
    private String status;
    private LocalDateTime detectedAt;
    private Long analystId;
    private Long slaPolicyId;
    private Set<Long> tagIds;
}