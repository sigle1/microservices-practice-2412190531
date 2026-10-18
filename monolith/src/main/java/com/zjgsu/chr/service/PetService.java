package com.zjgsu.chr.service;

import com.zjgsu.chr.dto.PetCreateRequest;
import com.zjgsu.chr.dto.PetResponse;
import com.zjgsu.chr.exception.ResourceNotFoundException;
import com.zjgsu.chr.model.Pet;
import com.zjgsu.chr.repository.PetRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Transactional
    public PetResponse create(PetCreateRequest request) {
        Pet pet = new Pet(request.name(), request.ownerName(), request.ownerPhone(), request.species());
        return PetResponse.from(petRepository.save(pet));
    }

    @Transactional(readOnly = true)
    public List<PetResponse> findAll() {
        return petRepository.findAll().stream().map(PetResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public PetResponse findById(Long id) {
        return PetResponse.from(findEntity(id));
    }

    @Transactional(readOnly = true)
    public Pet findEntity(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("未找到 ID 为 " + id + " 的宠物"));
    }
}
