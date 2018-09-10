package com.ivo.sapweb.quartz.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ivo.sapweb.quartz.entity.JobEntity;

import java.util.List;

/**
 * @author wangjian
 * @date 2018/8/30
 */
public interface JobService {

    Page<JobEntity> listJob(JobEntity jobEntity, Integer pageNo, Integer pageSize);

    void addJob(JobEntity jobEntity);

    void triggerJob(JobEntity jobEntity);

    void pauseJob(JobEntity jobEntity);

    void resumeJob(JobEntity jobEntity);

    void removeJob(JobEntity jobEntity);
}
