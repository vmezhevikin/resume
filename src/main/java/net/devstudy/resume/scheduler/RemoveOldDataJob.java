package net.devstudy.resume.scheduler;

/*import java.util.List;

import org.joda.time.LocalDate;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import net.devstudy.resume.entity.Course;
import net.devstudy.resume.entity.Education;
import net.devstudy.resume.entity.Experience;
import net.devstudy.resume.service.EditProfileService;
import net.devstudy.resume.service.FindProfileDataService;*/

public class RemoveOldDataJob /*implements Job*/ {
	
	/*private static final Logger LOGGER = LoggerFactory.getLogger(RemoveOldDataJob.class);

	@Autowired
	private EditProfileService editProfileService;

	@Autowired
	private FindProfileDataService findProfileDataService;

	@Value("${course.years.ago}")
	private int courseYearsAgo;

	@Value("${education.years.ago}")
	private int educationYearsAgo;

	@Value("${practic.years.ago}")
	private int practicYearsAgo;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.info("Scheduled: RemoveOldDataServiceJob");	
		removeOldCourses();
		removeOldEducations();
		removeOldExperiences();
	}

	private void removeOldCourses() {
		LOGGER.info("Scheduled : removing old courses");
		LocalDate boundDate = new LocalDate().minusYears(courseYearsAgo);
		List<Course> coursesToRemove = findProfileDataService.findCoursesBefore(boundDate.toDate());
		for (Course course : coursesToRemove) {
			editProfileService.removeCourse(course.getId());
		}
	}

	private void removeOldEducations() {
		LOGGER.info("Scheduled : removing old educations");
		int boundYear = new LocalDate().minusYears(educationYearsAgo).getYear();
		List<Education> educationsToRemove = findProfileDataService.findEducationsBefore(boundYear);
		for (Education education : educationsToRemove) {
			editProfileService.removeEducation(education.getId());
		}
	}

	private void removeOldExperiences() {
		LOGGER.info("Scheduled : removing old experiences");
		LocalDate boundDate = new LocalDate().minusYears(practicYearsAgo);
		List<Experience> experiencesToRemove = findProfileDataService.findExperiencesBefore(boundDate.toDate());
		for (Experience experience : experiencesToRemove) {
			editProfileService.removeExperience(experience.getId());
		}
	}*/
}
