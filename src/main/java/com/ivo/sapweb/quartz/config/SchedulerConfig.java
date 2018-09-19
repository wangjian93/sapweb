package com.ivo.sapweb.quartz.config;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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
    private Environment env;

    @Autowired
    private SpringJobFactory springJobFactory;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setOverwriteExistingJobs(true);
        factoryBean.setAutoStartup(true);
        factoryBean.setStartupDelay(5);
        factoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        factoryBean.setJobFactory(springJobFactory);

        String[] profiles = env.getActiveProfiles();
        // 根据开发、测试、生产环境不同运行不同的配置
        String classPathResource = "quartz-" + profiles[0] + ".properties";

        factoryBean.setConfigLocation(new ClassPathResource(classPathResource));

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
