package com.security.incidentmanager.controller.api;

import com.security.incidentmanager.domain.Analyst;
import com.security.incidentmanager.dto.mapper.AnalystMapper;
import com.security.incidentmanager.dto.request.AnalystRequestDTO;
import com.security.incidentmanager.dto.response.AnalystResponseDTO;
import com.security.incidentmanager.service.AnalystService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/analysts")
@RequiredArgsConstructor
public class AnalystRestController {

    private final AnalystService analystService;
    private final AnalystMapper analystMapper;

    @GetMapping
    public List<AnalystResponseDTO> getAll() {
        return analystService.findAll()
                .stream()
                .map(analystMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnalystResponseDTO> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                analystMapper.toResponseDTO(
                        analystService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<AnalystResponseDTO> create(
            @RequestBody AnalystRequestDTO dto) {
        Analyst saved = analystService.save(
                analystMapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(analystMapper.toResponseDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnalystResponseDTO> update(
            @PathVariable Long id,
            @RequestBody AnalystRequestDTO dto) {
        Analyst analyst = analystMapper.toEntity(dto);
        analyst.setId(id);
        return ResponseEntity.ok(
                analystMapper.toResponseDTO(
                        analystService.save(analyst)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        analystService.delete(id);
        return ResponseEntity.noContent().build();
    }
}