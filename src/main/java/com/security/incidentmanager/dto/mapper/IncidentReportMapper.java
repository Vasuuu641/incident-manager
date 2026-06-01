package com.security.incidentmanager.dto.mapper;

import com.security.incidentmanager.domain.IncidentReport;
import com.security.incidentmanager.dto.request.IncidentReportRequestDTO;
import com.security.incidentmanager.dto.response.IncidentReportResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class IncidentReportMapper
        implements AbstractMapper<IncidentReport, IncidentReportRequestDTO, IncidentReportResponseDTO> {

    @Override
    public IncidentReportResponseDTO toResponseDTO(
            IncidentReport report) {
        IncidentReportResponseDTO dto =
                new IncidentReportResponseDTO();
        dto.setId(report.getId());
        dto.setFindings(report.getFindings());
        dto.setRecommendations(report.getRecommendations());
        dto.setSeverity(report.getSeverity());
        dto.setCreatedAt(report.getCreatedAt());
        if (report.getIncident() != null) {
            dto.setIncidentId(report.getIncident().getId());
            dto.setIncidentTitle(report.getIncident().getTitle());
        }
        return dto;
    }

    @Override
    public IncidentReport toEntity(IncidentReportRequestDTO dto) {
        IncidentReport report = new IncidentReport();
        report.setFindings(dto.getFindings());
        report.setRecommendations(dto.getRecommendations());
        report.setSeverity(dto.getSeverity());
        return report;
    }
}