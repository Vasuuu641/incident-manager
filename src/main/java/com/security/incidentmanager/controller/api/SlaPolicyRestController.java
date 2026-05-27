package com.security.incidentmanager.controller.api;

import com.security.incidentmanager.domain.SlaPolicy;
import com.security.incidentmanager.dto.mapper.SlaPolicyMapper;
import com.security.incidentmanager.dto.request.SlaPolicyRequestDTO;
import com.security.incidentmanager.dto.response.SlaPolicyResponseDTO;
import com.security.incidentmanager.service.SlaPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sla-policies")
@RequiredArgsConstructor
public class SlaPolicyRestController {

    private final SlaPolicyService slaPolicyService;
    private final SlaPolicyMapper slaPolicyMapper;

    @GetMapping
    public List<SlaPolicyResponseDTO> getAll() {
        return slaPolicyService.findAll()
                .stream()
                .map(slaPolicyMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SlaPolicyResponseDTO> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                slaPolicyMapper.toResponseDTO(
                        slaPolicyService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<SlaPolicyResponseDTO> create(
            @RequestBody SlaPolicyRequestDTO dto) {
        SlaPolicy saved = slaPolicyService.save(
                slaPolicyMapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(slaPolicyMapper.toResponseDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SlaPolicyResponseDTO> update(
            @PathVariable Long id,
            @RequestBody SlaPolicyRequestDTO dto) {
        SlaPolicy policy = slaPolicyMapper.toEntity(dto);
        policy.setId(id);
        return ResponseEntity.ok(
                slaPolicyMapper.toResponseDTO(
                        slaPolicyService.save(policy)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            slaPolicyService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}