package com.security.incidentmanager.service;

import com.security.incidentmanager.domain.Analyst;
import com.security.incidentmanager.repository.AnalystRepository;
import com.security.incidentmanager.domain.Incident;
import com.security.incidentmanager.repository.IncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidentService {

    // Import from repository
    private final IncidentRepository incidentRepository;
    private final AnalystRepository analystRepository;

    public List<Incident> findAll() {
        return incidentRepository.findAll();
    }

    public Incident findById(Long id) {
        return incidentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incident not found"));
    }

    public Incident save(Incident incident) {
        if (incident.getAnalyst() != null
                && incident.getAnalyst().getId() != null) {
            Analyst analyst = analystRepository
                    .findById(incident.getAnalyst().getId())
                    .orElseThrow(() -> new RuntimeException("Analyst not found"));
            incident.setAnalyst(analyst);
        } else {
            incident.setAnalyst(null);
        }
        return incidentRepository.save(incident);
    }


    public void delete(Long id) {
        incidentRepository.deleteById(id);
    }

    public List<Incident> findByStatus(String status) {
        return incidentRepository.findByStatus(status);
    }

    public List<Incident> findByAnalystId(Long analystId) {
        return incidentRepository.findByAnalystId(analystId);
    }
}