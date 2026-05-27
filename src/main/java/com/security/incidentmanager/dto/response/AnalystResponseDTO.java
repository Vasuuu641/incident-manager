package com.security.incidentmanager.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AnalystResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String specialization;
    private LocalDateTime createdAt;
    private int incidentCount;
}