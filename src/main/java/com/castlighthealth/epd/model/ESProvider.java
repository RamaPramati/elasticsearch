package com.castlighthealth.epd.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by naveenb on 2/17/16.
 */
@Document(indexName = "provider_sample")
public class ESProvider implements Serializable {

    @Id
    private int providerId;
    private String providerName;
    private List<ESParticipation> esParticipations;
    private List<Integer> specialtyIds;


    public ESProvider(Provider provider) {
        this.providerId = provider.getProviderId();
        this.providerName = provider.getProviderName();
        this.esParticipations = new ArrayList<>(provider.getParticipations().size());
        for( Participation p : provider.getParticipations()) {
            this.esParticipations.add(new ESParticipation(p));
        }
        this.specialtyIds = new ArrayList<>(provider.getSpecialtyIds().size());
        for( ProviderSpecialties ps : provider.getSpecialtyIds()) {
            this.specialtyIds.add(ps.getSpecialtyId());
        }
    }

    public ESProvider() {
    }

    public ESProvider(int providerId, String providerName, List<ESParticipation> esParticipations, List<Integer> specialtyIds) {
        this.providerId = providerId;
        this.providerName = providerName;
        this.esParticipations = esParticipations;
        this.specialtyIds = specialtyIds;
    }

    public int getProviderId() {
        return providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public List<ESParticipation> getEsParticipations() {
        return esParticipations;
    }

    public List<Integer> getSpecialtyIds() {
        return specialtyIds;
    }
}

