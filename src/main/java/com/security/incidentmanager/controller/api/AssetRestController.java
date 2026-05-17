package com.security.incidentmanager.controller.api;

import com.security.incidentmanager.domain.Asset;
import com.security.incidentmanager.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetRestController {

    private final AssetService assetService;

    @GetMapping
    public List<Asset> getAll() {
        return assetService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asset> getById(@PathVariable Long id) {
        return ResponseEntity.ok(assetService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Asset> create(@RequestBody Asset asset) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assetService.save(asset));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Asset> update(
            @PathVariable Long id,
            @RequestBody Asset asset) {
        asset.setId(id);
        return ResponseEntity.ok(assetService.save(asset));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        assetService.delete(id);
        return ResponseEntity.noContent().build();
    }
}