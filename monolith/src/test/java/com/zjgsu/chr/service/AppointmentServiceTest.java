package com.zjgsu.chr.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.zjgsu.chr.dto.AppointmentCreateRequest;
import com.zjgsu.chr.dto.PetCreateRequest;
import com.zjgsu.chr.dto.PetResponse;
import com.zjgsu.chr.exception.DuplicateAppointmentException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppointmentServiceTest {

    @Autowired
    private PetService petService;

    @Autowired
    private AppointmentService appointmentService;

    @Test
    void rejectsDuplicateAppointmentForSamePetAndTime() {
        PetResponse pet = petService.create(new PetCreateRequest("团团", "陈昊睿", "13800000000", "猫"));
        LocalDateTime appointmentTime = LocalDateTime.now().plusDays(1).withSecond(0).withNano(0);
        AppointmentCreateRequest request = new AppointmentCreateRequest(pet.id(), appointmentTime, "年度体检");

        appointmentService.create(request);
        DuplicateAppointmentException exception = assertThrows(
                DuplicateAppointmentException.class, () -> appointmentService.create(request));

        assertEquals("同一宠物不能在同一时段重复预约", exception.getMessage());
    }
}
