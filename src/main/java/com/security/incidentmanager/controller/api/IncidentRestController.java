package com.security.incidentmanager.controller.api;

import com.security.incidentmanager.domain.Incident;
import com.security.incidentmanager.service.IncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/incidents")
@RequiredArgsConstructor
public class IncidentRestController {

    private final IncidentService incidentService;

    @GetMapping
    public List<Incident> getAll() {
        return incidentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Incident> getById(@PathVariable Long id) {
        return ResponseEntity.ok(incidentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Incident> create(@RequestBody Incident incident) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(incidentService.save(incident));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Incident> update(
            @PathVariable Long id,
            @RequestBody Incident incident) {
        incident.setId(id);
        return ResponseEntity.ok(incidentService.save(incident));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        incidentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}