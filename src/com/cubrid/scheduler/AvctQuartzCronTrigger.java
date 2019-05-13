package com.cubrid.scheduler;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.CronScheduleBuilder.*;

public class AvctQuartzCronTrigger {

	public void start() {
		try {
		// Grab the Scheduler instance from the Factory
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		// and start it off
		scheduler.start();
		
		// define the job and tie it to our Hello.job class
		JobDetail job = newJob(AvctJob.class)
				.withIdentity("avctScheduling", "techGroup")
				.build();
		
		// Trigger the job to run now, ss mm hh dd MM Week
		// (초, 분, 시, 일, 월, 요일, 연도)
		Trigger trigger = newTrigger()
				.withIdentity("avctTrigger", "techGroup")
				.withSchedule(cronSchedule("0 0 0 1 1 ?"))
				.build();
		
		// Tell quartz to schedule the job using out trigger
		scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			System.out.println(e.getMessage());
		}
	}
}