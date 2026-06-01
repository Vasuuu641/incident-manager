package com.security.incidentmanager.dto.mapper;

import com.security.incidentmanager.domain.BaseEntity;

// T  = entity type       (e.g. Analyst)
// RQ = request DTO type  (e.g. AnalystRequestDTO)
// RS = response DTO type (e.g. AnalystResponseDTO)
public interface AbstractMapper<T extends BaseEntity, RQ, RS> {
    RS toResponseDTO(T entity);
    T toEntity(RQ dto);
}