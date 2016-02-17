package com.castlighthealth.epd.main;

import com.castlighthealth.epd.model.ESProvider;
import com.castlighthealth.epd.model.Provider;
import com.castlighthealth.epd.repositories.ProviderJPARepository;
import com.castlighthealth.epd.repositories.ProviderRepository;
import org.elasticsearch.common.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.castlighthealth.epd.repositories")
@EnableElasticsearchRepositories("com.castlighthealth.epd.repositories")
@ComponentScan("com.castlighthealth.epd.model")
@EntityScan(basePackages = {"com.castlighthealth.epd.model"})
public class ElasticsearchApplication {
	@Autowired
	ProviderJPARepository providerJPARepository;

	public static void main(String[] args) {

		SpringApplication.run(ElasticsearchApplication.class, args);

	}

	@Bean
	public CommandLineRunner demo(ProviderJPARepository providerJPARepository, ProviderRepository providerRepository) {
		return (args) -> {
			Provider provider = providerJPARepository.findOne(286);
            System.out.println("\n\n\n");
            System.out.println(provider);
            System.out.println("\n\n\n");

            providerRepository.save(new ESProvider(provider));

		};
	}
}
