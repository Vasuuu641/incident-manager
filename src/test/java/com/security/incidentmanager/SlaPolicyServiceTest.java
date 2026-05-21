package com.security.incidentmanager;

import com.security.incidentmanager.domain.Incident;
import com.security.incidentmanager.domain.SlaPolicy;
import com.security.incidentmanager.repository.IncidentRepository;
import com.security.incidentmanager.repository.SlaPolicyRepository;
import com.security.incidentmanager.service.SlaPolicyService;
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
class SlaPolicyServiceTest {

    @Mock
    private SlaPolicyRepository slaPolicyRepository;

    @Mock
    private IncidentRepository incidentRepository;

    @InjectMocks
    private SlaPolicyService slaPolicyService;

    @Test
    void findAll_ShouldReturnAllPolicies() {
        SlaPolicy critical = new SlaPolicy();
        critical.setSeverity("CRITICAL");
        critical.setResolutionHours(4);

        SlaPolicy high = new SlaPolicy();
        high.setSeverity("HIGH");
        high.setResolutionHours(24);

        when(slaPolicyRepository.findAll())
                .thenReturn(List.of(critical, high));

        List<SlaPolicy> result = slaPolicyService.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getSeverity()).isEqualTo("CRITICAL");
        verify(slaPolicyRepository).findAll();
    }

    @Test
    void findBySeverity_ShouldReturnPolicy_WhenExists() {
        SlaPolicy policy = new SlaPolicy();
        policy.setSeverity("CRITICAL");
        policy.setResolutionHours(4);
        policy.setEscalationHours(2);

        when(slaPolicyRepository.findBySeverity("CRITICAL"))
                .thenReturn(Optional.of(policy));

        Optional<SlaPolicy> result =
                slaPolicyService.findBySeverity("CRITICAL");

        assertThat(result).isPresent();
        assertThat(result.get().getResolutionHours()).isEqualTo(4);
        assertThat(result.get().getEscalationHours()).isEqualTo(2);
        verify(slaPolicyRepository).findBySeverity("CRITICAL");
    }

    @Test
    void findBySeverity_ShouldReturnEmpty_WhenNotFound() {
        when(slaPolicyRepository.findBySeverity("UNKNOWN"))
                .thenReturn(Optional.empty());

        Optional<SlaPolicy> result =
                slaPolicyService.findBySeverity("UNKNOWN");

        assertThat(result).isEmpty();
        verify(slaPolicyRepository).findBySeverity("UNKNOWN");
    }

    @Test
    void findById_ShouldThrowException_WhenNotFound() {
        when(slaPolicyRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> slaPolicyService.findById(99L));
    }

    @Test
    void save_ShouldReturnSavedPolicy() {
        SlaPolicy policy = new SlaPolicy();
        policy.setSeverity("MEDIUM");
        policy.setResolutionHours(72);

        when(slaPolicyRepository.save(policy))
                .thenReturn(policy);

        SlaPolicy result = slaPolicyService.save(policy);

        assertThat(result.getSeverity()).isEqualTo("MEDIUM");
        assertThat(result.getResolutionHours()).isEqualTo(72);
        verify(slaPolicyRepository).save(policy);
    }

    @Test
    void delete_ShouldDeletePolicy_WhenNoIncidentsReferencing() {
        when(incidentRepository.findBySlaPolicyId(1L))
                .thenReturn(List.of());

        slaPolicyService.delete(1L);

        verify(slaPolicyRepository).deleteById(1L);
    }

    @Test
    void delete_ShouldThrowException_WhenIncidentsStillReferencing() {
        Incident incident = new Incident();
        incident.setTitle("Active Incident");

        when(incidentRepository.findBySlaPolicyId(1L))
                .thenReturn(List.of(incident));

        assertThrows(IllegalStateException.class,
                () -> slaPolicyService.delete(1L));

        verify(slaPolicyRepository, never()).deleteById(1L);
    }
}