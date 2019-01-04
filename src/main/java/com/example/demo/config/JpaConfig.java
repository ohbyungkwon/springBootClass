package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

@EnableJpaAuditing
@Configuration
public class JpaConfig {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
