package com.biopulse.adapter.db.repository;

import com.biopulse.adapter.db.entity.BiometricRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringDataBiometricRecordRepository extends JpaRepository<BiometricRecordEntity, UUID> {}