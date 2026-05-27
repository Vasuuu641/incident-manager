package com.security.incidentmanager.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TagResponseDTO {
    private Long id;
    private String name;
    private String color;
    private LocalDateTime createdAt;
}