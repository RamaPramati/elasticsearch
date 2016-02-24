package com.castlighthealth.epd.main;

import com.castlighthealth.epd.model.ESProvider;
import com.castlighthealth.epd.model.Provider;
import com.castlighthealth.epd.repositories.ProviderJPARepository;
import com.castlighthealth.epd.repositories.ProviderRepository;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * Created by vamshi on 19/02/16.
 */
public class ESDocumentBuilder extends RecursiveAction {

    private final long startPage;
    private final long endPage;
    private final int pageSize;
    private final ProviderJPARepository providerJPARepository;
    private final ProviderRepository providerRepository;
    Logger logger = Logger.getLogger(ESDocumentBuilder.class);


    public ESDocumentBuilder(long startPage, long endPage, int pageSize, ProviderJPARepository providerJPARepository, ProviderRepository providerRepository) {
        this.startPage = startPage;
        this.endPage = endPage;
        this.pageSize = pageSize;
        this.providerRepository = providerRepository;
        this.providerJPARepository = providerJPARepository;
    }

    @Override
    protected void compute() {


        if (endPage - startPage > 6) {
            int result = 0;

            logger.info("Splitting tasks for " + startPage + " to  " + endPage);

            List<ESDocumentBuilder> subtasks = createSubtasks();

            for (ESDocumentBuilder subtask : subtasks) {
                subtask.fork();
            }

            for (ESDocumentBuilder subtask : subtasks) {
                subtask.join();
            }


        } else {

            logger.info("Indexing from pages " + startPage + " to  " + endPage);
            for (int i = (int) startPage; i <= endPage ; i++) {
                StopWatch pageTime = new StopWatch();
                pageTime.start();
                Page<Provider> page = providerJPARepository.findAll(new PageRequest(i, pageSize));
                pageTime.stop();
                logger.info("Time for fetching page records " + i + " : " + pageTime.getLastTaskTimeMillis());
                List<ESProvider> providerList = new ArrayList<>(pageSize);
                page.forEach(provider -> providerList.add(new ESProvider(provider)));
                if(!providerList.isEmpty()) {
                    StopWatch indexTime = new StopWatch();
                    indexTime.start();
                    providerRepository.save(providerList);
                    indexTime.stop();
                   logger.info("Time for indexing the page records :  " + i + " : "  + indexTime.getLastTaskTimeMillis());
                }

            }
        }
    }

    private List<ESDocumentBuilder> createSubtasks() {
        List<ESDocumentBuilder> subtasks = new ArrayList<ESDocumentBuilder>();
        long midPage = (endPage - startPage) / 2;
        subtasks.add(new ESDocumentBuilder(startPage, startPage + midPage, pageSize, providerJPARepository, providerRepository));
        subtasks.add(new ESDocumentBuilder(startPage + midPage + 1, endPage, pageSize, providerJPARepository, providerRepository));
        return subtasks;
    }
}
