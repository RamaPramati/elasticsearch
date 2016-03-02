package com.castlighthealth.epd.main;

import com.castlighthealth.epd.model.ESProvider;
import com.castlighthealth.epd.model.Provider;
import com.castlighthealth.epd.repositories.ProviderJPARepository;
import com.castlighthealth.epd.repositories.ProviderRepository;
import com.castlighthealth.epd.repositories.ProviderESRepository;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.GeoDistanceFilterBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.elasticsearch.index.query.*;
import static org.springframework.data.elasticsearch.core.query.Criteria.*;


import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;


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
	public CommandLineRunner demo(ProviderJPARepository providerJPARepositoryImpl,
                                  ProviderRepository providerRepository,
                                  ProviderESRepository providerESRepository) {
		return (args) -> {
			int page_size = 100000;
			Page<Provider> page = null;
			//long noOfProviders = providerJPARepository.count();
            long noOfProviders = 300000;
            long noOfPages =  noOfProviders/page_size +1;
			System.out.println("Total Poviders -- " +noOfProviders );
			System.out.println("Total pages -- " + noOfPages );
			elasticsearchOperations.deleteIndex(ESProvider.class);
			ForkJoinPool forkJoinPool = new ForkJoinPool(8);
			forkJoinPool.invoke(new ESDocumentBuilder(0, noOfPages, page_size, providerJPARepositoryImpl, providerRepository));

            searchByProviderName(providerESRepository);
		};
	}


	private void searchByProviderName(ProviderESRepository providerESRepository) {
        List<ESProvider> providers = providerESRepository.findByProviderName("john");
        System.out.println("\t\t Performing name search:   Searching for providers with name John");
        if (providers != null) {
            System.out.println (providers.size());
            providers.stream().forEach(provider -> System.out.println (provider.getProviderId() + " : " + provider.getProviderName()));
        }

        System.out.println("\n\n Performing participationId search: ");

        QueryBuilder participationQueryBuilder = QueryBuilders.boolQuery().
                                    must(QueryBuilders.termQuery("participationId", "4855331")).
                                    must(QueryBuilders.termQuery("providerNetworkId", "4438"));
        System.out.println(participationQueryBuilder.toString());
        Iterable<ESProvider> esProviders = providerESRepository.search(participationQueryBuilder);
        if (esProviders != null) {
            esProviders.forEach(provider -> System.out.println (provider.getProviderId() + " : " + provider.getProviderName()));
        }


        System.out.println("\n\n Performing specialty search with GeoLocation limit ");
        GeoDistanceFilterBuilder filterQuery = FilterBuilders.
                                            geoDistanceFilter("geoPoint").
                                            point(41.7648283000, -72.7264945000).
                                            distance(500, DistanceUnit.MILES);

        QueryBuilder geoDistanceQuery = QueryBuilders.filteredQuery(QueryBuilders.termQuery("specialtyIds","1303"), filterQuery);

        System.out.println(geoDistanceQuery.toString());
        Iterable<ESProvider> specialtyResults = providerESRepository.search(geoDistanceQuery);
        if (specialtyResults != null) {
            specialtyResults.forEach(provider -> System.out.println (provider.getProviderId() + " : " + provider.getProviderName()));
        }
	}
}
