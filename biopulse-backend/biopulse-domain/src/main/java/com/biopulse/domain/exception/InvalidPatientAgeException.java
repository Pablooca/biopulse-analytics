package com.biopulse.domain.exception;

/**
 * Exception thrown when a patient admission operation fails due to age constraints.
 * Telemetry trials are strictly bounded to legal adult individuals (Age >= 18).
 */
public class InvalidPatientAgeException extends DomainException {

    public InvalidPatientAgeException(String message) {
        super(message);
    }
}