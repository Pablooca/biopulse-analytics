package com.biopulse.domain.model;

/**
 * Defines the operational lifecycles of a patient enrolled in the telemetry program.
 */
public enum PatientStatus {
    /** Patient is fully eligible and actively streaming vital signs. */
    ACTIVE,
    /** Telemetry is paused due to clinical reviews or manual intervention. */
    SUSPENDED,
    /** Patient is permanently removed from the monitoring scope; data is frozen. */
    DISCHARGED
}