package com.example.demo.config;

import org.springframework.batch.core.configuration.BatchConfigurationException;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.MapJobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;

@Configuration
@Profile("!local")
public class InMemoryBatchConfig implements BatchConfigurer {
    @Autowired
    @Qualifier("transactionManager")
    private PlatformTransactionManager transactionManager;

    private JobRepository jobRepository;

    private JobLauncher jobLauncher;

    private JobExplorer jobExplorer;

    @Override
    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    @Override
    @Bean("JobRepository")
    public JobRepository getJobRepository() {
        return jobRepository;
    }

    @Override
    @Bean("JobLauncher")
    public JobLauncher getJobLauncher() {
        return jobLauncher;
    }

    @Override
    @Bean("JobExplorer")
    public JobExplorer getJobExplorer() {
        return jobExplorer;
    }

    @PostConstruct
    public void initialize() {
        try {
            MapJobRepositoryFactoryBean jobRepositoryFactoryBean = new MapJobRepositoryFactoryBean(this.transactionManager);
            jobRepositoryFactoryBean.afterPropertiesSet();
            this.jobRepository = jobRepositoryFactoryBean.getObject();

            MapJobExplorerFactoryBean jobExplorerFactoryBean = new MapJobExplorerFactoryBean(jobRepositoryFactoryBean);
            jobExplorerFactoryBean.afterPropertiesSet();
            this.jobExplorer = jobExplorerFactoryBean.getObject();

            SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
            jobLauncher.setJobRepository(jobRepository);
            jobLauncher.afterPropertiesSet();
            this.jobLauncher = jobLauncher;
        } catch (Exception e) {
            throw new BatchConfigurationException(e);
        }
    }
}