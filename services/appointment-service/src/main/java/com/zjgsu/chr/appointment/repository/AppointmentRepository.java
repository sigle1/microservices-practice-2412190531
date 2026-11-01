package com.zjgsu.chr.appointment.repository;

import com.zjgsu.chr.appointment.model.Appointment;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByPetIdAndAppointmentTime(Long petId, LocalDateTime appointmentTime);
}
