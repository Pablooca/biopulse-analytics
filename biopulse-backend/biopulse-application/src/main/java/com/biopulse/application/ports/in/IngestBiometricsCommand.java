package com.biopulse.application.ports.in;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * Immutable command payload representing the explicit internal contract required to trigger
 * a biometric ingestion workflow. Completely decoupled from external frameworks.
 */
public final class IngestBiometricsCommand {

    private final UUID patientId;
    private final Integer heartRate;
    private final Integer systolicBp;
    private final Integer diastolicBp;
    private final Integer oxygenSaturation;
    private final BigDecimal bodyTemperature;

    private IngestBiometricsCommand(IngestBiometricsCommandBuilder builder) {
        this.patientId = builder.patientId;
        this.heartRate = builder.heartRate;
        this.systolicBp = builder.systolicBp;
        this.diastolicBp = builder.diastolicBp;
        this.oxygenSaturation = builder.oxygenSaturation;
        this.bodyTemperature = builder.bodyTemperature;
    }

    // --- PURE JAVA GETTERS ---
    public UUID getPatientId() { return patientId; }
    public Integer getHeartRate() { return heartRate; }
    public Integer getStaticBp() { return systolicBp; } // Kept for backwards alignment if needed
    public Integer getSystolicBp() { return systolicBp; }
    public Integer getDiastolicBp() { return diastolicBp; }
    public Integer getOxygenSaturation() { return oxygenSaturation; }
    public BigDecimal getBodyTemperature() { return bodyTemperature; }

    public static IngestBiometricsCommandBuilder builder() {
        return new IngestBiometricsCommandBuilder();
    }

    /**
     * Static inner class implementing the native fluent Builder.
     */
    public static class IngestBiometricsCommandBuilder {
        private UUID patientId;
        private Integer heartRate;
        private Integer systolicBp;
        private Integer diastolicBp;
        private Integer oxygenSaturation;
        private BigDecimal bodyTemperature;

        public IngestBiometricsCommandBuilder patientId(UUID patientId) { this.patientId = patientId; return this; }
        public IngestBiometricsCommandBuilder heartRate(Integer heartRate) { this.heartRate = heartRate; return this; }
        public IngestBiometricsCommandBuilder systolicBp(Integer systolicBp) { this.systolicBp = systolicBp; return this; }
        public IngestBiometricsCommandBuilder diastolicBp(Integer diastolicBp) { this.diastolicBp = diastolicBp; return this; }
        public IngestBiometricsCommandBuilder oxygenSaturation(Integer oxygenSaturation) { this.oxygenSaturation = oxygenSaturation; return this; }
        public IngestBiometricsCommandBuilder bodyTemperature(BigDecimal bodyTemperature) { this.bodyTemperature = bodyTemperature; return this; }

        public IngestBiometricsCommand build() {
            return new IngestBiometricsCommand(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngestBiometricsCommand command = (IngestBiometricsCommand) o;
        return Objects.equals(patientId, command.patientId) &&
                Objects.equals(heartRate, command.heartRate) &&
                Objects.equals(systolicBp, command.systolicBp) &&
                Objects.equals(diastolicBp, command.diastolicBp) &&
                Objects.equals(oxygenSaturation, command.oxygenSaturation) &&
                Objects.equals(bodyTemperature, command.bodyTemperature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, heartRate, systolicBp, diastolicBp, oxygenSaturation, bodyTemperature);
    }
}