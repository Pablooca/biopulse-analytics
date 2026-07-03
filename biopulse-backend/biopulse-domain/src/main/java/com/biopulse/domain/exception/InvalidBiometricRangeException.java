package com.biopulse.domain.exception;

/**
 * Exception thrown when an IoT telemetry payload registers vital signs outside acceptable
 * physiological thresholds, indicating sensor miscalibration or data transmission noise.
 */
public class InvalidBiometricRangeException extends DomainException {

    public InvalidBiometricRangeException(String message) {
        super(message);
    }
}