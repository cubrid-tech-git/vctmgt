package com.cubrid.scheduler;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.CronScheduleBuilder.*;

public class RvctQuartzCronTrigger {

	public void start() {
		try {
		// Grab the Scheduler instance from the Factory
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		Scheduler scheduler2 = StdSchedulerFactory.getDefaultScheduler();
		// and start it off
		scheduler.start();
		scheduler2.start();
		
		// define the job and tie it to our Hello.job class
		JobDetail job = newJob(RvctJob.class)
				.withIdentity("rvctScheduling", "techGroup")
				.build();
		
		JobDetail job2 = newJob(RvctJob2.class)
				.withIdentity("rvctScheduling2", "techGroup2")
				.build();
		
		// Trigger the job to run now, ss mm hh dd MM Week
		// (ÃÊ, ºÐ, ½Ã, ÀÏ, ¿ù, ¿äÀÏ, ¿¬µµ)
		Trigger trigger = newTrigger()
				.withIdentity("rvctTrigger", "techGroup")
				.withSchedule(cronSchedule("0 0 0 1 1/3 ?"))
//				.withSchedule(cronSchedule("0 40 13 6 1/3 ?"))
				.build();
		
		Trigger trigger2 = newTrigger()
				.withIdentity("rvctTriger2", "techGroup2")
				.withSchedule(cronSchedule("0 0 0 10 1/3 ?"))
//				.withSchedule(cronSchedule("0 45 13 6 1/3 ?"))
				.build();
		
		// Tell quartz to schedule the job using out trigger
		scheduler.scheduleJob(job, trigger);
		scheduler2.scheduleJob(job2, trigger2);
		} catch (SchedulerException e) {
			System.out.println(e.getMessage());
		}
	}
}