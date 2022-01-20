package com.example.demo.quartz;

import com.example.demo.quartz.job.SendEmailToLockUserJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Profile("prod")
public class QuartzManagement {
    @Autowired
    private Scheduler scheduler;

    @Value("${spring.profiles.active}")
    private String profiles;

    @PostConstruct
    public void start() {
        if(profiles.equals("prod")) {
            JobDetail sendEmailToLockUser = this.buildJobDetail(SendEmailToLockUserJob.class, "SendEmailLockUser", "tasklet");

            try {
                scheduler.scheduleJob(sendEmailToLockUser, buildJobTrigger("0 0 1 * * ?"));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private Trigger buildJobTrigger(String scheduleExp) {
        return TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)).build();
    }

    private JobDetail buildJobDetail(Class job, String name, String group) {
        return JobBuilder.newJob(job).withIdentity(name, group).build();
    }
}
