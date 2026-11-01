package com.zjgsu.chr.appointment.service;

import com.zjgsu.chr.appointment.client.PetClient;
import com.zjgsu.chr.appointment.model.Appointment;
import com.zjgsu.chr.appointment.repository.AppointmentRepository;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PetClient petClient;

    public AppointmentService(AppointmentRepository appointmentRepository, PetClient petClient) {
        this.appointmentRepository = appointmentRepository;
        this.petClient = petClient;
    }

    @Transactional
    public Appointment create(Long petId, LocalDateTime appointmentTime, String reason) {
        // Cross-service validation happens before this service writes its own appointment data.
        petClient.requirePet(petId);
        if (appointmentRepository.existsByPetIdAndAppointmentTime(petId, appointmentTime)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "同一宠物不能在同一时段重复预约");
        }
        return appointmentRepository.save(new Appointment(petId, appointmentTime, reason));
    }
}
