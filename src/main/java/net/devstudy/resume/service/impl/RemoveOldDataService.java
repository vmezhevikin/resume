package net.devstudy.resume.service.impl;

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

import net.devstudy.resume.entity.Course;
import net.devstudy.resume.entity.Education;
import net.devstudy.resume.entity.Experience;
import net.devstudy.resume.service.EditProfileService;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RemoveOldDataService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(RemoveOldDataService.class);

	@Autowired
	private EditProfileService editProfileService;

	@Value("${course.years.ago}")
	private int courseYearsAgo;

	@Value("${education.years.ago}")
	private int educationYearsAgo;

	@Value("${practic.years.ago}")
	private int practicYearsAgo;

	@Transactional
	public void removeOldCourses()
	{
		LOGGER.debug("Scheduled : removing old courses");
		
		LocalDate today = new LocalDate();
		LocalDate date = today.minusYears(courseYearsAgo);
		List<Course> coursesToRemove = editProfileService.coursesBefore(date.toDate());
		for (Course course : coursesToRemove)
		{
			long idProfile = course.getProfile().getId();
			editProfileService.removeCourse(idProfile, course);
		}
	}
	
	@Transactional
	public void removeOldEducations()
	{
		LOGGER.debug("Scheduled : removing old educations");

		LocalDate today = new LocalDate();
		int year = today.minusYears(educationYearsAgo).getYear();
		List<Education> educationsToRemove = editProfileService.educationBefore(year);
		for (Education education : educationsToRemove)
		{
			long idProfile = education.getProfile().getId();
			editProfileService.removeEducation(idProfile, education);
		}
	}
	
	@Transactional
	public void removeOldExperiences()
	{
		LOGGER.debug("Scheduled : removing old experiences");

		LocalDate today = new LocalDate();
		LocalDate date = today.minusYears(practicYearsAgo);
		List<Experience> experiencesToRemove = editProfileService.experienceBefore(date.toDate());
		for (Experience experience : experiencesToRemove)
		{
			long idProfile = experience.getProfile().getId();
			editProfileService.removeExperience(idProfile, experience);
		}
	}
}