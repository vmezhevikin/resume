package net.devstudy.resume.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.devstudy.resume.entity.Certificate;
import net.devstudy.resume.entity.Hobby;
import net.devstudy.resume.form.CertificateForm;
import net.devstudy.resume.form.ChangePasswordForm;
import net.devstudy.resume.form.CourseForm;
import net.devstudy.resume.form.EducationForm;
import net.devstudy.resume.form.ExperienceForm;
import net.devstudy.resume.form.HobbyForm;
import net.devstudy.resume.form.LanguageForm;
import net.devstudy.resume.form.SignUpForm;
import net.devstudy.resume.form.SkillForm;
import net.devstudy.resume.service.FindProfileDataService;
import net.devstudy.resume.service.FindProfileService;
import net.devstudy.resume.service.FormService;
import net.devstudy.resume.service.StaticDataService;
import net.devstudy.resume.util.FormUtil;
import net.devstudy.resume.util.SecurityUtil;

@Service
public class FormServiceImpl implements FormService {

	@Autowired
	private FindProfileService findProfileService;
	
	@Autowired
	private FindProfileDataService findProfileDataService;
	
	@Autowired
	private StaticDataService staticDataService;
	
	@Value("${profile.hobbies.max}")
	private int profileHobbiesMax;
	
	@Override
	public Object produceForm(String formName) {
		long currentProfileId = SecurityUtil.getCurrentProfileId();
		switch (formName) {
		case "general":
			return findProfileService.findById(currentProfileId);
		case "contact":
			return findProfileDataService.findContact(currentProfileId);
		case "skill":
			return new SkillForm(findProfileDataService.findListSkill(currentProfileId));
		case "experience":
			return new ExperienceForm(findProfileDataService.findListExperience(currentProfileId));
		case "certificate-edit":
			return new CertificateForm(findProfileDataService.findListCertificate(currentProfileId));
		case "certificate-add":
			return new Certificate();
		case "course":
			return new CourseForm(findProfileDataService.findListCourse(currentProfileId));
		case "education":
			return new EducationForm(findProfileDataService.findListEducation(currentProfileId));
		case "language":
			return new LanguageForm(findProfileDataService.findListLanguage(currentProfileId));
		case "hobby":
			List<Hobby> currentHobbies = findProfileDataService.findListHobby(currentProfileId);
			List<Hobby> items = FormUtil.setCheckedItems(staticDataService.getListHobby(), currentHobbies);
			return new HobbyForm(items, profileHobbiesMax);
		case "additional":
			return findProfileService.findById(currentProfileId);
		case "password":
			return new ChangePasswordForm();
		case "sign-up":
			return new SignUpForm();
		default:
			throw new IllegalArgumentException("Can't produce form for argument " + formName);
		}
	}
}