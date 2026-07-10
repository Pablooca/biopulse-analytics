package com.biopulse.adapter.db.repository;

import com.biopulse.adapter.db.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringDataPatientRepository extends JpaRepository<PatientEntity, UUID> {}