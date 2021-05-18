package com.example.demo.batch.job;

import com.example.demo.batch.ItemCutClass;
import com.example.demo.batch.ProductItemProcessor;
import com.example.demo.domain.Product;
import com.example.demo.dto.elevenshop.ElevenProduct;
import com.example.demo.dto.elevenshop.ProductSerachResponse;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.*;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Configuration
@EnableBatchProcessing
@Profile("!local")
public class ProductInsertJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public Job setJob(){
        return jobBuilderFactory.get("productInsertJob")
                .start(setMasterStep())
                .build();
    }

    public Step setMasterStep(){
        return stepBuilderFactory.get("masterStep")
                .partitioner(setSlaveStep())
                .partitioner("slaveStep", new ItemCutClass())
                .gridSize(10)
                .taskExecutor(taskExecutor)
                .build();
    }

    private Step setSlaveStep(){
        return stepBuilderFactory.get("slaveStep")
                .<ElevenProduct, Product> chunk(100)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<ElevenProduct> reader(){
        return new ItemReader<ElevenProduct>(){
            List<ElevenProduct> productList = giveReader(null);
            int index = 0;
            @Override
            public ElevenProduct read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                if(index == productList.size() - 1) return null;
                return productList.get(index++);
            }
        };
    }

    @Bean
    @StepScope
    public ItemProcessor<ElevenProduct, Product> processor(){
        return new ProductItemProcessor();
    }

    @Bean
    public JpaItemWriter<Product> writer(){
        JpaItemWriter<Product> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    public List<ElevenProduct> giveReader(@Value(value = "#{stepExecutionCOntext[category]}") String category){
        ResponseEntity<ProductSerachResponse> productSerachResponse = restTemplate.getForEntity("http://openapi.11st.co.kr/openapi/OpenApiService.tmall?apiCode=ProductSearch&key=3cbec99cb426bf807ea21cdfea22c162&expCnt=1&keyword="+category
                , ProductSerachResponse.class);

        ProductSerachResponse productSerachResponseTemp = productSerachResponse.getBody();
        return productSerachResponseTemp.getProducts();
    }
}
