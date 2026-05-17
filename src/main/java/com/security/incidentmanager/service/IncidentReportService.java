package com.security.incidentmanager.service;

import com.security.incidentmanager.domain.IncidentReport;
import com.security.incidentmanager.repository.IncidentReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidentReportService {
    private final IncidentReportRepository incidentReportRepository;

    public List<IncidentReport> findAll() {
        return incidentReportRepository.findAll();
    }

    public IncidentReport save(IncidentReport incidentReport) {
        return incidentReportRepository.save(incidentReport);
    }

    public IncidentReport findById(Long id) {
        return incidentReportRepository.findById(id).orElseThrow(() -> new RuntimeException("Incident not found"));
    }

    public void delete(Long id) {
        incidentReportRepository.deleteById(id);
    }

}
