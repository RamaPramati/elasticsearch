package com.castlighthealth.epd.main;

import com.castlighthealth.epd.model.ESProvider;
import com.castlighthealth.epd.model.Provider;
import com.castlighthealth.epd.repositories.ProviderJPARepository;
import com.castlighthealth.epd.repositories.ProviderRepository;
import org.apache.log4j.Logger;
import org.elasticsearch.common.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

@SpringBootApplication
@EnableJpaRepositories("com.castlighthealth.epd.repositories")
@EnableElasticsearchRepositories("com.castlighthealth.epd.repositories")
@ComponentScan("com.castlighthealth.epd.model")
@EntityScan(basePackages = {"com.castlighthealth.epd.model"})
public class ElasticsearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchApplication.class, args);
	}

	@Autowired
	private ElasticsearchOperations elasticsearchOperations;

	@Inject
	private ElasticsearchTemplate elasticsearchTemplate;
	@Bean
	public CommandLineRunner demo(ProviderJPARepository providerJPARepository, ProviderRepository providerRepository) {
		return (args) -> {
			int page_size = 10000;
			Page<Provider> page = null;
			long noOfProviders = providerJPARepository.count();
            long noOfPages =  noOfProviders/page_size +1;
			System.out.println("Total Poviders -- " +noOfProviders );
			System.out.println("Total pages -- " + noOfPages );
			elasticsearchOperations.deleteIndex(ESProvider.class);
			ForkJoinPool forkJoinPool = new ForkJoinPool(8);
			forkJoinPool.invoke(new ESDocumentBuilder(0, noOfPages, page_size, providerJPARepository, providerRepository));

		};
	}
}
