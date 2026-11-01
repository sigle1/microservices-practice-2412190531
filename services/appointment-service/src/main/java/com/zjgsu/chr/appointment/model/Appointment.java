package com.zjgsu.chr.appointment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long petId;

    @Column(nullable = false)
    private LocalDateTime appointmentTime;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private String status;

    protected Appointment() {
    }

    public Appointment(Long petId, LocalDateTime appointmentTime, String reason) {
        this.petId = petId;
        this.appointmentTime = appointmentTime;
        this.reason = reason;
        this.status = "SCHEDULED";
    }

    public Long getId() { return id; }
    public Long getPetId() { return petId; }
    public LocalDateTime getAppointmentTime() { return appointmentTime; }
    public String getReason() { return reason; }
    public String getStatus() { return status; }
}
