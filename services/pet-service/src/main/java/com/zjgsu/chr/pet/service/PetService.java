package com.zjgsu.chr.pet.service;

import com.zjgsu.chr.pet.model.Pet;
import com.zjgsu.chr.pet.repository.PetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet create(Long ownerId, String name, String species) {
        return petRepository.save(new Pet(ownerId, name, species));
    }

    public Pet findById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "宠物不存在: " + id));
    }
}
