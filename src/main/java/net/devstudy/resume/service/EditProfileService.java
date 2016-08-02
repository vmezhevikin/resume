package net.devstudy.resume.service;

import java.util.List;

import javax.annotation.Nonnull;

import net.devstudy.resume.entity.Certificate;
import net.devstudy.resume.entity.Contact;
import net.devstudy.resume.entity.Course;
import net.devstudy.resume.entity.Education;
import net.devstudy.resume.entity.Experience;
import net.devstudy.resume.entity.Hobby;
import net.devstudy.resume.entity.Language;
import net.devstudy.resume.entity.Profile;
import net.devstudy.resume.entity.Skill;
import net.devstudy.resume.form.ChangePasswordForm;
import net.devstudy.resume.form.SignUpForm;

public interface EditProfileService {
	
	@Nonnull Profile createNewProfile(@Nonnull SignUpForm form);

	void updateSkill(long idProfile, @Nonnull List<Skill> editedList);

	void updateLanguage(long idProfile, @Nonnull List<Language> editedList);

	void updateExperience(long idProfile, @Nonnull List<Experience> editedList);

	void updateEducation(long idProfile, @Nonnull List<Education> editedList);

	void updateCourse(long idProfile, @Nonnull List<Course> editedList);

	void updateCertificate(long idProfile, @Nonnull List<Certificate> editedList);

	void addCertificate(long idProfile, @Nonnull Certificate newCertificate);

	void updateGeneralInfo(long idProfile, @Nonnull Profile editedProfile);

	void updateAdditionalInfo(long idProfile, @Nonnull Profile editedProfile);

	void updateContact(long idProfile, @Nonnull Contact newContact);

	void updateHobby(long idProfile, @Nonnull List<Hobby> editedList);

	void removeProfile(long idProfile);

	void removeCourse(long idCourse);

	void removeEducation(long idEducation);

	void removeExperience(long idExperience);

	void addRestoreToken(long idProfile, @Nonnull String token);

	void removeRestoreToken(long idProfile);

	void updatePassword(long idProfile, @Nonnull ChangePasswordForm form);
}
