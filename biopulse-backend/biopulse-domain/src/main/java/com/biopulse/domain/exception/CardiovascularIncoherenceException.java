package com.biopulse.domain.exception;

/**
 * Exception thrown when cardiovascular physics are violated within a biometric capture.
 * Specifically handles scenarios where diastolic blood pressure meets or exceeds systolic pressure.
 */
public class CardiovascularIncoherenceException extends DomainException {

    public CardiovascularIncoherenceException(String message) {
        super(message);
    }
}