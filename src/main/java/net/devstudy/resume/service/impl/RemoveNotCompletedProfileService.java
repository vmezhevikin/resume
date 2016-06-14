package net.devstudy.resume.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.devstudy.resume.entity.Profile;
import net.devstudy.resume.service.EditProfileService;

@Service
public class RemoveNotCompletedProfileService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(RemoveNotCompletedProfileService.class);

	@Autowired
	private EditProfileService editProfileService;
	
	@Value("${remove.not.completed.profiles.interval}")
	private int removeNotCompletedProfilesInterval;
	
	public void removeNotCompletedProfiles()
	{
		LOGGER.debug("Scheduled : removing not complited profiles");

		LocalDate today = new LocalDate();
		today.minusDays(removeNotCompletedProfilesInterval);
		Timestamp date = new Timestamp(today.toDate().getTime());

		List<Profile> profilesToRemove = editProfileService.findNotCompletedProfilesCreatedBefore(date);
		for (Profile profile : profilesToRemove)
			editProfileService.removeProfile(profile.getId());
	}
}