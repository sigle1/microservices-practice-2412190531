package com.zjgsu.chr.appointment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.zjgsu.chr.appointment.model.Appointment;
import com.zjgsu.chr.appointment.repository.AppointmentRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
class AppointmentServiceTest {

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    RestTemplate restTemplate;

    @MockitoBean
    DiscoveryClient discoveryClient;

    MockRestServiceServer remote;

    @BeforeEach
    void setUp() {
        appointmentRepository.deleteAll();
        remote = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    void createsAppointmentThroughDiscoveredPetInstance() {
        Mockito.when(discoveryClient.getInstances("pet-service")).thenReturn(List.of(
                new DefaultServiceInstance("pet-a", "pet-service", "10.0.0.11", 8082, false)));
        remote.expect(requestTo("http://10.0.0.11:8082/api/pets/7"))
                .andRespond(withSuccess("{\"id\":7,\"name\":\"团团\"}", MediaType.APPLICATION_JSON));

        Appointment appointment = appointmentService.create(
                7L, LocalDateTime.of(2026, 12, 1, 10, 0), "年度体检");

        assertEquals(7L, appointment.getPetId());
        assertEquals(1, appointmentRepository.count());
        remote.verify();
    }

    @Test
    void emptyInstanceListReturns503WithoutSavingAppointment() {
        Mockito.when(discoveryClient.getInstances("pet-service")).thenReturn(List.of());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> appointmentService.create(
                        7L, LocalDateTime.of(2026, 12, 1, 10, 0), "年度体检"));

        assertEquals(503, exception.getStatusCode().value());
        assertEquals(0, appointmentRepository.count());
        remote.verify();
    }
}
