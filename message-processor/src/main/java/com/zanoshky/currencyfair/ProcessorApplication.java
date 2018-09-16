package com.zanoshky.currencyfair;

import com.zanoshky.currencyfair.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class ProcessorApplication {
    @Autowired
    CacheService cacheService;

    public static void main(final String[] args) {
        SpringApplication.run(ProcessorApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @PostConstruct
    private void init() {
        cacheService.loadCurrencyPairsFromDbIntoMap();
    }

    @Scheduled(fixedRate = 60000, initialDelay = 10000)
    private void downloadLatestVolumeMessages() {
        cacheService.triggerStatisticalSync();
    }

}
