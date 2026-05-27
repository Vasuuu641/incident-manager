package com.security.incidentmanager.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Tag extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name; // e.g. "Ransomware", "Phishing", "DDoS"

    private String color; // e.g. "#FF0000" for UI badge color

    @ManyToMany(mappedBy = "tags")
    @lombok.EqualsAndHashCode.Exclude
    @lombok.ToString.Exclude
    private Set<Incident> incidents;
}