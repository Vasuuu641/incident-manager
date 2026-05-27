package com.security.incidentmanager.dto.request;

import lombok.Data;

@Data
public class IncidentReportRequestDTO {
    private String findings;
    private String recommendations;
    private String severity;
}