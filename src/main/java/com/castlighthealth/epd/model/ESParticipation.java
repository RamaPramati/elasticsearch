package com.castlighthealth.epd.model;

/**
 * Created by naveenb on 2/17/16.
 */
public class ESParticipation {
    private int participationId;
    private int providerNetworkId;
    private double lat;
    private double lon;
    private String state;

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
}