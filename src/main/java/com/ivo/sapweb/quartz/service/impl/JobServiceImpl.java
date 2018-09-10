package com.ivo.sapweb.quartz.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.ivo.sapweb.common.util.StringUtil;
import com.ivo.sapweb.quartz.repository.JobMapper;
import com.ivo.sapweb.quartz.service.JobService;
import com.ivo.sapweb.quartz.entity.JobEntity;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangjian
 * @date 2018/8/30
 */
@Service
public class JobServiceImpl implements JobService, Serializable {

    private final static Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private JobMapper jobMapper;

    @Override
    public Page<JobEntity> listJob(JobEntity jobEntity, Integer pageNo, Integer pageSize) {
        if(StringUtil.isBlank(jobEntity.getJobName())) {
            jobEntity.setJobName(null);
        }
        Page<JobEntity> page = new Page<>(pageNo, pageSize);
        List<JobEntity> list = jobMapper.listJobEntity(page, jobEntity.getJobName());
        page.setRecords(list);
        return page;
    }

    @Override
    public void addJob(JobEntity jobEntity) {
        try {
            //获取Scheduler实例、废弃、使用自动注入的scheduler、否则spring的service将无法注入
            //Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            //如果是修改  展示旧的 任务
            if(jobEntity.getOldJobGroup()!=null){
                JobKey key = new JobKey(jobEntity.getOldJobName(),jobEntity.getOldJobGroup());
                scheduler.deleteJob(key);
            }
            //实例化执行类
            Class cls = Class.forName(jobEntity.getJobClassName());
            cls.newInstance();

            //构建job信息
            JobDetail job = JobBuilder.newJob(cls)
                    .withIdentity(jobEntity.getJobName(), jobEntity.getJobGroup())
                    .withDescription(jobEntity.getDescription())
                    .build();

            //触发时间点
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(jobEntity.getCronExpression());
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobEntity.getJobName(), jobEntity.getJobGroup())
                    .startNow()
                    .withSchedule(cronScheduleBuilder)
                    .build();

            //交由Scheduler安排触发
            scheduler.scheduleJob(job, trigger);

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SchedulerException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void triggerJob(JobEntity jobEntity) {
        try {
            JobKey jobKey = JobKey.jobKey(jobEntity.getJobName(), jobEntity.getJobGroup());
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void pauseJob(JobEntity jobEntity) {
        try {
            JobKey jobKey = JobKey.jobKey(jobEntity.getJobName(), jobEntity.getJobGroup());
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void resumeJob(JobEntity jobEntity) {
        try {
            JobKey jobKey = JobKey.jobKey(jobEntity.getJobName(), jobEntity.getJobGroup());
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void removeJob(JobEntity jobEntity) {

        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobEntity.getJobName(), jobEntity.getJobGroup());
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            JobKey jobKey = JobKey.jobKey(jobEntity.getJobName(), jobEntity.getJobGroup());
            scheduler.deleteJob(jobKey);

        } catch (SchedulerException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
