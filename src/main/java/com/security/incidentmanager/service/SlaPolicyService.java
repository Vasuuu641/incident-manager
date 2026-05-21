package com.security.incidentmanager.service;

import com.security.incidentmanager.domain.SlaPolicy;
import com.security.incidentmanager.repository.SlaPolicyRepository;
import com.security.incidentmanager.repository.IncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SlaPolicyService {

    private final SlaPolicyRepository slaPolicyRepository;
    private final IncidentRepository incidentRepository;

    public List<SlaPolicy> findAll() {
        return slaPolicyRepository.findAll();
    }

    public SlaPolicy findById(Long id) {
        return slaPolicyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SLA Policy not found"));
    }

    public Optional<SlaPolicy> findBySeverity(String severity) {
        return slaPolicyRepository.findBySeverity(severity);
    }

    public SlaPolicy save(SlaPolicy slaPolicy) {
        return slaPolicyRepository.save(slaPolicy);
    }

    public void delete(Long id) {
        // Prevent deleting a policy that is still referenced by incidents
        if (!incidentRepository.findBySlaPolicyId(id).isEmpty()) {
            throw new IllegalStateException("Cannot delete SLA policy that is referenced by existing incidents");
        }
        slaPolicyRepository.deleteById(id);
    }
}