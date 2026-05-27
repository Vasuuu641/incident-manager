package com.security.incidentmanager.controller.api;

import com.security.incidentmanager.domain.Incident;
import com.security.incidentmanager.domain.Tag;
import com.security.incidentmanager.dto.mapper.IncidentMapper;
import com.security.incidentmanager.dto.request.IncidentRequestDTO;
import com.security.incidentmanager.dto.response.IncidentResponseDTO;
import com.security.incidentmanager.service.AnalystService;
import com.security.incidentmanager.service.IncidentService;
import com.security.incidentmanager.service.SlaPolicyService;
import com.security.incidentmanager.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/incidents")
@RequiredArgsConstructor
public class IncidentRestController {

    private final IncidentService incidentService;
    private final AnalystService analystService;
    private final TagService tagService;
    private final SlaPolicyService slaPolicyService;
    private final IncidentMapper incidentMapper;

    @GetMapping
    public List<IncidentResponseDTO> getAll() {
        return incidentService.findAll()
                .stream()
                .map(incidentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidentResponseDTO> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                incidentMapper.toResponseDTO(
                        incidentService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<IncidentResponseDTO> create(
            @RequestBody IncidentRequestDTO dto) {
        Incident incident = incidentMapper.toEntity(dto);
        resolveRelationships(incident, dto);
        Incident saved = incidentService.save(incident);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(incidentMapper.toResponseDTO(saved));
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        incidentService.delete(id);
        return ResponseEntity.noContent().build();
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