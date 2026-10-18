package com.zjgsu.chr.dto;

import com.zjgsu.chr.model.Pet;

public record PetResponse(Long id, String name, String ownerName, String ownerPhone, String species) {

    public static PetResponse from(Pet pet) {
        return new PetResponse(pet.getId(), pet.getName(), pet.getOwnerName(), pet.getOwnerPhone(), pet.getSpecies());
    }
}
