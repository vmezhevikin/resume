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
import net.devstudy.resume.repository.storage.CourseRepository;
import net.devstudy.resume.repository.storage.EducationRepository;
import net.devstudy.resume.repository.storage.ExperienceRepository;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RemoveOldDataService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(RemoveOldDataService.class);

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private EducationRepository educationRepository;

	@Autowired
	private ExperienceRepository experienceRepository;

	@Value("${course.years.ago}")
	private int courseYearsAgo;

	@Value("${education.years.ago}")
	private int educationYearsAgo;

	@Value("${practic.years.ago}")
	private int practicYearsAgo;

	@Transactional
	// @Scheduled(cron = "${remove.old.data.shedule.expression}")
	public void removeOldCourses()
	{
		LOGGER.debug("Scheduled : removing old courses");

		LocalDate today = new LocalDate();
		LocalDate date = today.minusYears(courseYearsAgo);
		List<Course> coursesToRemove = courseRepository.findByCompletionDateBefore(date.toDate());
		for (Course course : coursesToRemove)
		{
			LOGGER.debug("Scheduled : removing course " + course.getId());
			courseRepository.delete(course);
		}
	}

	@Transactional
	// @Scheduled(cron = "${remove.old.data.shedule.expression}")
	public void removeOldEducation()
	{
		LOGGER.debug("Scheduled : removing old educations");

		LocalDate today = new LocalDate();
		int year = today.minusYears(educationYearsAgo).getYear();
		List<Education> educationsToRemove = educationRepository.findByCompletionYearLessThan(year);
		for (Education education : educationsToRemove)
		{
			LOGGER.debug("Scheduled : removing educations " + education.getId());
			educationRepository.delete(education);
		}
	}

	@Transactional
	// @Scheduled(cron = "${remove.old.data.shedule.expression}")
	public void removeOldExperience()
	{
		LOGGER.debug("Scheduled : removing old experiences");

		LocalDate today = new LocalDate();
		LocalDate date = today.minusYears(practicYearsAgo);
		List<Experience> experiencesToRemove = experienceRepository.findByCompletionDateBefore(date.toDate());
		for (Experience experience : experiencesToRemove)
		{
			LOGGER.debug("Scheduled : removing experience " + experience.getId());
			experienceRepository.delete(experience);
		}
	}
}