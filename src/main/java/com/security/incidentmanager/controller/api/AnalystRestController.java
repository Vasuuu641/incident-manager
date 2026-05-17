package com.security.incidentmanager.controller.api;

import com.security.incidentmanager.domain.Analyst;
import com.security.incidentmanager.service.AnalystService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/analyst")
@RequiredArgsConstructor
public class AnalystRestController {

    private final AnalystService analystService;

    @GetMapping
    public List<Analyst> getAll() {
        return analystService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Analyst> getById(@PathVariable Long id) {
        return ResponseEntity.ok(analystService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Analyst> create(@RequestBody Analyst analyst) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(analystService.save(analyst));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Analyst> update(
            @PathVariable Long id,
            @RequestBody Analyst analyst) {
        analyst.setId(id);
        return ResponseEntity.ok(analystService.save(analyst));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        analystService.delete(id);
        return ResponseEntity.noContent().build();
    }
}