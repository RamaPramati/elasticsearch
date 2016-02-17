package com.castlighthealth.epd.model;

import javax.persistence.*;

@Entity
@Table(name="provider_specialties")
public class ProviderSpecialties {
    @Id
    @Column(name="id")
    private int id;

    @ManyToOne(targetEntity = Provider.class)
    @JoinColumn(name="provider_id", nullable = false)
    private Provider provider;

    @Column(name="specialty_id")
    private int specialtyId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(int specialtyId) {
        this.specialtyId = specialtyId;
    }
}
