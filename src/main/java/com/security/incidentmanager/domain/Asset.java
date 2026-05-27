package com.security.incidentmanager.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Asset extends BaseEntity {

    private String hostname;
    private String ipAddress;
    private String assetType; // SERVER, WORKSTATION, NETWORK_DEVICE

    @ManyToOne
    @JoinColumn(name = "incident_id")
    @lombok.EqualsAndHashCode.Exclude
    @lombok.ToString.Exclude
    private Incident incident;
}