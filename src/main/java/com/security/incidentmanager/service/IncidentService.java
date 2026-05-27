package com.security.incidentmanager.service;

import com.security.incidentmanager.domain.Analyst;
import com.security.incidentmanager.repository.AnalystRepository;
import com.security.incidentmanager.domain.Incident;
import com.security.incidentmanager.repository.IncidentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IncidentService
        extends AbstractCrudService<Incident, IncidentRepository>{

    private final AnalystRepository analystRepository;

    public IncidentService(IncidentRepository repository,
                           AnalystRepository analystRepository) {
        super(repository);
        this.analystRepository = analystRepository;
    }

    @Override
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
        return repository.save(incident);
    }

    public List<Incident> findByStatus(String status) {
        return repository.findByStatus(status);
    }

}