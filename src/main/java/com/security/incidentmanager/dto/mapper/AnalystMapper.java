package com.security.incidentmanager.dto.mapper;

import com.security.incidentmanager.domain.Analyst;
import com.security.incidentmanager.dto.request.AnalystRequestDTO;
import com.security.incidentmanager.dto.response.AnalystResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class AnalystMapper {

    public AnalystResponseDTO toResponseDTO(Analyst analyst) {
        AnalystResponseDTO dto = new AnalystResponseDTO();
        dto.setId(analyst.getId());
        dto.setName(analyst.getName());
        dto.setEmail(analyst.getEmail());
        dto.setSpecialization(analyst.getSpecialization());
        dto.setCreatedAt(analyst.getCreatedAt());
        dto.setIncidentCount(
                analyst.getIncidents() != null ?
                        analyst.getIncidents().size() : 0);
        return dto;
    }

    public Analyst toEntity(AnalystRequestDTO dto) {
        Analyst analyst = new Analyst();
        analyst.setName(dto.getName());
        analyst.setEmail(dto.getEmail());
        analyst.setSpecialization(dto.getSpecialization());
        return analyst;
    }
}