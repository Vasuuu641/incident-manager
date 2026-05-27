package com.security.incidentmanager.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AssetResponseDTO {
    private Long id;
    private String hostname;
    private String ipAddress;
    private String assetType;
    private Long incidentId;
    private String incidentTitle;
    private LocalDateTime createdAt;
}