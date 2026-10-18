package com.zjgsu.chr.repository;

import com.zjgsu.chr.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
