package com.security.incidentmanager.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class IncidentReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 5000)
    private String findings;

    @Column(length = 5000)
    private String recommendations;

    private String severity; // LOW, MEDIUM, HIGH, CRITICAL

    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "report")
    @lombok.EqualsAndHashCode.Exclude
    @lombok.ToString.Exclude
    private Incident incident; // OneToOne back reference
}