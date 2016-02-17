package com.castlighthealth.epd.repositories;

import com.castlighthealth.epd.model.ESProvider;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by naveenb on 2/17/16.
 */
public interface ProviderRepository extends ElasticsearchRepository<ESProvider, Integer> {
}
