package com.security.incidentmanager.dto.mapper;

import com.security.incidentmanager.domain.SlaPolicy;
import com.security.incidentmanager.dto.request.SlaPolicyRequestDTO;
import com.security.incidentmanager.dto.response.SlaPolicyResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class SlaPolicyMapper
        implements AbstractMapper<SlaPolicy, SlaPolicyRequestDTO, SlaPolicyResponseDTO> {

    @Override
    public SlaPolicyResponseDTO toResponseDTO(SlaPolicy policy) {
        SlaPolicyResponseDTO dto = new SlaPolicyResponseDTO();
        dto.setId(policy.getId());
        dto.setSeverity(policy.getSeverity());
        dto.setResolutionHours(policy.getResolutionHours());
        dto.setEscalationHours(policy.getEscalationHours());
        dto.setCreatedAt(policy.getCreatedAt());
        dto.setIncidentCount(
                policy.getIncidents() != null ?
                        policy.getIncidents().size() : 0);
        return dto;
    }


    @Override
    public SlaPolicy toEntity(SlaPolicyRequestDTO dto) {
        SlaPolicy policy = new SlaPolicy();
        policy.setSeverity(dto.getSeverity());
        policy.setResolutionHours(dto.getResolutionHours());
        policy.setEscalationHours(dto.getEscalationHours());
        return policy;
    }
}