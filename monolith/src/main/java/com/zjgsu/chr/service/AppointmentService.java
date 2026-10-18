package com.zjgsu.chr.service;

import com.zjgsu.chr.dto.AppointmentCreateRequest;
import com.zjgsu.chr.dto.AppointmentResponse;
import com.zjgsu.chr.exception.DuplicateAppointmentException;
import com.zjgsu.chr.model.Appointment;
import com.zjgsu.chr.model.Pet;
import com.zjgsu.chr.repository.AppointmentRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PetService petService;

    public AppointmentService(AppointmentRepository appointmentRepository, PetService petService) {
        this.appointmentRepository = appointmentRepository;
        this.petService = petService;
    }

    @Transactional
    public AppointmentResponse create(AppointmentCreateRequest request) {
        if (appointmentRepository.existsByPetIdAndAppointmentTime(request.petId(), request.appointmentTime())) {
            throw new DuplicateAppointmentException("同一宠物不能在同一时段重复预约");
        }
        Pet pet = petService.findEntity(request.petId());
        Appointment appointment = new Appointment(pet, request.appointmentTime(), request.reason());
        return AppointmentResponse.from(appointmentRepository.save(appointment));
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> findAll(Long petId) {
        List<Appointment> appointments = petId == null
                ? appointmentRepository.findAll()
                : appointmentRepository.findByPetIdOrderByAppointmentTimeAsc(petId);
        return appointments.stream().map(AppointmentResponse::from).toList();
    }
}
