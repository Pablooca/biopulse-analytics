package com.biopulse.application.ports.out;

import com.biopulse.domain.model.BiometricRecord;

/**
 * Outbound driven port SPI defining persistence capabilities for time-series biometric snapshots.
 */
public interface BiometricRecordRepositoryPort {

    /**
     * Dispatches a verified BiometricRecord to the target permanent persistence layer.
     *
     * @param record Pure unalterable value object record.
     */
    void save(BiometricRecord record);
}