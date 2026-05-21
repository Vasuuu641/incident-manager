package com.security.incidentmanager.repository;

import com.security.incidentmanager.domain.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    List<Incident> findByStatus(String status);
    List<Incident> findByAnalystId(Long analystId);
    List<Incident> findByStatusIn(List<String> statuses);
    // find incidents by SLA policy id to detect references before deleting a policy
    List<Incident> findBySlaPolicyId(Long slaPolicyId);
}