package com.zjgsu.chr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PetCreateRequest(
        @NotBlank @Size(max = 50) String name,
        @NotBlank @Size(max = 50) String ownerName,
        @NotBlank @Pattern(regexp = "^[0-9+ -]{6,20}$") String ownerPhone,
        @NotBlank @Size(max = 30) String species) {
}
