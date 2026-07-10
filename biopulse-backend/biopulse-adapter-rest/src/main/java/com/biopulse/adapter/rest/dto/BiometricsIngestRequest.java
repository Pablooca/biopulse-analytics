package com.biopulse.adapter.rest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record BiometricsIngestRequest(
        @NotNull(message = "patientId is mandatory")
        UUID patientId,

        @NotNull(message = "heartRate is mandatory")
        @Min(value = 30, message = "Heart rate out of physiological lower bound")
        @Max(value = 250, message = "Heart rate out of physiological upper bound")
        Integer heartRate,

        @NotNull(message = "systolicBp is mandatory")
        @Min(value = 70, message = "Systolic BP out of physiological lower bound")
        @Max(value = 210, message = "Systolic BP out of physiological upper bound")
        Integer systolicBp,

        @NotNull(message = "diastolicBp is mandatory")
        @Min(value = 40, message = "Diastolic BP out of physiological lower bound")
        @Max(value = 130, message = "Diastolic BP out of physiological upper bound")
        Integer diastolicBp,

        @NotNull(message = "oxygenSaturation is mandatory")
        @Min(value = 50, message = "Oxygen saturation out of physiological lower bound")
        @Max(value = 100, message = "Oxygen saturation out of physiological upper bound")
        Integer oxygenSaturation,

        @NotNull(message = "bodyTemperature is mandatory")
        @Min(value = 34, message = "Body temperature out of physiological lower bound")
        @Max(value = 43, message = "Body temperature out of physiological upper bound")
        BigDecimal bodyTemperature
) {}

