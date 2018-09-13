package com.zanoshky.currencyfair;

import com.zanoshky.currencyfair.model.VolumeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class MessageProcessorApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProcessorApplication.class);

    public static void main(final String[] args) {
        SpringApplication.run(MessageProcessorApplication.class, args);
    }

    @Scheduled(fixedRate = 6000)
    private void getLatestData() {
        RestTemplate restTemplate = new RestTemplate();
        VolumeMessage quote = restTemplate.getForObject("http://localhost:8080/api/volume-messages", VolumeMessage.class);
        LOGGER.info(quote.toString());
    }

}
