package com.zjgsu.chr.appointment.controller;

import com.zjgsu.chr.appointment.model.Appointment;
import com.zjgsu.chr.appointment.service.AppointmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> create(@Valid @RequestBody CreateAppointmentRequest request) {
        Appointment appointment = appointmentService.create(
                request.petId(), request.appointmentTime(), request.reason());
        return ResponseEntity.created(URI.create("/api/appointments/" + appointment.getId()))
                .body(new AppointmentResponse(appointment.getId(), appointment.getPetId(),
                        appointment.getAppointmentTime(), appointment.getReason(), appointment.getStatus()));
    }

    public record CreateAppointmentRequest(@NotNull Long petId, @NotNull LocalDateTime appointmentTime,
                                           @NotBlank String reason) { }
    public record AppointmentResponse(Long id, Long petId, LocalDateTime appointmentTime,
                                      String reason, String status) { }
}
