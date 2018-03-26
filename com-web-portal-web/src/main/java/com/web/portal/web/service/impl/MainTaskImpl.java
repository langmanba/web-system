package com.web.portal.web.service.impl;

import com.web.portal.web.service.MainTask;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("mainTask")
public class MainTaskImpl implements MainTask {

    @Scheduled(cron = "0 58 11 ? * *" )
    public void task() throws Exception {
        for (int i = 0; i < 10; i++) {
            Thread.sleep(2);
            System.out.println("Task begin...");

        }
    }
}
