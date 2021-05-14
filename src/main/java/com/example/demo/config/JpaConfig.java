package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
public class JpaConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    public PlatformTransactionManager defaultTransactionManager(){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);

        transactionManager.setGlobalRollbackOnParticipationFailure(true);
        return transactionManager;
    }
}
