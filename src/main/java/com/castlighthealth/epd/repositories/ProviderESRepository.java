package com.castlighthealth.epd.repositories;

import com.castlighthealth.epd.model.ESProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProviderESRepository extends ElasticsearchRepository<ESProvider, Integer> {
    public List<ESProvider> findByProviderName(String name);
}
