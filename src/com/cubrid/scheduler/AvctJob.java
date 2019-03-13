package com.cubrid.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class AvctJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		AvctScheduler avctScheduler = new AvctScheduler();
		avctScheduler.initRvct();
	}
	
}
