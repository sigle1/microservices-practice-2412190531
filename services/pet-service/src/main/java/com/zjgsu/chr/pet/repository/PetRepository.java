package com.zjgsu.chr.pet.repository;

import com.zjgsu.chr.pet.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
