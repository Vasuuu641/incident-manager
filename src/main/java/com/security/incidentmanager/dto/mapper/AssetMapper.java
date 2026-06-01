package com.security.incidentmanager.dto.mapper;

import com.security.incidentmanager.domain.Asset;
import com.security.incidentmanager.dto.request.AssetRequestDTO;
import com.security.incidentmanager.dto.response.AssetResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class AssetMapper
        implements AbstractMapper<Asset, AssetRequestDTO, AssetResponseDTO> {

    @Override
    public AssetResponseDTO toResponseDTO(Asset asset) {
        AssetResponseDTO dto = new AssetResponseDTO();
        dto.setId(asset.getId());
        dto.setHostname(asset.getHostname());
        dto.setIpAddress(asset.getIpAddress());
        dto.setAssetType(asset.getAssetType());
        dto.setCreatedAt(asset.getCreatedAt());
        if (asset.getIncident() != null) {
            dto.setIncidentId(asset.getIncident().getId());
            dto.setIncidentTitle(asset.getIncident().getTitle());
        }
        return dto;
    }

    @Override
    public Asset toEntity(AssetRequestDTO dto) {
        Asset asset = new Asset();
        asset.setHostname(dto.getHostname());
        asset.setIpAddress(dto.getIpAddress());
        asset.setAssetType(dto.getAssetType());
        return asset;
    }
}