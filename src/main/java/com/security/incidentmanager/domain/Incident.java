package com.security.incidentmanager.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private String status; // OPEN, IN_PROGRESS, RESOLVED, CLOSED

    private LocalDateTime detectedAt;

    // OneToOne — each incident has one detailed report
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "report_id")
    private IncidentReport report;

    // ManyToOne — many incidents assigned to one analyst
    @ManyToOne
    @JoinColumn(name = "analyst_id")
    private Analyst analyst;

    // ManyToMany — incidents share tags with other incidents
    @ManyToMany
    @JoinTable(
            name = "incident_tags",
            joinColumns = @JoinColumn(name = "incident_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;
}