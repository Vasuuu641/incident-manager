package com.security.incidentmanager.service;

import com.security.incidentmanager.domain.SlaPolicy;
import com.security.incidentmanager.repository.SlaPolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SlaPolicyService {

    private final SlaPolicyRepository slaPolicyRepository;

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
        slaPolicyRepository.deleteById(id);
    }
}