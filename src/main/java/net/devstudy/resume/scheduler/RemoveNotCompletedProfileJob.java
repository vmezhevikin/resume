package net.devstudy.resume.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import net.devstudy.resume.service.impl.RemoveNotCompletedProfileService;

public class RemoveNotCompletedProfileJob implements Job
{
	private static final Logger LOGGER = LoggerFactory.getLogger(RemoveNotCompletedProfileJob.class);
	
	@Autowired
	private RemoveNotCompletedProfileService jobService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		LOGGER.debug("Scheduled: RemoveNotCompletedProfileJob");	
		jobService.removeNotCompletedProfiles();
	}
}
