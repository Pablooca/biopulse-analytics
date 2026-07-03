package com.biopulse.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import com.biopulse.domain.exception.InvalidBiometricRangeException;
import com.biopulse.domain.exception.CardiovascularIncoherenceException;

/**
 * Value Object capturing an unalterable point-in-time telemetry vital sign packet emitted by IoT hardware.
 * Written in Pure Java 21 to eliminate framework processing overhead.
 */
public final class BiometricRecord {

    private final UUID id;
    private final UUID patientId;
    private final Instant timestamp;
    private final Integer heartRate;
    private final Integer systolicBp;
    private final Integer diastolicBp;
    private final Integer oxygenSaturation;
    private final BigDecimal bodyTemperature;

    /**
     * Private constructor enforced by the Builder pattern.
     * Executes physiological validation boundaries before instantiation.
     */
    private BiometricRecord(BiometricRecordBuilder builder) {
        this.id = builder.id;
        this.patientId = builder.patientId;
        this.timestamp = builder.timestamp;
        this.heartRate = builder.heartRate;
        this.systolicBp = builder.systolicBp;
        this.diastolicBp = builder.diastolicBp;
        this.oxygenSaturation = builder.oxygenSaturation;
        this.bodyTemperature = builder.bodyTemperature;
        validateInvariants();
    }

    private void validateInvariants() {
        if (timestamp != null && timestamp.isAfter(Instant.now())) {
            throw new InvalidBiometricRangeException("Telemetry execution baseline cannot exist in the future.");
        }
        if (heartRate != null && (heartRate < 30 || heartRate > 250)) {
            throw new InvalidBiometricRangeException("Physiological noise error: Heart rate falls out of bounds [30-250] bpm.");
        }
        if (systolicBp != null && (systolicBp < 70 || systolicBp > 210)) {
            throw new InvalidBiometricRangeException("Physiological noise error: Systolic BP falls out of bounds [70-210] mmHg.");
        }
        if (diastolicBp != null && (diastolicBp < 40 || diastolicBp > 130)) {
            throw new InvalidBiometricRangeException("Physiological noise error: Diastolic BP falls out of bounds [40-130] mmHg.");
        }
        if (oxygenSaturation != null && (oxygenSaturation < 50 || oxygenSaturation > 100)) {
            throw new InvalidBiometricRangeException("Physiological noise error: Oxygen saturation falls out of bounds [50-100] %.");
        }
        if (bodyTemperature != null && (bodyTemperature.compareTo(new BigDecimal("34.0")) < 0 || bodyTemperature.compareTo(new BigDecimal("43.0")) > 0)) {
            throw new InvalidBiometricRangeException("Physiological noise error: Body temperature falls out of bounds [34.0-43.0] °C.");
        }
        if (systolicBp != null && diastolicBp != null && systolicBp <= diastolicBp) {
            throw new CardiovascularIncoherenceException(
                    "Biophysical rule violation: Systolic pressure must be strictly higher than Diastolic pressure."
            );
        }
    }

    // --- PURE JAVA GETTERS ---
    public UUID getId() { return id; }
    public UUID getPatientId() { return patientId; }
    public Instant getTimestamp() { return timestamp; }
    public Integer getHeartRate() { return heartRate; }
    public Integer getSystolicBp() { return systolicBp; }
    public Integer getDiastolicBp() { return diastolicBp; }
    public Integer getOxygenSaturation() { return oxygenSaturation; }
    public BigDecimal getBodyTemperature() { return bodyTemperature; }

    public static BiometricRecordBuilder builder() {
        return new BiometricRecordBuilder();
    }

    /**
     * Static inner class implementing a fluent API Builder pattern for BiometricRecord.
     */
    public static class BiometricRecordBuilder {
        private UUID id;
        private UUID patientId;
        private Instant timestamp;
        private Integer heartRate;
        private Integer systolicBp;
        private Integer diastolicBp;
        private Integer oxygenSaturation;
        private BigDecimal bodyTemperature;

        public BiometricRecordBuilder id(UUID id) { this.id = id; return this; }
        public BiometricRecordBuilder patientId(UUID patientId) { this.patientId = patientId; return this; }
        public BiometricRecordBuilder timestamp(Instant timestamp) { this.timestamp = timestamp; return this; }
        public BiometricRecordBuilder heartRate(Integer heartRate) { this.heartRate = heartRate; return this; }
        public BiometricRecordBuilder systolicBp(Integer systolicBp) { this.systolicBp = systolicBp; return this; }
        public BiometricRecordBuilder diastolicBp(Integer diastolicBp) { this.diastolicBp = diastolicBp; return this; }
        public BiometricRecordBuilder oxygenSaturation(Integer oxygenSaturation) { this.oxygenSaturation = oxygenSaturation; return this; }
        public BiometricRecordBuilder bodyTemperature(BigDecimal bodyTemperature) { this.bodyTemperature = bodyTemperature; return this; }

        public BiometricRecord build() {
            return new BiometricRecord(this);
        }
    }

    // --- STANDARD OVERRIDES ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BiometricRecord that = (BiometricRecord) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(patientId, that.patientId) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(heartRate, that.heartRate) &&
                Objects.equals(systolicBp, that.systolicBp) &&
                Objects.equals(diastolicBp, that.diastolicBp) &&
                Objects.equals(oxygenSaturation, that.oxygenSaturation) &&
                Objects.equals(bodyTemperature, that.bodyTemperature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patientId, timestamp, heartRate, systolicBp, diastolicBp, oxygenSaturation, bodyTemperature);
    }

    @Override
    public String toString() {
        return "BiometricRecord{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", timestamp=" + timestamp +
                ", heartRate=" + heartRate +
                ", systolicBp=" + systolicBp +
                ", diastolicBp=" + diastolicBp +
                ", oxygenSaturation=" + oxygenSaturation +
                ", bodyTemperature=" + bodyTemperature +
                '}';
    }
}