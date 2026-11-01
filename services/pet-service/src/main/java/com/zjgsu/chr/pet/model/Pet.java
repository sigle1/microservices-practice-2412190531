package com.zjgsu.chr.pet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long ownerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String species;

    protected Pet() {
    }

    public Pet(Long ownerId, String name, String species) {
        this.ownerId = ownerId;
        this.name = name;
        this.species = species;
    }

    public Long getId() { return id; }
    public Long getOwnerId() { return ownerId; }
    public String getName() { return name; }
    public String getSpecies() { return species; }
}
