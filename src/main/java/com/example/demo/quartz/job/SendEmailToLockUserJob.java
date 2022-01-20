package com.example.demo.quartz.job;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
@Profile("prod")
public class SendEmailToLockUserJob extends QuartzJobBean implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LocalDate date = LocalDate.now();
        Date oneMonthAgo = date.minusMonths(1).toDate();

        List<User> lockTarget = userRepository.findUsersByLastLogined(oneMonthAgo);

        for(User user: lockTarget) {
            StopWatch stopWatch = new StopWatch();

            JobParametersBuilder jobParameters = new JobParametersBuilder();
            jobParameters.addString("username", user.getUsername());
            jobParameters.addString("name", user.getName());
            jobParameters.addString("email", user.getEmail());
            jobParameters.addDate("lastLoginedDate", user.getLastLoginedDate());
            Job job = applicationContext.getBean("sendEmailToLockUserBatchJob", Job.class);

            try {
                stopWatch.start();
                log.info("[START BATCH - {}] 계정 : {}", "메일 전송 시작", user.getId());
                JobExecution je = jobLauncher.run(job, jobParameters.toJobParameters());
                Long result = je.getId();
                context.setResult(result);

                user.setIsEnable(false);
                userRepository.save(user);
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                log.info("[END BATCH] {} : {}", "메일 전송 완료", stopWatch.getTotalTimeMillis()/1000.0 + "초");
                stopWatch.stop();
            }
        }
    }
}
