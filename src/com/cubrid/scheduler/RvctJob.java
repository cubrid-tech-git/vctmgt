package com.cubrid.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class RvctJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		RvctScheduler rvctScheduler = new RvctScheduler();
		rvctScheduler.initRvct();
	}
	
}
