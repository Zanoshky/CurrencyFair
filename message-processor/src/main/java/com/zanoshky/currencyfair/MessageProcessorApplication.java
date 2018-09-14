package com.zanoshky.currencyfair;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import com.zanoshky.currencyfair.common.model.dto.VolumeMessage;

@SpringBootApplication
public class MessageProcessorApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProcessorApplication.class);

    public static void main(final String[] args) {
        SpringApplication.run(MessageProcessorApplication.class, args);
    }

    @PostConstruct
    @Scheduled(fixedRate = 6000)
    private void init() {
        final RestTemplate restTemplate = new RestTemplate();
        final VolumeMessage quote = restTemplate.getForObject("http://localhost:8001/api/volume-messages", VolumeMessage.class);
        LOGGER.info(quote.toString());
    }

}
