package com.ivo.sapweb.quartz.config;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * quartz配置
 *
 * @author wangjian
 * @date 2018/8/29
 */
@Configuration
public class SchedulerConfig {

    @Autowired
    private SpringJobFactory springJobFactory;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setOverwriteExistingJobs(true);
        factoryBean.setAutoStartup(true);
        factoryBean.setStartupDelay(5);
        factoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        factoryBean.setConfigLocation(new ClassPathResource("quartz.properties"));
        factoryBean.setJobFactory(springJobFactory);
        return factoryBean;
    }

    /**
     * 通过SchedulerFactoryBean获取Scheduler的实例
     */
    @Bean
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }
}
