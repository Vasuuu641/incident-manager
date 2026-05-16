package com.security.incidentmanager.service;

import com.security.incidentmanager.domain.Asset;
import com.security.incidentmanager.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {
    private final AssetRepository assetRepository;

    public Asset save(Asset asset) {
        return assetRepository.save(asset);
    }

    public List<Asset> findAll() {
        return assetRepository.findAll();
    }

    public Asset findById(Long id) {
        return assetRepository.findById(id).orElseThrow(() -> new RuntimeException("Asset not found"));
    }

    public List<Asset> findByIncidentId(Long incidentId) {
        return assetRepository.findByIncidentId(incidentId);
    }

    public void delete(Long id) {
        assetRepository.deleteById(id);
    }
}