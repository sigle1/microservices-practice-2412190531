package com.zjgsu.chr.pet.controller;

import com.zjgsu.chr.pet.model.Pet;
import com.zjgsu.chr.pet.service.PetService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<PetResponse> create(@Valid @RequestBody CreatePetRequest request) {
        Pet pet = petService.create(request.ownerId(), request.name(), request.species());
        return ResponseEntity.created(URI.create("/api/pets/" + pet.getId())).body(toResponse(pet));
    }

    @GetMapping("/{id}")
    public PetResponse findById(@PathVariable Long id) {
        return toResponse(petService.findById(id));
    }

    private PetResponse toResponse(Pet pet) {
        return new PetResponse(pet.getId(), pet.getOwnerId(), pet.getName(), pet.getSpecies());
    }

    public record CreatePetRequest(@NotNull Long ownerId, @NotBlank String name, @NotBlank String species) { }
    public record PetResponse(Long id, Long ownerId, String name, String species) { }
}
