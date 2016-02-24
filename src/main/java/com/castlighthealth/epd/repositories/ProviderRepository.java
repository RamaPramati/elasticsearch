package com.castlighthealth.epd.repositories;

import com.castlighthealth.epd.model.ESProvider;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created by naveenb on 2/17/16.
 */
public interface ProviderRepository extends ElasticsearchRepository<ESProvider, Integer> {
/*
    List<ESProvider> findProviderParticipationsWithInRange(List<Integer> participationIds, List<Integer> networks,GeoPoint geoPoint, Double radius);
    List<ESProvider> findProviderParticipationsWithSpecialtiesAndInRange(List<Integer> specialties, List<Integer> networks,GeoPoint geoPoint, Double radius);
    List<ESProvider> findProviderParticipationsForProvidersWithInRange(List<Integer> providerIds, List<Integer> networks,GeoPoint geoPoint, Double radius);
*/
}

