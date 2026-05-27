package com.security.incidentmanager.controller.api;

import com.security.incidentmanager.domain.IncidentReport;
import com.security.incidentmanager.dto.mapper.IncidentReportMapper;
import com.security.incidentmanager.dto.request.IncidentReportRequestDTO;
import com.security.incidentmanager.dto.response.IncidentReportResponseDTO;
import com.security.incidentmanager.service.IncidentReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class IncidentReportRestController {

    private final IncidentReportService incidentReportService;
    private final IncidentReportMapper incidentReportMapper;

    @GetMapping
    public List<IncidentReportResponseDTO> getAll() {
        return incidentReportService.findAll()
                .stream()
                .map(incidentReportMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidentReportResponseDTO> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                incidentReportMapper.toResponseDTO(
                        incidentReportService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<IncidentReportResponseDTO> create(
            @RequestBody IncidentReportRequestDTO dto) {
        IncidentReport saved = incidentReportService.save(
                incidentReportMapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(incidentReportMapper.toResponseDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncidentReportResponseDTO> update(
            @PathVariable Long id,
            @RequestBody IncidentReportRequestDTO dto) {
        IncidentReport report = incidentReportMapper.toEntity(dto);
        report.setId(id);
        return ResponseEntity.ok(
                incidentReportMapper.toResponseDTO(
                        incidentReportService.save(report)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        incidentReportService.delete(id);
        return ResponseEntity.noContent().build();
    }
}