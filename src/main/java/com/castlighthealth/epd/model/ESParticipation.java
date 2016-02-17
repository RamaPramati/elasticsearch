package com.castlighthealth.epd.model;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Created by naveenb on 2/17/16.
 */
public class ESParticipation implements Serializable {
    @Id
    private int participationId;
    private int providerNetworkId;
    private double lat;
    private double lon;
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