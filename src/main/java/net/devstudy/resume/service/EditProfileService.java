package net.devstudy.resume.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.restfb.types.User;

import net.devstudy.resume.entity.Certificate;
import net.devstudy.resume.entity.Contact;
import net.devstudy.resume.entity.Course;
import net.devstudy.resume.entity.Education;
import net.devstudy.resume.entity.Experience;
import net.devstudy.resume.entity.Hobby;
import net.devstudy.resume.entity.HobbyName;
import net.devstudy.resume.entity.Language;
import net.devstudy.resume.entity.Profile;
import net.devstudy.resume.entity.Skill;
import net.devstudy.resume.entity.SkillCategory;
import net.devstudy.resume.form.ChangePasswordForm;
import net.devstudy.resume.form.SignUpForm;

public interface EditProfileService
{
	Profile createNewProfile(SignUpForm form);

	List<HobbyName> findListHobbyName();

	List<SkillCategory> findListSkillCategory();

	List<Skill> findListSkill(long idProfile);

	void updateSkill(long idProfile, List<Skill> editedList);

	void addSkill(long idProfile, Skill newSkill);

	List<Language> findListLanguage(long idProfile);

	void updateLanguage(long idProfile, List<Language> editedList);

	void addLanguage(long idProfile, Language newLanguage);

	List<Experience> findListExperience(long idProfile);

	void updateExperience(long idProfile, List<Experience> editedList);

	void addExperience(long idProfile, Experience newExperience);

	List<Education> findListEducation(long idProfile);

	void updateEducation(long idProfile, List<Education> editedList);

	void addEducation(long idProfile, Education newEducation);

	List<Course> findListCourse(long idProfile);

	void updateCourse(long idProfile, List<Course> editedList);

	void addCourse(long idProfile, Course newCourse);

	List<Certificate> findListCertificate(long idProfile);

	void updateCertificate(long idProfile, List<Certificate> editedList);

	void addCertificate(long idProfile, Certificate newCertificate);

	void updateGeneralInfo(long idProfile, Profile editedProfile);

	void updateAdditionalInfo(long idProfile, Profile editedProfile);

	Contact findContact(long idProfile);

	void updateContact(long idProfile, Contact newContact);

	List<Hobby> findListHobby(long idProfile);

	void updateHobby(long idProfile, List<String> editedList);

	List<Profile> findNotCompletedProfilesCreatedBefore(Timestamp date);
	
	void removeProfile(long idProfile);

	List<Course> findCoursesBefore(Date date);

	void removeCourse(long idCourse);

	List<Education> findEducationsBefore(int year);

	void removeEducation(long idEducation);

	List<Experience> findExperiencesBefore(Date date);

	void removeExperience(long idExperience);
	
	void addRestoreToken(long idProfile, String token);
	
	void removeRestoreToken(long idProfile);
	
	void updatePassword(long idProfile, ChangePasswordForm form);
	
	Profile createNewProfileViaFacebook(User user);
}
