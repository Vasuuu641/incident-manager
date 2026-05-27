package com.security.incidentmanager.controller.api;

import com.security.incidentmanager.domain.Asset;
import com.security.incidentmanager.dto.mapper.AssetMapper;
import com.security.incidentmanager.dto.request.AssetRequestDTO;
import com.security.incidentmanager.dto.response.AssetResponseDTO;
import com.security.incidentmanager.service.AssetService;
import com.security.incidentmanager.service.IncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetRestController {

    private final AssetService assetService;
    private final IncidentService incidentService;
    private final AssetMapper assetMapper;

    @GetMapping
    public List<AssetResponseDTO> getAll() {
        return assetService.findAll()
                .stream()
                .map(assetMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetResponseDTO> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                assetMapper.toResponseDTO(
                        assetService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<AssetResponseDTO> create(
            @RequestBody AssetRequestDTO dto) {
        Asset asset = assetMapper.toEntity(dto);
        if (dto.getIncidentId() != null) {
            asset.setIncident(
                    incidentService.findById(dto.getIncidentId()));
        }
        Asset saved = assetService.save(asset);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assetMapper.toResponseDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetResponseDTO> update(
            @PathVariable Long id,
            @RequestBody AssetRequestDTO dto) {
        Asset asset = assetMapper.toEntity(dto);
        asset.setId(id);
        if (dto.getIncidentId() != null) {
            asset.setIncident(
                    incidentService.findById(dto.getIncidentId()));
        }
        return ResponseEntity.ok(
                assetMapper.toResponseDTO(assetService.save(asset)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        assetService.delete(id);
        return ResponseEntity.noContent().build();
    }
}