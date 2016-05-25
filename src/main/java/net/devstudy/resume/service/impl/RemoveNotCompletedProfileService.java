package net.devstudy.resume.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.devstudy.resume.entity.Profile;
import net.devstudy.resume.repository.storage.ProfileRepository;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RemoveNotCompletedProfileService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(RemoveNotCompletedProfileService.class);

	@Autowired
	private ProfileRepository profileRepository;

	@Value("${remove.not.completed.profiles.interval}")
	private int removeNotCompletedProfilesInterval;
	
	@Transactional
	//@Scheduled(cron = "${remove.not.completed.profiles.schedule.expression}")
	public void removeNotCompletedProfiles()
	{
		LOGGER.debug("Scheduled : removing not complited profiles");

		LocalDate today = new LocalDate();
		today.minusDays(removeNotCompletedProfilesInterval);
		Timestamp date = new Timestamp(today.toDate().getTime());

		List<Profile> profilesToRemove = profileRepository.findByActiveFalseAndCreatedBefore(date);
		for (Profile profile : profilesToRemove)
			profileRepository.delete(profile);
	}
}