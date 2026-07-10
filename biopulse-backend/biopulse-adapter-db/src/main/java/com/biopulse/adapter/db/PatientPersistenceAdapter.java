package com.biopulse.adapter.db;

import com.biopulse.application.ports.out.PatientRepositoryPort;
import com.biopulse.domain.model.Patient;
import com.biopulse.domain.model.PatientStatus;
import com.biopulse.adapter.db.entity.PatientEntity;
import com.biopulse.adapter.db.repository.SpringDataPatientRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.UUID;

@Component
public class PatientPersistenceAdapter implements PatientRepositoryPort {

    private final SpringDataPatientRepository repository;

    public PatientPersistenceAdapter(SpringDataPatientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Patient> findById(UUID id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public void save(Patient patient) {
        repository.save(toEntity(patient));
    }

    private Patient toDomain(PatientEntity entity) {
        return Patient.builder()
                .id(entity.getId())
                .patientCode(entity.getPatientCode())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .dateOfBirth(entity.getDateOfBirth())
                .email(entity.getEmail())
                .status(PatientStatus.valueOf(entity.getStatus()))
                .build();
    }

    private PatientEntity toEntity(Patient domain) {
        return new PatientEntity(
                domain.getId(),
                domain.getPatientCode(),
                domain.getFirstName(),
                domain.getLastName(),
                domain.getDateOfBirth(),
                domain.getEmail(),
                domain.getStatus().name()
        );
    }
}