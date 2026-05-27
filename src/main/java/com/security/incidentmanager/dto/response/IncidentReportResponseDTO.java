package com.security.incidentmanager.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class IncidentReportResponseDTO {
    private Long id;
    private String findings;
    private String recommendations;
    private String severity;
    private LocalDateTime createdAt;
    private Long incidentId;
    private String incidentTitle;
}