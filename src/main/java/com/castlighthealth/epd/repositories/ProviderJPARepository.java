package com.castlighthealth.epd.repositories;

import com.castlighthealth.epd.model.Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProviderJPARepository extends JpaRepository<Provider, Integer>
{
//    public Iterable<Provider> findIdsBetween(int startRange, int endRange);
}