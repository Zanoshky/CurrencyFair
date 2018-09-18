package com.zanoshky.currencyfair;

import com.zanoshky.currencyfair.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class ProcessorApplication {
    @Autowired
    CacheService cacheService;

    public static void main(final String[] args) {
        SpringApplication.run(ProcessorApplication.class, args);
    }

    @PostConstruct
    private void init() {
        cacheService.loadCurrencyPairsFromDbIntoMap();
    }

    @Scheduled(fixedRate = 10000, initialDelay = 10000)
    private void downloadLatestVolumeMessages() {
        cacheService.triggerStatisticalSync();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer initJackson() {
        return new Jackson2ObjectMapperBuilderCustomizer() {
            @Override
            public void customize(final Jackson2ObjectMapperBuilder builder) {
                builder.timeZone(TimeZone.getDefault());
            }
        };
    }

}
