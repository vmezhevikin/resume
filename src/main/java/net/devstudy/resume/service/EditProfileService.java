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
import net.devstudy.resume.form.CertificateForm;
import net.devstudy.resume.form.ChangePasswordForm;
import net.devstudy.resume.form.CourseForm;
import net.devstudy.resume.form.EducationForm;
import net.devstudy.resume.form.ExperienceForm;
import net.devstudy.resume.form.HobbyForm;
import net.devstudy.resume.form.LanguageForm;
import net.devstudy.resume.form.SignUpForm;
import net.devstudy.resume.form.SkillForm;

public interface EditProfileService
{
	Profile createNewProfile(SignUpForm form);

	List<HobbyName> listHobbyName();

	List<SkillCategory> listSkillCategory();

	List<Skill> listSkill(long idProfile);

	void updateSkill(long idProfile, SkillForm form);

	void addSkill(long idProfile, Skill form);

	List<Language> listLanguage(long idProfile);

	void updateLanguage(long idProfile, LanguageForm form);

	void addLanguage(long idProfile, Language form);

	List<Experience> listExperience(long idProfile);

	void updateExperience(long idProfile, ExperienceForm form);

	void addExperience(long idProfile, Experience form);

	List<Education> listEducation(long idProfile);

	void updateEducation(long idProfile, EducationForm form);

	void addEducation(long idProfile, Education form);

	List<Course> listCourse(long idProfile);

	void updateCourse(long idProfile, CourseForm form);

	void addCourse(long idProfile, Course form);

	List<Certificate> listCertificate(long idProfile);

	void updateCertificate(long idProfile, CertificateForm form);

	void addCertificate(long idProfile, Certificate form);

	void updateGeneralInfo(long idProfile, Profile form);

	void updateAdditionalInfo(long idProfile, Profile form);

	Contact contact(long idProfile);

	void updateContact(long idProfile, Contact form);

	List<Hobby> listHobby(long idProfile);

	void updateHobby(long idProfile, HobbyForm form);

	List<Profile> notCompletedProfilesCreatedBefore(Timestamp date);
	
	void removeProfile(long idProfile);

	List<Course> coursesBefore(Date date);

	void removeCourse(long idCourse);

	List<Education> educationsBefore(int year);

	void removeEducation(long idEducation);

	List<Experience> experiencesBefore(Date date);

	void removeExperience(long idExperience);
	
	void addRestoreToken(long idProfile, String token);
	
	void removeRestoreToken(long idProfile);
	
	void updatePassword(long idProfile, ChangePasswordForm form);
	
	Profile createNewProfileViaFacebook(User user);
}
