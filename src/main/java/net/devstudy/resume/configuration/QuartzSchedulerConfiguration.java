package net.devstudy.resume.configuration;

import java.io.IOException;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import net.devstudy.resume.scheduler.RemoveNotCompletedProfileJob;
import net.devstudy.resume.scheduler.RemoveOldDataServiceJob;

@Configuration
public class QuartzSchedulerConfiguration
{
	@Value("${remove.not.completed.profiles.schedule.cron}")
	private String removeNotCompletedProfilesScheduleCron;

	@Value("${remove.old.data.schedule.cron}")
	private String removeOldDataScheduleCron;

	@Bean
	public JobDetail jobDetail()
	{
		return JobBuilder.newJob(RemoveNotCompletedProfileJob.class).withIdentity("RemoveNotCompletedProfileJob", "PurgeDB").storeDurably(true)
				.build();
	}

	@Bean
	public Trigger trigger()
	{
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(removeNotCompletedProfilesScheduleCron);
		return TriggerBuilder.newTrigger().withIdentity("RemoveNotCompletedProfileTrigger", "PurgeDB").withSchedule(scheduleBuilder).build();
	}

	@Bean//(destroyMethod = "shutdown")
	public Scheduler scheduler(JobDetail jobDetail, Trigger trigger) throws SchedulerException, IOException
	{
		StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
		schedulerFactory.initialize(new ClassPathResource("quartz.properties").getInputStream());

		Scheduler scheduler = schedulerFactory.getScheduler();
		scheduler.scheduleJob(jobDetail, trigger);
		scheduler.start();
		return scheduler;
	}

	@Bean
	public JobDetail jobDetail1()
	{
		return JobBuilder.newJob(RemoveOldDataServiceJob.class).withIdentity("RemoveOldDataServiceJob", "PurgeDB").storeDurably(true).build();
	}

	@Bean
	public Trigger trigger1()
	{
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(removeOldDataScheduleCron);
		return TriggerBuilder.newTrigger().withIdentity("RemoveOldDataTrigger", "PurgeDB").withSchedule(scheduleBuilder).build();
	}

	@Bean//(destroyMethod = "shutdown")
	public Scheduler scheduler1(JobDetail jobDetail1, Trigger trigger1) throws SchedulerException, IOException
	{
		StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
		schedulerFactory.initialize(new ClassPathResource("quartz.properties").getInputStream());

		Scheduler scheduler = schedulerFactory.getScheduler();
		scheduler.scheduleJob(jobDetail1, trigger1);
		scheduler.start();
		return scheduler;
	}
}