package com.security.incidentmanager.dto.mapper;

import com.security.incidentmanager.domain.Asset;
import com.security.incidentmanager.domain.Incident;
import com.security.incidentmanager.domain.Tag;
import com.security.incidentmanager.dto.request.IncidentRequestDTO;
import com.security.incidentmanager.dto.response.IncidentResponseDTO;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class IncidentMapper
        implements AbstractMapper<Incident, IncidentRequestDTO, IncidentResponseDTO> {

    @Override
    public IncidentResponseDTO toResponseDTO(Incident incident) {
        IncidentResponseDTO dto = new IncidentResponseDTO();
        dto.setId(incident.getId());
        dto.setTitle(incident.getTitle());
        dto.setDescription(incident.getDescription());
        dto.setStatus(incident.getStatus());
        dto.setDetectedAt(incident.getDetectedAt());
        dto.setSlaDeadline(incident.getSlaDeadline());
        dto.setEscalated(incident.isEscalated());
        dto.setCreatedAt(incident.getCreatedAt());

        // flat analyst reference
        if (incident.getAnalyst() != null) {
            dto.setAnalystId(incident.getAnalyst().getId());
            dto.setAnalystName(incident.getAnalyst().getName());
        }

        // flat SLA policy reference
        if (incident.getSlaPolicy() != null) {
            dto.setSlaPolicyId(incident.getSlaPolicy().getId());
            dto.setSlaPolicySeverity(
                    incident.getSlaPolicy().getSeverity());
            dto.setSlaPolicyResolutionHours(
                    incident.getSlaPolicy().getResolutionHours());
        }

        // flat report reference
        if (incident.getReport() != null) {
            dto.setReportId(incident.getReport().getId());
            dto.setReportSeverity(
                    incident.getReport().getSeverity());
        }

        // tag names only — no full tag objects
        if (incident.getTags() != null) {
            dto.setTagNames(incident.getTags()
                    .stream()
                    .map(Tag::getName)
                    .collect(Collectors.toSet()));
        }

        // asset hostnames only — no full asset objects
        if (incident.getAssets() != null) {
            dto.setAssetHostnames(incident.getAssets()
                    .stream()
                    .map(Asset::getHostname)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    @Override
    public Incident toEntity(IncidentRequestDTO dto) {
        Incident incident = new Incident();
        incident.setTitle(dto.getTitle());
        incident.setDescription(dto.getDescription());
        incident.setStatus(dto.getStatus());
        incident.setDetectedAt(dto.getDetectedAt());
        return incident;
    }
}