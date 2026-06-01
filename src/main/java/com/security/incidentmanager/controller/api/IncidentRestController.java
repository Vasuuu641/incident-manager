package com.security.incidentmanager.controller.api;

import com.security.incidentmanager.domain.Incident;
import com.security.incidentmanager.domain.Tag;
import com.security.incidentmanager.dto.mapper.IncidentMapper;
import com.security.incidentmanager.dto.request.IncidentRequestDTO;
import com.security.incidentmanager.dto.response.IncidentResponseDTO;
import com.security.incidentmanager.dto.mapper.AbstractMapper;
import com.security.incidentmanager.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/incidents")
public class IncidentRestController
        extends AbstractRestController<Incident, IncidentRequestDTO, IncidentResponseDTO> {

    private final IncidentService incidentService;
    private final AnalystService analystService;
    private final TagService tagService;
    private final SlaPolicyService slaPolicyService;
    private final IncidentMapper incidentMapper;

    public IncidentRestController(IncidentService incidentService,
                                  AnalystService analystService,
                                  TagService tagService,
                                  SlaPolicyService slaPolicyService,
                                  IncidentMapper incidentMapper) {
        this.incidentService = incidentService;
        this.analystService = analystService;
        this.tagService = tagService;
        this.slaPolicyService = slaPolicyService;
        this.incidentMapper = incidentMapper;
    }

    // required by AbstractRestController
    @Override
    protected CrudService<Incident, Long> getService() {
        return incidentService;
    }

    // required by AbstractRestController
    @Override
    protected AbstractMapper<Incident, IncidentRequestDTO,
            IncidentResponseDTO> getMapper() {
        return incidentMapper;
    }

    @Override
    @PostMapping
    public ResponseEntity<IncidentResponseDTO> create(
            @RequestBody IncidentRequestDTO dto) {
        Incident incident = incidentMapper.toEntity(dto);
        resolveRelationships(incident, dto);
        Incident saved = incidentService.save(incident);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(incidentMapper.toResponseDTO(saved));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<IncidentResponseDTO> update(
            @PathVariable Long id,
            @RequestBody IncidentRequestDTO dto) {
        Incident incident = incidentMapper.toEntity(dto);
        incident.setId(id);
        resolveRelationships(incident, dto);
        return ResponseEntity.ok(
                incidentMapper.toResponseDTO(
                        incidentService.save(incident)));
    }

    // resolves IDs from the request DTO into managed entities
    private void resolveRelationships(Incident incident,
                                      IncidentRequestDTO dto) {
        if (dto.getAnalystId() != null) {
            incident.setAnalyst(
                    analystService.findById(dto.getAnalystId()));
        }
        if (dto.getSlaPolicyId() != null) {
            incident.setSlaPolicy(
                    slaPolicyService.findById(dto.getSlaPolicyId()));
        }
        if (dto.getTagIds() != null && !dto.getTagIds().isEmpty()) {
            Set<Tag> tags = dto.getTagIds()
                    .stream()
                    .map(tagService::findById)
                    .collect(Collectors.toSet());
            incident.setTags(tags);
        }
    }
}