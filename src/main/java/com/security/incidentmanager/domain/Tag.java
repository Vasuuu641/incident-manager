package com.security.incidentmanager.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Entity
@Data
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // e.g. "Ransomware", "Phishing", "DDoS"

    private String color; // e.g. "#FF0000" for UI badge color

    @ManyToMany(mappedBy = "tags")
    @lombok.EqualsAndHashCode.Exclude
    @lombok.ToString.Exclude
    private Set<Incident> incidents;
}