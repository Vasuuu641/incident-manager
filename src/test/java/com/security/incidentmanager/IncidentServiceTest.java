package com.security.incidentmanager;

import com.security.incidentmanager.domain.Analyst;
import com.security.incidentmanager.domain.Incident;
import com.security.incidentmanager.repository.AnalystRepository;
import com.security.incidentmanager.repository.IncidentRepository;
import com.security.incidentmanager.service.IncidentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncidentServiceTest {

    @Mock
    private IncidentRepository incidentRepository;

    @Mock
    private AnalystRepository analystRepository;

    @InjectMocks
    private IncidentService incidentService;

    @Test
    void findAll_ShouldReturnAllIncidents() {
        Incident i1 = new Incident();
        i1.setTitle("Incident 1");

        Incident i2 = new Incident();
        i2.setTitle("Incident 2");

        when(incidentRepository.findAll())
                .thenReturn(List.of(i1, i2));

        List<Incident> result = incidentService.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo("Incident 1");
        verify(incidentRepository).findAll();
    }

    @Test
    void findById_ShouldReturnIncident_WhenExists() {
        Incident incident = new Incident();
        incident.setId(1L);
        incident.setTitle("Test Incident");

        when(incidentRepository.findById(1L))
                .thenReturn(Optional.of(incident));

        Incident result = incidentService.findById(1L);

        assertThat(result.getTitle()).isEqualTo("Test Incident");
        assertThat(result.getId()).isEqualTo(1L);
        verify(incidentRepository).findById(1L);
    }

    @Test
    void findById_ShouldThrowException_WhenNotFound() {
        when(incidentRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> incidentService.findById(99L));

        verify(incidentRepository).findById(99L);
    }

    @Test
    void findByStatus_ShouldReturnFilteredIncidents() {
        Incident open = new Incident();
        open.setStatus("OPEN");

        when(incidentRepository.findByStatus("OPEN"))
                .thenReturn(List.of(open));

        List<Incident> result = incidentService.findByStatus("OPEN");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStatus()).isEqualTo("OPEN");
        verify(incidentRepository).findByStatus("OPEN");
    }

    @Test
    void save_ShouldResolveAnalyst_WhenAnalystIdProvided() {
        Analyst analyst = new Analyst();
        analyst.setId(1L);
        analyst.setName("Alice Chen");

        Incident incident = new Incident();
        incident.setTitle("Test Incident");
        incident.setAnalyst(analyst);

        when(analystRepository.findById(1L))
                .thenReturn(Optional.of(analyst));
        when(incidentRepository.save(incident))
                .thenReturn(incident);

        Incident result = incidentService.save(incident);

        assertThat(result.getAnalyst().getName())
                .isEqualTo("Alice Chen");
        verify(analystRepository).findById(1L);
        verify(incidentRepository).save(incident);
    }

    @Test
    void save_ShouldSetAnalystNull_WhenNoAnalystProvided() {
        Incident incident = new Incident();
        incident.setTitle("Unassigned Incident");
        incident.setAnalyst(null);

        when(incidentRepository.save(incident))
                .thenReturn(incident);

        Incident result = incidentService.save(incident);

        assertThat(result.getAnalyst()).isNull();
        verify(incidentRepository).save(incident);
    }

    @Test
    void delete_ShouldCallRepository() {
        incidentService.delete(1L);
        verify(incidentRepository).deleteById(1L);
    }
}