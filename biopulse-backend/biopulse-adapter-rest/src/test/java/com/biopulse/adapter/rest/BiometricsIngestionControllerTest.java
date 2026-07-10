package com.biopulse.adapter.rest;

import com.biopulse.application.ports.in.IngestBiometricsUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BiometricsIngestionController.class)
class BiometricsIngestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngestBiometricsUseCase ingestBiometricsUseCase;

    @Test
    void shouldReturnAcceptedWhenPayloadIsValid() throws Exception {
        String validPayload = """
                {
                  "patientId": "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d",
                  "heartRate": 72,
                  "systolicBp": 120,
                  "diastolicBp": 80,
                  "oxygenSaturation": 98,
                  "bodyTemperature": 36.65
                }
                """;

        mockMvc.perform(post("/api/v1/biometrics/ingest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validPayload))
                .andExpect(status().isAccepted());

        // Verificamos que el controlador realmente delegó la ejecución al caso de uso del core
        Mockito.verify(ingestBiometricsUseCase, Mockito.times(1)).ingest(any());
    }

    @Test
    void shouldReturnBadRequestWhenRequiredFieldsAreMissing() throws Exception {
        // Payload malformado: falta el patientId que es obligatorio
        String invalidPayload = """
                {
                  "heartRate": 72,
                  "systolicBp": 120,
                  "diastolicBp": 80,
                  "oxygenSaturation": 98,
                  "bodyTemperature": 36.65
                }
                """;

        mockMvc.perform(post("/api/v1/biometrics/ingest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPayload))
                .andExpect(status().isBadRequest());

        // El caso de uso jamás debió ser invocado al fallar el filtro perimetral
        Mockito.verifyNoInteractions(ingestBiometricsUseCase);
    }

    @Test
    void shouldReturnBadRequestWhenPhysiologicalVitalsAreOutOfBounds() throws Exception {
        // Heart rate en 10 bpm viola la anotación @Min(30) del DTO
        String outOfBoundsPayload = """
                {
                  "patientId": "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d",
                  "heartRate": 10,
                  "systolicBp": 120,
                  "diastolicBp": 80,
                  "oxygenSaturation": 98,
                  "bodyTemperature": 36.65
                }
                """;

        mockMvc.perform(post("/api/v1/biometrics/ingest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(outOfBoundsPayload))
                .andExpect(status().isBadRequest());

        Mockito.verifyNoInteractions(ingestBiometricsUseCase);
    }
}