package com.security.incidentmanager.service;

import com.security.incidentmanager.domain.SlaPolicy;
import com.security.incidentmanager.repository.SlaPolicyRepository;
import com.security.incidentmanager.repository.IncidentRepository;
import com.security.incidentmanager.exception.BusinessRuleException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class SlaPolicyService
        extends AbstractCrudService<SlaPolicy, SlaPolicyRepository> {

    private final IncidentRepository incidentRepository;

    public SlaPolicyService(SlaPolicyRepository repository,
                            IncidentRepository incidentRepository) {
        super(repository);
        this.incidentRepository = incidentRepository;
    }

    public Optional<SlaPolicy> findBySeverity(String severity) {
        return repository.findBySeverity(severity);
    }

    @Override
    public void delete(Long id) {
        if (!incidentRepository.findBySlaPolicyId(id).isEmpty()) {
            throw new BusinessRuleException(
                    "Cannot delete SLA policy referenced by existing incidents");
        }
        repository.deleteById(id);
    }
}