package com.security.incidentmanager.service;

import com.security.incidentmanager.domain.IncidentReport;
import com.security.incidentmanager.repository.IncidentReportRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IncidentReportService
        extends AbstractCrudService<IncidentReport, IncidentReportRepository> {

    public IncidentReportService(IncidentReportRepository repository) {
        super(repository);
    }

}
