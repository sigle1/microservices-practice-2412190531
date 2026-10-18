package com.zjgsu.chr.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record AppointmentCreateRequest(
        @NotNull @Positive Long petId,
        @NotNull @Future LocalDateTime appointmentTime,
        @NotBlank @Size(max = 200) String reason) {
}
