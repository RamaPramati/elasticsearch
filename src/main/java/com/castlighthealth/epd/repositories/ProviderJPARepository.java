package com.castlighthealth.epd.repositories;

import com.castlighthealth.epd.model.Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;

public interface ProviderJPARepository extends CrudRepository<Provider, Integer>
{

}