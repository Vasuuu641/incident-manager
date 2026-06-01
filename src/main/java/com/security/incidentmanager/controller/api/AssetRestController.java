package com.security.incidentmanager.controller.api;

import com.security.incidentmanager.domain.Asset;
import com.security.incidentmanager.dto.mapper.AssetMapper;
import com.security.incidentmanager.dto.request.AssetRequestDTO;
import com.security.incidentmanager.dto.response.AssetResponseDTO;
import com.security.incidentmanager.service.AssetService;
import com.security.incidentmanager.service.CrudService;
import com.security.incidentmanager.service.IncidentService;
import com.security.incidentmanager.dto.mapper.AbstractMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assets")
public class AssetRestController
        extends AbstractRestController<Asset, AssetRequestDTO, AssetResponseDTO> {

    private final AssetService assetService;
    private final IncidentService incidentService;
    private final AssetMapper assetMapper;

    public AssetRestController(AssetService assetService,
                               IncidentService incidentService,
                               AssetMapper assetMapper) {
        this.assetService = assetService;
        this.incidentService = incidentService;
        this.assetMapper = assetMapper;
    }

    @Override
    protected CrudService<Asset, Long> getService() {
        return assetService;
    }

    @Override
    protected AbstractMapper<Asset, AssetRequestDTO, AssetResponseDTO> getMapper() {
        return assetMapper;
    }

    @Override
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

    @Override
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

}