package com.biopulse.application.ports.in;

/**
 * Inbound driving port interface defining the capability to ingest and stream patient telemetry.
 */
public interface IngestBiometricsUseCase {

    /**
     * Executes the business use case process for validating and persisting an incoming biometric capture.
     *
     * @param command Fully populated ingestion request parameters.
     */
    void ingest(IngestBiometricsCommand command);
}