package com.biopulse.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import com.biopulse.domain.exception.InvalidPatientAgeException;

class PatientTest {

    @Test
    void shouldCreateValidPatientWhenAllInvariantsAreMet() {
        Patient patient = Patient.builder()
                .id(UUID.randomUUID())
                .patientCode("BIO-123456")
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.now().minusYears(25))
                .email("john.doe@hospital.com")
                .status(PatientStatus.ACTIVE)
                .build();

        assertNotNull(patient);
        assertEquals("John", patient.getFirstName());
        assertEquals(PatientStatus.ACTIVE, patient.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenPatientIsUnderage() {
        assertThrows(InvalidPatientAgeException.class, () -> {
            Patient.builder()
                    .id(UUID.randomUUID())
                    .patientCode("BIO-123456")
                    .dateOfBirth(LocalDate.now().minusYears(17)) // 17 years old -> Illegal
                    .build();
        });
    }

    @Test
    void shouldThrowExceptionWhenPatientCodeIsMalformed() {
        assertThrows(IllegalArgumentException.class, () -> {
            Patient.builder()
                    .id(UUID.randomUUID())
                    .patientCode("INVALID-CODE-123")
                    .dateOfBirth(LocalDate.now().minusYears(30))
                    .build();
        });
    }

    @Test
    void shouldEvolveStatusToSuspendedCorrectly() {
        Patient activePatient = Patient.builder()
                .id(UUID.randomUUID())
                .status(PatientStatus.ACTIVE)
                .build();

        Patient suspendedPatient = activePatient.suspendTelemetry();

        assertEquals(PatientStatus.SUSPENDED, suspendedPatient.getStatus());
        assertNotSame(activePatient, suspendedPatient); // Assures immutability copy
    }

    @Test
    void shouldThrowExceptionWhenSuspendingDischargedPatient() {
        Patient dischargedPatient = Patient.builder()
                .id(UUID.randomUUID())
                .status(PatientStatus.DISCHARGED)
                .build();

        assertThrows(IllegalStateException.class, dischargedPatient::suspendTelemetry);
    }
}