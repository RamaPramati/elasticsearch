package com.castlighthealth.epd.model;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import javax.persistence.Id;

/**
 * Represents a person who is a healthcare provider.
 */
@Entity
@Table(name="providers")
@NamedQueries({
        @NamedQuery(name = Provider.QUERY_FIND_PROVIDERS_BETWEEN,
                query = "SELECT p FROM Provider p WHERE p.id BETWEEN :" + Provider.MIN_PROVIDER_ID + " AND :" + Provider.MAX_PROVIDER_ID)
})
public class Provider {

    public static final String QUERY_FIND_PROVIDERS_BETWEEN = "Provider.findProviderBetween";
    public static final String MIN_PROVIDER_ID = "minProviderId";
    public static final String MAX_PROVIDER_ID = "maxProviderId";


    @Id
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String providerName;

    @Field(type = FieldType.Nested)
    @OneToMany(mappedBy = "provider", fetch = FetchType.EAGER)
    private List<Participation> participations;

    @OneToMany(mappedBy = "provider", fetch = FetchType.EAGER)
    private List<ProviderSpecialties> specialtyIds;

    public Provider() {
    }

    public Provider(int id, String providerName,
                    List<Participation> participations,
                    List<ProviderSpecialties> specialtyIds) {
        this.id = id;
        this.providerName = providerName;
        this.participations = participations;
        this.specialtyIds = specialtyIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

        if (id != provider.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
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
                "id=" + id +
                ", providerName='" + providerName + '\'' +
                ", specialty=" + sb.toString() +
                '}';
    }
}