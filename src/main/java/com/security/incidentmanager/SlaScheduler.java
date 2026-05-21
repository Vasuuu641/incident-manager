package com.security.incidentmanager;

import com.security.incidentmanager.domain.Incident;
import com.security.incidentmanager.repository.IncidentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SlaScheduler {

    private final IncidentRepository incidentRepository;

    @Scheduled(fixedRate = 60000)
    public void checkSlaBreaches() {
        List<Incident> activeIncidents = incidentRepository
                .findByStatusIn(List.of("OPEN", "IN_PROGRESS"));

        int breachCount = 0;

        for (Incident incident : activeIncidents) {
            if (incident.getSlaDeadline() != null
                    && LocalDateTime.now().isAfter(incident.getSlaDeadline())
                    && !incident.isEscalated()) {
                incident.setEscalated(true);
                incident.setStatus("ESCALATED");
                incidentRepository.save(incident);
                breachCount++;
                log.warn("🚨 SLA breached — incident escalated: [{}] {}",
                        incident.getId(), incident.getTitle());
            }
        }

        if (breachCount > 0) {
            log.info("SLA check complete — {} incident(s) escalated.",
                    breachCount);
        } else {
            log.debug("SLA check complete — no breaches detected.");
        }
    }
}