package com.castlighthealth.epd.model;

import java.util.List;
import com.castlighthealth.epd.model.Participation;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;

/**
 * Represents a person who is a healthcare provider.
 */
@Document(indexName = "provider_sample", type = "provider")
@Entity
@Table(name="providers")
@SecondaryTable(name="provider_specialties",
                 pkJoinColumns = @PrimaryKeyJoinColumn(name="provider_id"))
public class Provider {

    @Id
    @Column(name="id")
    private int providerId;

    @Column(name="name")
    private String providerName;

    @Field(type = FieldType.Nested)
    @OneToMany(mappedBy = "provider")
    private List<Participation> participations;

    @Column(table="provider_specialties")
    private List<Integer> specialtyIds;

    public Provider() {
    }

    public Provider(int providerId, String providerName,
                    List<Participation> participations,
                    List<Integer> specialtyIds) {
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

    public List<Integer> getSpecialtyIds() {
        return specialtyIds;
    }

    public void setSpecialtyIds(List<Integer> specialtyIds) {
        this.specialtyIds = specialtyIds;
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
            for (int sp : specialtyIds) {
                sb.append(sp).append(",");
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