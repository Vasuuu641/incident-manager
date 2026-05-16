package com.security.incidentmanager.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hostname;
    private String ipAddress;
    private String assetType; // SERVER, WORKSTATION, NETWORK_DEVICE

    @ManyToOne
    @JoinColumn(name = "incident_id")
    private Incident incident;
}