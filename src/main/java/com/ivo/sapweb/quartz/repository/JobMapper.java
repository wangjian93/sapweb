package com.ivo.sapweb.quartz.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ivo.sapweb.quartz.entity.JobEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangjian
 * @date 2018/8/30
 */
public interface JobMapper extends BaseMapper<JobEntity> {

    /**
     * 分页查询job
     * @param page
     * @param jobName
     * @return
     */
    List<JobEntity> listJobEntity(Page<JobEntity> page, @Param("jobName") String jobName);
}
