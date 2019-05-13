package com.cubrid.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class RvctJob2 implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		RvctSchedulerAfter rvctSchedulerAfter = new RvctSchedulerAfter();
		rvctSchedulerAfter.initRvct();
	}

}