package com.security.incidentmanager.service;

import com.security.incidentmanager.domain.Analyst;
import com.security.incidentmanager.repository.AnalystRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalystService {
    //Import from repository
    private final  AnalystRepository analystRepository;

    //do each method in analyst repo
    //find all
    public List<Analyst> findAll() {
        return analystRepository.findAll();
    }

    public  Analyst findById(Long id) {
        return analystRepository.findById(id).orElseThrow(() -> new RuntimeException("Analyst not found"));
    }

    public List<Analyst> findBySpecialization(String specialization) {
        return analystRepository.findBySpecialization(specialization);
    }

    public Analyst save(Analyst analyst) {
        return analystRepository.save(analyst);
    }

    public void delete(Long id) {
        analystRepository.deleteById(id);
    }
}