package com.zjgsu.chr.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(name = "owner_name", nullable = false, length = 50)
    private String ownerName;

    @Column(name = "owner_phone", nullable = false, length = 20)
    private String ownerPhone;

    @Column(nullable = false, length = 30)
    private String species;

    @OneToMany(mappedBy = "pet")
    private List<Appointment> appointments = new ArrayList<>();

    protected Pet() {
    }

    public Pet(String name, String ownerName, String ownerPhone, String species) {
        this.name = name;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.species = species;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public String getSpecies() {
        return species;
    }
}
