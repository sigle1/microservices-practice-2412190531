package com.zjgsu.chr.dto;

import com.zjgsu.chr.model.Appointment;
import java.time.LocalDateTime;

public record AppointmentResponse(Long id, Long petId, String petName, LocalDateTime appointmentTime,
                                  String reason, String status) {

    public static AppointmentResponse from(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getPet().getId(),
                appointment.getPet().getName(),
                appointment.getAppointmentTime(),
                appointment.getReason(),
                appointment.getStatus().name());
    }
}
