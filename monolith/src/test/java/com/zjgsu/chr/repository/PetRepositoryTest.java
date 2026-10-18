package com.zjgsu.chr.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.zjgsu.chr.model.Pet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PetRepositoryTest {

    @Autowired
    private PetRepository petRepository;

    @Test
    void savesAndReadsPet() {
        Pet saved = petRepository.save(new Pet("团团", "陈昊睿", "13800000000", "猫"));

        assertTrue(saved.getId() > 0);
        Pet loaded = petRepository.findById(saved.getId()).orElseThrow();
        assertEquals("团团", loaded.getName());
        assertEquals("陈昊睿", loaded.getOwnerName());
    }
}
