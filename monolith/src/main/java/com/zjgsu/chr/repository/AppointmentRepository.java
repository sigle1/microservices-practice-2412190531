package com.zjgsu.chr.repository;

import com.zjgsu.chr.model.Appointment;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByPetIdAndAppointmentTime(Long petId, LocalDateTime appointmentTime);

    List<Appointment> findByPetIdOrderByAppointmentTimeAsc(Long petId);
}
