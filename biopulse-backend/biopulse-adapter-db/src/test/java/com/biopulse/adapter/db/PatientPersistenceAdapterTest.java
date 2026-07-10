package com.biopulse.adapter.db;

import com.biopulse.domain.model.Patient;
import com.biopulse.domain.model.PatientStatus;
import com.biopulse.adapter.db.entity.PatientEntity;
import com.biopulse.adapter.db.repository.SpringDataPatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PatientPersistenceAdapterTest {

    @Mock
    private SpringDataPatientRepository repository;

    @InjectMocks
    private PatientPersistenceAdapter adapter;

    @Test
    void shouldReturnMappedDomainPatientWhenEntityExistsInDatabase() {
        UUID patientId = UUID.randomUUID();
        PatientEntity entity = new PatientEntity(
                patientId, "BIO-654321", "Pablo", "Oca",
                LocalDate.now().minusYears(23), "pablo@biopulse.com", "ACTIVE"
        );

        Mockito.when(repository.findById(patientId)).thenReturn(Optional.of(entity));

        Optional<Patient> result = adapter.findById(patientId);

        assertTrue(result.isPresent());
        assertEquals("BIO-654321", result.get().getPatientCode());
        assertEquals(PatientStatus.ACTIVE, result.get().getStatus());
    }

    @Test
    void shouldInvokeSpringDataSaveWhenPersistingPatient() {
        Patient domainPatient = Patient.builder()
                .id(UUID.randomUUID())
                .patientCode("BIO-111111")
                .firstName("Alex")
                .lastName("Smith")
                .dateOfBirth(LocalDate.now().minusYears(30))
                .email(UUID.randomUUID() + "@test.com")
                .status(PatientStatus.ACTIVE)
                .build();

        adapter.save(domainPatient);

        // Verificamos que el adaptador transformó el dominio a entidad y llamó al método save corporativo
        Mockito.verify(repository, Mockito.times(1)).save(any(PatientEntity.class));
    }
}