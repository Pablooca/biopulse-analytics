package com.biopulse.adapter.db;

import com.biopulse.application.ports.out.BiometricRecordRepositoryPort;
import com.biopulse.domain.model.BiometricRecord;
import com.biopulse.adapter.db.entity.BiometricRecordEntity;
import com.biopulse.adapter.db.repository.SpringDataBiometricRecordRepository;
import org.springframework.stereotype.Component;

@Component
public class BiometricRecordPersistenceAdapter implements BiometricRecordRepositoryPort {

    private final SpringDataBiometricRecordRepository repository;

    public BiometricRecordPersistenceAdapter(SpringDataBiometricRecordRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(BiometricRecord record) {
        BiometricRecordEntity entity = new BiometricRecordEntity(
                record.getId(),
                record.getPatientId(),
                record.getTimestamp(),
                record.getHeartRate(),
                record.getSystolicBp(),
                record.getDiastolicBp(),
                record.getOxygenSaturation(),
                record.getBodyTemperature()
        );
        repository.save(entity);
    }
}