package com.security.incidentmanager.service;

import com.security.incidentmanager.domain.Analyst;
import com.security.incidentmanager.repository.AnalystRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AnalystService
        extends AbstractCrudService<Analyst, AnalystRepository> {

    public AnalystService(AnalystRepository repository) {
        super(repository);
    }

    public List<Analyst> findBySpecialization(String specialization) {
        return repository.findBySpecialization(specialization);
    }
}