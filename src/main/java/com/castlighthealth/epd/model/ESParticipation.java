package com.castlighthealth.epd.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.io.Serializable;

/**
 * Created by naveenb on 2/17/16.
 */
public class ESParticipation implements Serializable {
    @Id
    @Field(store = true)
    private int participationId;

    @Field(store = true)
    private int providerNetworkId;

    @GeoPointField()
    private GeoPoint geoPoint;

    @Field(store = true)
    private String state;

    public ESParticipation() {
    }

    public ESParticipation(Participation participation) {
        this.participationId = participation.getParticipationId();
        this.providerNetworkId = participation.getProviderNetworkId();
        this.geoPoint = new GeoPoint(participation.getProviderLocation().getLat(),participation.getProviderLocation().getLon());
        this.state = participation.getProviderLocation().getState();
    }



    public int getParticipationId() {
        return participationId;
    }

    public int getProviderNetworkId() {
        return providerNetworkId;
    }



    public String getState() {
        return state;
    }
}