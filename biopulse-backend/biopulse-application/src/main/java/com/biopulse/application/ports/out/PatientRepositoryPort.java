package com.biopulse.application.ports.out;

import com.biopulse.domain.model.Patient;
import java.util.Optional;
import java.util.UUID;

/**
 * Outbound driven port SPI (Service Provider Interface).
 * Defines the contract that must be fulfilled by the persistence layer adapter (PostgreSQL/JPA).
 */
public interface PatientRepositoryPort {

    /**
     * Recovers a Patient Aggregate Root by its global domain identifier.
     *
     * @param id Global target UUID.
     * @return Optional wrapper enclosing the domain Patient instance if indexed.
     */
    Optional<Patient> findById(UUID id);

    /**
     * Persists or updates the state of a Patient Aggregate Root.
     *
     * @param patient Target state tracking instance.
     */
    void save(Patient patient);
}