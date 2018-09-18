package com.zanoshky.currencyfair;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.TimeZone;

@SpringBootApplication
public class FrontendApplication {

    public static void main(final String[] args) {
        SpringApplication.run(FrontendApplication.class, args);
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
