package com.security.incidentmanager.controller.api;

import com.security.incidentmanager.domain.IncidentReport;
import com.security.incidentmanager.service.IncidentReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/incident-reports")
@RequiredArgsConstructor
public class IncidentReportRestController {

    private final IncidentReportService incidentReportService;

    @GetMapping
    public List<IncidentReport> getAll() {
        return incidentReportService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidentReport> getById(@PathVariable Long id) {
        return ResponseEntity.ok(incidentReportService.findById(id));
    }

    @PostMapping
    public ResponseEntity<IncidentReport> create(@RequestBody IncidentReport incidentReport) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(incidentReportService.save(incidentReport));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncidentReport> update(
            @PathVariable Long id,
            @RequestBody IncidentReport incident) {
        incident.setId(id);
        return ResponseEntity.ok(incidentReportService.save(incident));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        incidentReportService.delete(id);
        return ResponseEntity.noContent().build();
    }
}