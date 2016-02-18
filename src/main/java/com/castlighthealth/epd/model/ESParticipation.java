package com.castlighthealth.epd.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * Created by naveenb on 2/17/16.
 */
public class ESParticipation implements Serializable {
    @Id
    @Field(
            type = FieldType.Integer,
            index = FieldIndex.analyzed,
            searchAnalyzer = "standard",
            indexAnalyzer = "standard",
            store = true
    )
    private int participationId;
    private int providerNetworkId;

    @Field(
            type = FieldType.Double,
            index = FieldIndex.analyzed,
            searchAnalyzer = "standard",
            indexAnalyzer = "standard",
            store = true
    )
    private double lat;

    @Field(
            type = FieldType.Double,
            index = FieldIndex.analyzed,
            searchAnalyzer = "standard",
            indexAnalyzer = "standard",
            store = true
    )
    private double lon;
    @Field(
            type = FieldType.String,
            index = FieldIndex.analyzed,
            searchAnalyzer = "standard",
            indexAnalyzer = "standard",
            store = true
    )
    private String state;

    public ESParticipation() {
    }

    public ESParticipation(Participation participation) {
        this.participationId = participation.getParticipationId();
        this.providerNetworkId = participation.getProviderNetworkId();
        this.lat = participation.getProviderLocation().getLat();
        this.lon = participation.getProviderLocation().getLon();
        this.state = participation.getProviderLocation().getState();
    }

    public ESParticipation(int participationId, int providerNetworkId, double lat, double lon) {
        this.participationId = participationId;
        this.providerNetworkId = providerNetworkId;
        this.lat = lat;
        this.lon = lon;
    }

    public int getParticipationId() {
        return participationId;
    }

    public int getProviderNetworkId() {
        return providerNetworkId;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getState() {
        return state;
    }
}