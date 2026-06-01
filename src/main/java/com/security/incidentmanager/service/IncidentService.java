package com.security.incidentmanager.service;

import com.security.incidentmanager.domain.Analyst;
import com.security.incidentmanager.domain.Asset;
import com.security.incidentmanager.repository.AnalystRepository;
import com.security.incidentmanager.domain.Incident;
import com.security.incidentmanager.repository.IncidentRepository;
import com.security.incidentmanager.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class IncidentService
        extends AbstractCrudService<Incident, IncidentRepository> {

    private final AnalystRepository analystRepository;

    public IncidentService(IncidentRepository repository,
                           AnalystRepository analystRepository) {
        super(repository);
        this.analystRepository = analystRepository;
    }

    @Transactional
    public void deleteAsset(Long incidentId, Long assetId) {

        Incident incident = repository.findById(incidentId)
                .orElseThrow();

        System.out.println("BEFORE REMOVE: " + incident.getAssets().size());

        boolean removed = incident.getAssets()
                .removeIf(a -> a.getId().equals(assetId));

        System.out.println("REMOVED? " + removed);
        System.out.println("AFTER REMOVE: " + incident.getAssets().size());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Incident> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Incident findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Incident not found with id: " + id));
    }

    @Override
    @Transactional
    public Incident save(Incident incident) {
        if (incident.getAnalyst() != null
                && incident.getAnalyst().getId() != null) {
            Analyst analyst = analystRepository
                    .findById(incident.getAnalyst().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Analyst not found"));
            incident.setAnalyst(analyst);
        } else {
            incident.setAnalyst(null);
        }
        return repository.save(incident);
    }

    @Transactional(readOnly = true)
    public List<Incident> findByStatus(String status) {
        return repository.findByStatus(status);
    }
}