package com.biopulse.adapter.rest;

import com.biopulse.application.ports.in.IngestBiometricsCommand;
import com.biopulse.application.ports.in.IngestBiometricsUseCase;
import com.biopulse.adapter.rest.dto.BiometricsIngestRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/biometrics")
public class BiometricsIngestionController {

    private final IngestBiometricsUseCase ingestBiometricsUseCase;

    public BiometricsIngestionController(IngestBiometricsUseCase ingestBiometricsUseCase) {
        this.ingestBiometricsUseCase = ingestBiometricsUseCase;
    }

    @PostMapping("/ingest")
    public ResponseEntity<Void> ingestBiometrics(@Valid @RequestBody BiometricsIngestRequest request) {
        IngestBiometricsCommand command = IngestBiometricsCommand.builder()
                .patientId(request.patientId())
                .heartRate(request.heartRate())
                .systolicBp(request.systolicBp())
                .diastolicBp(request.diastolicBp())
                .oxygenSaturation(request.oxygenSaturation())
                .bodyTemperature(request.bodyTemperature())
                .build();

        ingestBiometricsUseCase.ingest(command);

        return ResponseEntity.accepted().build();
    }
}