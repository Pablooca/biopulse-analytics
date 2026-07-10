package com.biopulse.adapter.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "biometric_records")
public class BiometricRecordEntity {

    @Id
    private UUID id;

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(nullable = false, columnDefinition = "TIMESTAMPTZ")
    private Instant timestamp;

    @Column(name = "heart_rate", nullable = false)
    private Integer heartRate;

    @Column(name = "systolic_bp", nullable = false)
    private Integer systolicBp;

    @Column(name = "diastolic_bp", nullable = false)
    private Integer diastolicBp;

    @Column(name = "oxygen_saturation", nullable = false)
    private Integer oxygenSaturation;

    @Column(name = "body_temperature", nullable = false, precision = 4, scale = 2)
    private BigDecimal bodyTemperature;

    public BiometricRecordEntity() {}

    public BiometricRecordEntity(UUID id, UUID patientId, Instant timestamp, Integer heartRate, Integer systolicBp, Integer diastolicBp, Integer oxygenSaturation, BigDecimal bodyTemperature) {
        this.id = id;
        this.patientId = patientId;
        this.timestamp = timestamp;
        this.heartRate = heartRate;
        this.systolicBp = systolicBp;
        this.diastolicBp = diastolicBp;
        this.oxygenSaturation = oxygenSaturation;
        this.bodyTemperature = bodyTemperature;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getPatientId() { return patientId; }
    public void setPatientId(UUID patientId) { this.patientId = patientId; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    public Integer getHeartRate() { return heartRate; }
    public void setHeartRate(Integer heartRate) { this.heartRate = heartRate; }
    public Integer getSystolicBp() { return systolicBp; }
    public void setSystolicBp(Integer systolicBp) { this.systolicBp = systolicBp; }
    public Integer getDiastolicBp() { return diastolicBp; }
    public void setDiastolicBp(Integer diastolicBp) { this.diastolicBp = diastolicBp; }
    public Integer getOxygenSaturation() { return oxygenSaturation; }
    public void setOxygenSaturation(Integer oxygenSaturation) { this.oxygenSaturation = oxygenSaturation; }
    public BigDecimal getBodyTemperature() { return bodyTemperature; }
    public void setBodyTemperature(BigDecimal bodyTemperature) { this.bodyTemperature = bodyTemperature; }
}