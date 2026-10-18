package com.zjgsu.chr.exception;

public class DuplicateAppointmentException extends RuntimeException {

    public DuplicateAppointmentException(String message) {
        super(message);
    }
}
