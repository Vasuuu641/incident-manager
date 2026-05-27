package com.security.incidentmanager.dto.request;

import lombok.Data;

@Data
public class AssetRequestDTO {
    private String hostname;
    private String ipAddress;
    private String assetType;
    private Long incidentId;
}