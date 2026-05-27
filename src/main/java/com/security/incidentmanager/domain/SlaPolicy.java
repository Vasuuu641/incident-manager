package com.security.incidentmanager.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class SlaPolicy extends BaseEntity {

    private String severity; // CRITICAL, HIGH, MEDIUM, LOW

    private int resolutionHours; // e.g. CRITICAL = 4 hours

    private int escalationHours; // escalate if not resolved in X hours

    // OneToMany — one policy applies to many incidents
    @OneToMany(mappedBy = "slaPolicy")
    private List<Incident> incidents;
}