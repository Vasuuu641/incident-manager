package com.security.incidentmanager.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Incident extends BaseEntity {

    @Column(nullable = false)
    private String title;

    private String description;

    private String status; // OPEN, IN_PROGRESS, RESOLVED, CLOSED

    private LocalDateTime detectedAt;

    private LocalDateTime slaDeadline;

    private boolean escalated = false;

    // OneToOne — each incident has one detailed report
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "report_id")
    @lombok.EqualsAndHashCode.Exclude
    @lombok.ToString.Exclude
    private IncidentReport report;

    // ManyToOne — many incidents assigned to one analyst
    @ManyToOne
    @JoinColumn(name = "analyst_id")
    @lombok.EqualsAndHashCode.Exclude
    @lombok.ToString.Exclude
    private Analyst analyst;

    // ManyToOne — many incidents governed by one SLA policy
    @ManyToOne
    @JoinColumn(name = "sla_policy_id")
    private SlaPolicy slaPolicy;

    // OneToMany — one incident has many affected assets
    @OneToMany(mappedBy = "incident", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @lombok.EqualsAndHashCode.Exclude
    @lombok.ToString.Exclude
    private List<Asset> assets;

    // ManyToMany — incidents share tags with other incidents
    @ManyToMany
    @JoinTable(
            name = "incident_tags",
            joinColumns = @JoinColumn(name = "incident_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @lombok.EqualsAndHashCode.Exclude
    @lombok.ToString.Exclude
    private Set<Tag> tags;
}