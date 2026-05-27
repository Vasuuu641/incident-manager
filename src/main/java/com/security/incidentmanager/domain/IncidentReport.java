package com.security.incidentmanager.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class IncidentReport extends BaseEntity{

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