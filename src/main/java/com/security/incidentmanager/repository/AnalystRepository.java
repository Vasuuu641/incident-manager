package com.security.incidentmanager.repository;

import com.security.incidentmanager.domain.Analyst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnalystRepository extends JpaRepository<Analyst, Long> {
    List<Analyst> findBySpecialization(String specialization);
}