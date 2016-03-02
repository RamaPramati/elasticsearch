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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories("com.castlighthealth.epd.repositories")
@EnableElasticsearchRepositories("com.castlighthealth.epd.repositories")
@ComponentScan("com.castlighthealth.epd.model")
@EntityScan(basePackages = {"com.castlighthealth.epd.model"})
public class ElasticsearchSingleThreadApplication {
    @Autowired
    ProviderJPARepository providerJPARepository;
    @Inject
    ElasticsearchTemplate elasticsearchTemplate;

    public static void main(String[] args) {

        SpringApplication.run(ElasticsearchSingleThreadApplication.class, args);

    }

    @Bean
    public CommandLineRunner demo(ProviderJPARepository providerJPARepository, ProviderRepository providerRepository) {
        return (args) -> {
            boolean done = true;
            int page_size = 10000;
            Page<Provider> page = null;
            int page_number = 0;

            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            do {
                StopWatch pageTime = new StopWatch();
                pageTime.start();
                page = providerJPARepository.findAll(new PageRequest(page_number,page_size));
                pageTime.stop();
                System.out.println("Time for fetching page records " + page_number + " : " + pageTime.getLastTaskTimeMillis() );
                page_number++;


                StopWatch indexTime = new StopWatch();
                indexTime.start();
                List<ESProvider> providerList = new ArrayList<>(page_size);
                page.forEach(provider -> providerList.add(new ESProvider(provider)));
                providerRepository.save(providerList);
                indexTime.stop();
                System.out.println("Time for indexing the page records :" + indexTime.getLastTaskTimeMillis() );
            } while (page.hasNext());




//            	ESProvider esprovider = providerRepository.findOne(286);
//            System.out.println("\n\n\n");
//            System.out.println(esprovider);
//            System.out.println("\n\n\n");
        };
    }
}
