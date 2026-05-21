package com.security.incidentmanager;

import com.security.incidentmanager.domain.Analyst;
import com.security.incidentmanager.repository.AnalystRepository;
import com.security.incidentmanager.service.AnalystService;
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
class AnalystServiceTest {

    @Mock
    private AnalystRepository analystRepository;

    @InjectMocks
    private AnalystService analystService;

    @Test
    void findAll_ShouldReturnAllAnalysts() {
        Analyst a1 = new Analyst();
        a1.setName("Alice Chen");

        Analyst a2 = new Analyst();
        a2.setName("Bob Patel");

        when(analystRepository.findAll())
                .thenReturn(List.of(a1, a2));

        List<Analyst> result = analystService.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Alice Chen");
        verify(analystRepository).findAll();
    }

    @Test
    void findById_ShouldReturnAnalyst_WhenExists() {
        Analyst analyst = new Analyst();
        analyst.setId(1L);
        analyst.setName("Alice Chen");
        analyst.setSpecialization("Network Security");

        when(analystRepository.findById(1L))
                .thenReturn(Optional.of(analyst));

        Analyst result = analystService.findById(1L);

        assertThat(result.getName()).isEqualTo("Alice Chen");
        assertThat(result.getSpecialization())
                .isEqualTo("Network Security");
        verify(analystRepository).findById(1L);
    }

    @Test
    void findById_ShouldThrowException_WhenNotFound() {
        when(analystRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> analystService.findById(99L));
    }

    @Test
    void findBySpecialization_ShouldReturnMatchingAnalysts() {
        Analyst analyst = new Analyst();
        analyst.setSpecialization("Network Security");

        when(analystRepository.findBySpecialization("Network Security"))
                .thenReturn(List.of(analyst));

        List<Analyst> result = analystService
                .findBySpecialization("Network Security");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSpecialization())
                .isEqualTo("Network Security");
        verify(analystRepository).findBySpecialization("Network Security");
    }

    @Test
    void save_ShouldReturnSavedAnalyst() {
        Analyst analyst = new Analyst();
        analyst.setName("Carol Smith");

        when(analystRepository.save(analyst))
                .thenReturn(analyst);

        Analyst result = analystService.save(analyst);

        assertThat(result.getName()).isEqualTo("Carol Smith");
        verify(analystRepository).save(analyst);
    }

    @Test
    void delete_ShouldCallRepository() {
        analystService.delete(1L);
        verify(analystRepository).deleteById(1L);
    }
}