package com.castlighthealth.epd.model;

import java.util.List;
import com.castlighthealth.epd.model.Participation;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Represents a person who is a healthcare provider.
 */
@Document(indexName = "provider_sample", type = "provider")
@Entity
@Table(name = "care_teams")
public class Provider {
    @Id
    private int providerId;
    private String providerName;
    private String providerNaturalName;

    @Field(type = FieldType.Nested)
    private List<Participation> participations;
    private List<Integer> specialtyIds;

    public Provider() {
    }

    public Provider(int providerId, String providerName, String providerNaturalName,
                    List<Participation> participations,
                    List<Integer> specialtyIds) {
        this.providerId = providerId;
        this.providerName = providerName;
        this.providerNaturalName = providerNaturalName;
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

    public String getProviderNaturalName() {
        return providerNaturalName;
    }

    public void setProviderNaturalName(String providerNaturalName) {
        this.providerNaturalName = providerNaturalName;
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
