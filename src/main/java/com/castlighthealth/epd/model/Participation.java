package com.castlighthealth.epd.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="provider_participations")
public class Participation {

    @Id
    @Column(name="id")
    private int participationId;

    @ManyToOne (targetEntity = Provider.class)
    @JoinColumn(name="provider_id", nullable = false)
    private Provider provider;

    @Column(name="provider_network_id")
    private int providerNetworkId;

    @OneToOne()
    @JoinColumn(name="provider_location_id")
    private ProviderLocation providerLocation;

    public Participation(int participationId, Provider provider, int providerNetworkId, ProviderLocation providerLocation) {
        this.participationId = participationId;
        this.provider = provider;
        this.providerNetworkId = providerNetworkId;
        this.providerLocation = providerLocation;
    }

    public int getParticipationId() {
        return participationId;
    }

    public void setParticipationId(int participationId) {
        this.participationId = participationId;
    }
}
