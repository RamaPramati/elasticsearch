package com.castlighthealth.epd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="provider_locations")
public class ProviderLocation {
    @Id
    @Column(name="id")
    private int id;

    @Column(name="state")
    private String state;

    @Column(name="latitude")
    private float lat;

    @Column(name="longitude")
    private float lon;

    public ProviderLocation(int id, String state, float lat, float lon) {
        this.id = id;
        this.state = state;
        this.lat = lat;
        this.lon = lon;
    }
}
