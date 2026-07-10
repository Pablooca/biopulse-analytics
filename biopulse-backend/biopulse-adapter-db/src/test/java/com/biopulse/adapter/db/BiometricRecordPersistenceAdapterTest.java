package com.biopulse.adapter.db;

import com.biopulse.domain.model.BiometricRecord;
import com.biopulse.adapter.db.entity.BiometricRecordEntity;
import com.biopulse.adapter.db.repository.SpringDataBiometricRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class BiometricRecordPersistenceAdapterTest {

    @Mock
    private SpringDataBiometricRecordRepository repository;

    @InjectMocks
    private BiometricRecordPersistenceAdapter adapter;

    @Test
    void shouldInvokeSpringDataSaveWhenPersistingBiometricRecord() {
        BiometricRecord record = BiometricRecord.builder()
                .id(UUID.randomUUID())
                .patientId(UUID.randomUUID())
                .timestamp(Instant.now())
                .heartRate(80)
                .systolicBp(120)
                .diastolicBp(75)
                .oxygenSaturation(99)
                .bodyTemperature(new BigDecimal("36.50"))
                .build();

        adapter.save(record);

        Mockito.verify(repository, Mockito.times(1)).save(any(BiometricRecordEntity.class));
    }
}