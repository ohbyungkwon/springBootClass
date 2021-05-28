package com.example.demo.batch.job;

import com.example.demo.repository.UserRepository;
import com.example.demo.utils.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Date;

@Slf4j
@Configuration
@EnableBatchProcessing
@Profile("!local")
public class SendEmailToLockUserJob {
    @Value("$(mail.smtp.username)")
    private String fromEmail;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private UserRepository userRepository;

    @Bean
    public Job sendEmailJob() {
        return jobBuilderFactory.get("sendEmailToLockUserBatchJob")
                .preventRestart()
                .start(makeEmailContent())
                .on("FAILED")
                    .to(sendEmailAdminStep())
                        .on("*")
                        .end()
                .from(makeEmailContent())
                    .on("UNKNOWN")
                    .end()
                .from(makeEmailContent())
                    .on("*")
                        .to(sendEmailUserStep())
                            .on("FAILED")
                                .to(sendEmailAdminStep())
                                    .on("*")
                                    .end()
                .end()
                .build();
    }

    @Bean
    @JobScope
    public Step makeEmailContent() {
        return stepBuilderFactory.get("makeEmailContent")
                .tasklet(makeEmailContentTask(null, null, null, null))
                .build();
    }

    @Bean
    @JobScope
    public Step sendEmailUserStep() {
        return stepBuilderFactory.get("sendEmailUserStep")
                .tasklet(sendEmailUserStepTask())
                .build();
    }

    @Bean
    @JobScope
    public Step sendEmailAdminStep() {
        return stepBuilderFactory.get("sendEmailAdminStep")
                .tasklet(sendEmailAdminStepTask(null, null))
                .build();
    }


    @Bean
    @StepScope
    public Tasklet makeEmailContentTask(@Value("#{jobParameters[id]}") String id,
                                        @Value("#{jobParameters[name]}") String name,
                                        @Value("#{jobParameters[email]}") String email,
                                        @Value("#{jobParameters[lastLogined]}") Date lastLogined) {
        return ((contribution, chunkContext) -> {
            ExecutionContext context = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();

            String subject = "휴명 계정 전환 공지";
            String content = id + "(" + name + ") 님 안녕하세요." +
                    "보안 정책에 의해 90일 이상 접속하지 않아 " + id + "계정이 잠금되었습니다." +
                    "마지막 접속일: " + lastLogined;

            MailUtil mailUtil = new MailUtil(new JavaMailSenderImpl());
            mailUtil.setSubject(subject);
            mailUtil.setText(content);
            mailUtil.setToEmail(email);
            mailUtil.setToEmail(fromEmail);

            context.put("emailContent", mailUtil);

            return RepeatStatus.FINISHED;
        });
    }

    @Bean
    @StepScope
    public Tasklet sendEmailUserStepTask(){
        return ((contribution, chunkContext) -> {
            ExecutionContext context = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
            MailUtil mailUtil = (MailUtil) context.get("emailContent");

            if(mailUtil == null){
                contribution.setExitStatus(ExitStatus.FAILED);
                return RepeatStatus.FINISHED;
            }

            mailUtil.send();
            return RepeatStatus.FINISHED;
        });
    }

    @Bean
    @StepScope
    public Tasklet sendEmailAdminStepTask(@Value("#{jobParameters[id]}") String id,
                                          @Value("#{jobParameters[email]}") String email){
        return ((contribution, chunkContext) -> {
            ExecutionContext context = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
            MailUtil mailUtil = (MailUtil) context.get("emailContent");
            if(mailUtil == null){
                contribution.setExitStatus(ExitStatus.FAILED);
                return RepeatStatus.FINISHED;
            }

            mailUtil.setSubject("[전송실패] 휴명 계정 전환 공지");
            mailUtil.setToEmail("admin@gmail.com");
            mailUtil.setText(id + "계정(" + email + ")에" + "이메일 전송을 실패했습니다.");

            mailUtil.send();
            return RepeatStatus.FINISHED;
        });
    }

}