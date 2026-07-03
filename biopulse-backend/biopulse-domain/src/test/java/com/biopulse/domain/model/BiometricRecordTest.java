package com.biopulse.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import com.biopulse.domain.exception.InvalidBiometricRangeException;
import com.biopulse.domain.exception.CardiovascularIncoherenceException;

class BiometricRecordTest {

    @Test
    void shouldCreateValidRecordWhenVitalsAreWithinPhysiologicalRanges() {
        BiometricRecord record = BiometricRecord.builder()
                .id(UUID.randomUUID())
                .patientId(UUID.randomUUID())
                .timestamp(Instant.now())
                .heartRate(75)
                .systolicBp(120)
                .diastolicBp(80)
                .oxygenSaturation(98)
                .bodyTemperature(new BigDecimal("36.6"))
                .build();

        assertNotNull(record);
        assertEquals(75, record.getHeartRate());
    }

    @Test
    void shouldThrowExceptionWhenHeartRateIsOutInTheWild() {
        assertThrows(InvalidBiometricRangeException.class, () -> {
            BiometricRecord.builder()
                    .heartRate(29) // Noise threshold lower bound is 30
                    .build();
        });
    }

    @Test
    void shouldThrowExceptionWhenCardiovascularPhysicsAreIncoherent() {
        assertThrows(CardiovascularIncoherenceException.class, () -> {
            BiometricRecord.builder()
                    .systolicBp(100)
                    .diastolicBp(110) // Incoherence: Diastolic cannot be higher than Systolic
                    .build();
        });
    }
}