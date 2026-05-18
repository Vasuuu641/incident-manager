package com.security.incidentmanager.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Analyst {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String email;

    private String specialization; // e.g. "Network Security", "Malware Analysis"

    @OneToMany(mappedBy = "analyst", cascade = CascadeType.ALL)
    @lombok.EqualsAndHashCode.Exclude
    @lombok.ToString.Exclude
    private List<Incident> incidents; // OneToMany
}