package com.castlighthealth.epd.model;

import java.util.List;
import com.castlighthealth.epd.model.Participation;
import org.springframework.data.annotation.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import javax.persistence.Id;

/**
 * Represents a person who is a healthcare provider.
 */
@Entity
@Table(name="providers")
public class Provider {

    @Id
    @Column(name="id")
    private int providerId;

    @Column(name="name")
    private String providerName;

    @Field(type = FieldType.Nested)
    @OneToMany(mappedBy = "provider", fetch = FetchType.EAGER)
    private List<Participation> participations;

    @OneToMany(mappedBy = "provider", fetch = FetchType.EAGER)
    private List<ProviderSpecialties> specialtyIds;

    public Provider() {
    }

    public Provider(int providerId, String providerName,
                    List<Participation> participations,
                    List<ProviderSpecialties> specialtyIds) {
        this.providerId = providerId;
        this.providerName = providerName;
        this.participations = participations;
        this.specialtyIds = specialtyIds;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public List<Participation> getParticipations() {
        return participations;
    }

    public List<ProviderSpecialties> getSpecialtyIds() {
        return specialtyIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Provider provider = (Provider) o;

        if (providerId != provider.providerId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return providerId;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("[");
        if (specialtyIds != null) {
            for (ProviderSpecialties sp : specialtyIds) {
                sb.append(sp.getSpecialtyId()).append(",");
            }
        }
        sb.append("]");

        return "Provider{" +
                "providerId=" + providerId +
                ", providerName='" + providerName + '\'' +
                ", specialty=" + sb.toString() +
                '}';
    }
}