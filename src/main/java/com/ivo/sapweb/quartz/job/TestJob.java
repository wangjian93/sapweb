package com.ivo.sapweb.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.Serializable;

/**
 * 实现序列化接口、防止重启应用出现quartz Couldn't retrieve job because a required class was not found 的问题
 */
public class TestJob implements Job,Serializable {

	private static final long serialVersionUID = 1L;
    
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println(context.getJobDetail().getKey().toString() + "任务执行成功");
	}
}
