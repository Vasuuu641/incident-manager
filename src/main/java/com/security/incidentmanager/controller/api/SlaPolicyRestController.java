package com.security.incidentmanager.controller.api;

import com.security.incidentmanager.domain.SlaPolicy;
import com.security.incidentmanager.service.SlaPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sla-policies")
@RequiredArgsConstructor
public class SlaPolicyRestController {

    private final SlaPolicyService slaPolicyService;

    @GetMapping
    public List<SlaPolicy> getAll() {
        return slaPolicyService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SlaPolicy> getById(@PathVariable Long id) {
        return ResponseEntity.ok(slaPolicyService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SlaPolicy> create(@RequestBody SlaPolicy slaPolicy) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(slaPolicyService.save(slaPolicy));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SlaPolicy> update(
            @PathVariable Long id,
            @RequestBody SlaPolicy slaPolicy) {
        slaPolicy.setId(id);
        return ResponseEntity.ok(slaPolicyService.save(slaPolicy));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            slaPolicyService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException ex) {
            // cannot delete due to existing references
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}