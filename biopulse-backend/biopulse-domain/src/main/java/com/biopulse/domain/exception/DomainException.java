package com.biopulse.domain.exception;

/**
 * Base abstract class for all business and domain-driven exceptions within the BioPulse ecosystem.
 * Forces specialized semantic exceptions and prevents generic runtime errors from leaking the core.
 */
public abstract class DomainException extends RuntimeException {

    /**
     * Constructs a new DomainException with a specific clinical or operational message.
     *
     * @param message Detailed reason describing the business rule violation.
     */
    protected DomainException(String message) {
        super(message);
    }
}