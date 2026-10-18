package com.zjgsu.chr.controller;

import com.zjgsu.chr.dto.PetCreateRequest;
import com.zjgsu.chr.dto.PetResponse;
import com.zjgsu.chr.service.PetService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
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
    public ResponseEntity<PetResponse> create(@Valid @RequestBody PetCreateRequest request) {
        PetResponse response = petService.create(request);
        return ResponseEntity.created(URI.create("/api/pets/" + response.id())).body(response);
    }

    @GetMapping
    public List<PetResponse> findAll() {
        return petService.findAll();
    }

    @GetMapping("/{id}")
    public PetResponse findById(@PathVariable Long id) {
        return petService.findById(id);
    }
}
