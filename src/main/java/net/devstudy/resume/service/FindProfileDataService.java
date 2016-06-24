package net.devstudy.resume.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Nonnull;

import net.devstudy.resume.entity.Certificate;
import net.devstudy.resume.entity.Contact;
import net.devstudy.resume.entity.Course;
import net.devstudy.resume.entity.Education;
import net.devstudy.resume.entity.Experience;
import net.devstudy.resume.entity.Hobby;
import net.devstudy.resume.entity.Language;
import net.devstudy.resume.entity.Skill;

public interface FindProfileDataService {
	
	@Nonnull List<Skill> findListSkill(long idProfile);

	@Nonnull List<Language> findListLanguage(long idProfile);

	@Nonnull List<Experience> findListExperience(long idProfile);

	@Nonnull List<Education> findListEducation(long idProfile);

	@Nonnull List<Course> findListCourse(long idProfile);

	@Nonnull List<Certificate> findListCertificate(long idProfile);

	@Nonnull Contact findContact(long idProfile);

	@Nonnull List<Hobby> findListHobby(long idProfile);

	@Nonnull List<Course> findCoursesBefore(@Nonnull Date date);

	@Nonnull List<Education> findEducationsBefore(int year);

	@Nonnull List<Experience> findExperiencesBefore(@Nonnull Date date);
}
