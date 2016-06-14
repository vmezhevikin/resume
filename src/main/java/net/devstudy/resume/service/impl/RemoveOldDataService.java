package net.devstudy.resume.service.impl;

import java.util.List;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.devstudy.resume.entity.Course;
import net.devstudy.resume.entity.Education;
import net.devstudy.resume.entity.Experience;
import net.devstudy.resume.service.EditProfileService;

@Service
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

	public void removeOldCourses()
	{
		LOGGER.debug("Scheduled : removing old courses");
		
		LocalDate today = new LocalDate();
		LocalDate date = today.minusYears(courseYearsAgo);
		List<Course> coursesToRemove = editProfileService.findCoursesBefore(date.toDate());
		for (Course course : coursesToRemove)
			editProfileService.removeCourse(course.getId());
	}
	
	public void removeOldEducations()
	{
		LOGGER.debug("Scheduled : removing old educations");

		LocalDate today = new LocalDate();
		int year = today.minusYears(educationYearsAgo).getYear();
		List<Education> educationsToRemove = editProfileService.findEducationsBefore(year);
		for (Education education : educationsToRemove)
			editProfileService.removeEducation(education.getId());
	}
	
	public void removeOldExperiences()
	{
		LOGGER.debug("Scheduled : removing old experiences");

		LocalDate today = new LocalDate();
		LocalDate date = today.minusYears(practicYearsAgo);
		List<Experience> experiencesToRemove = editProfileService.findExperiencesBefore(date.toDate());
		for (Experience experience : experiencesToRemove)
			editProfileService.removeExperience(experience.getId());
	}
}