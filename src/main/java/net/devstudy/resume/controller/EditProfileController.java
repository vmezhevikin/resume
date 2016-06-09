package net.devstudy.resume.controller;

import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.devstudy.resume.Constants;
import net.devstudy.resume.entity.Certificate;
import net.devstudy.resume.entity.Contact;
import net.devstudy.resume.entity.Course;
import net.devstudy.resume.entity.Education;
import net.devstudy.resume.entity.Experience;
import net.devstudy.resume.entity.Language;
import net.devstudy.resume.entity.Profile;
import net.devstudy.resume.entity.Skill;
import net.devstudy.resume.form.CertificateForm;
import net.devstudy.resume.form.ChangePasswordForm;
import net.devstudy.resume.form.CourseForm;
import net.devstudy.resume.form.EducationForm;
import net.devstudy.resume.form.ExperienceForm;
import net.devstudy.resume.form.HobbyForm;
import net.devstudy.resume.form.LanguageForm;
import net.devstudy.resume.form.SkillForm;
import net.devstudy.resume.model.CurrentProfile;
import net.devstudy.resume.service.EditProfileService;
import net.devstudy.resume.service.FindProfileService;
import net.devstudy.resume.util.SecurityUtil;

@Controller
public class EditProfileController
{
	@Autowired
	private FindProfileService findProfileService;

	@Autowired
	private EditProfileService editProfileService;
	
	@Value("${profile.hobbies.max}")
	private int profileHobbiesMax;
	
	@Value("${practic.years.ago}")
	private int practicYearsAgo;
			
	@Value("${course.years.ago}")
	private int courseYearsAgo;
			
	@Value("${education.years.ago}")
	private int educationYearsAgo;

	@RequestMapping(value = "/my-profile", method = RequestMethod.GET)
	public String getMyProfile(@AuthenticationPrincipal CurrentProfile currentProfile)
	{
		return "redirect:/" + currentProfile.getUsername();
	}

	@RequestMapping(value = "/edit/general", method = RequestMethod.GET)
	public String getEditGeneral(Model model)
	{
		model.addAttribute("profile", findProfileService.findById(SecurityUtil.getCurrentProfileId()));
		addCurrentSection(model, "General");
		return "edit/general";
	}

	@RequestMapping(value = "/edit/general", method = RequestMethod.POST)
	public String postEditGeneral(@Valid @ModelAttribute("profile") Profile form, BindingResult bindingResult, Model model)
	{
		if (bindingResult.hasErrors())
		{
			model.addAttribute("section", "General");
			if (form.getPhoto() == null && form.getFile().isEmpty())
				model.addAttribute("emptyPhoto", true);
			else
				model.addAttribute("emptyPhoto", false);
			return "edit/general";
		}
		if (form.getPhoto() == null && form.getFile().isEmpty())
		{
			model.addAttribute("emptyPhoto", true);
			addCurrentSection(model, "General");
			return "edit/general";
		}
		editProfileService.updateGeneralInfo(SecurityUtil.getCurrentProfileId(), form);
		return "redirect:/edit/general";
	}

	@RequestMapping(value = "/edit/contact", method = RequestMethod.GET)
	public String getEditContact(Model model)
	{
		model.addAttribute("contactForm", editProfileService.contact(SecurityUtil.getCurrentProfileId()));
		addCurrentSection(model, "Contact");
		return "edit/contact";
	}

	@RequestMapping(value = "/edit/contact", method = RequestMethod.POST)
	public String postEditContact(@Valid @ModelAttribute("contactForm") Contact form, BindingResult bindingResult, Model model)
	{
		if (bindingResult.hasErrors())
		{
			addCurrentSection(model, "Contact");
			return "edit/contact";
		}
		editProfileService.updateContact(SecurityUtil.getCurrentProfileId(), form);
		return "redirect:/edit/contact";
	}

	@RequestMapping(value = "/edit/skill", method = RequestMethod.GET)
	public String getEditSkill(Model model)
	{
		model.addAttribute("skillForm", new SkillForm(editProfileService.listSkill(SecurityUtil.getCurrentProfileId())));
		model.addAttribute("skillCategories", editProfileService.listSkillCategory());
		addCurrentSection(model, "Skill");
		return "edit/skill";
	}

	@RequestMapping(value = "/edit/skill", method = RequestMethod.POST)
	public String postEditSkill(@Valid @ModelAttribute("skillForm") SkillForm form, BindingResult bindingResult, Model model)
	{
		if (bindingResult.hasErrors())
		{
			model.addAttribute("skillCategories", editProfileService.listSkillCategory());
			addCurrentSection(model, "Skill");
			return "edit/skill";
		}
		editProfileService.updateSkill(SecurityUtil.getCurrentProfileId(), form);
		return "redirect:/edit/skill";
	}

	@RequestMapping(value = "/add/skill", method = RequestMethod.GET)
	public String getAddSkill(Model model)
	{
		model.addAttribute("skillForm", new Skill());
		model.addAttribute("skillCategories", editProfileService.listSkillCategory());
		addCurrentSection(model, "Skill");
		return "add/skill";
	}

	@RequestMapping(value = "/add/skill", method = RequestMethod.POST)
	public String postAddSkill(@Valid @ModelAttribute("skillForm") Skill form, BindingResult bindingResult, Model model)
	{
		if (bindingResult.hasErrors())
		{
			model.addAttribute("skillCategories", editProfileService.listSkillCategory());
			addCurrentSection(model, "Skill");
			return "add/skill";
		}
		editProfileService.addSkill(SecurityUtil.getCurrentProfileId(), form);
		return "redirect:/edit/skill";
	}

	@RequestMapping(value = "/edit/experience", method = RequestMethod.GET)
	public String getEditExperience(Model model)
	{
		model.addAttribute("experienceForm", new ExperienceForm(editProfileService.listExperience(SecurityUtil.getCurrentProfileId())));
		addMinMaxYears(model, practicYearsAgo);
		addMonthNames(model);
		addCurrentSection(model, "Experience");
		return "edit/experience";
	}

	@RequestMapping(value = "/edit/experience", method = RequestMethod.POST)
	public String postEditExperience(@Valid @ModelAttribute("experienceForm") ExperienceForm form, BindingResult bindingResult, Model model)
	{
		if (bindingResult.hasErrors())
		{
			addMinMaxYears(model, practicYearsAgo);
			addMonthNames(model);
			addCurrentSection(model, "Experience");
			return "edit/experience";
		}
		editProfileService.updateExperience(SecurityUtil.getCurrentProfileId(), form);
		return "redirect:/edit/experience";
	}

	@RequestMapping(value = "/add/experience", method = RequestMethod.GET)
	public String getAddExperience(Model model)
	{
		model.addAttribute("experienceForm", new Experience());
		addMinMaxYears(model, practicYearsAgo);
		addMonthNames(model);
		addCurrentSection(model, "Experience");
		return "add/experience";
	}

	@RequestMapping(value = "/add/experience", method = RequestMethod.POST)
	public String postAddExperience(@Valid @ModelAttribute("experienceForm") Experience form, BindingResult bindingResult, Model model)
	{
		if (bindingResult.hasErrors())
		{
			addMinMaxYears(model, practicYearsAgo);
			addMonthNames(model);
			addCurrentSection(model, "Experience");
			return "add/experience";
		}
		editProfileService.addExperience(SecurityUtil.getCurrentProfileId(), form);
		return "redirect:/edit/experience";
	}

	@RequestMapping(value = "/edit/certificate", method = RequestMethod.GET)
	public String getEditCertificate(Model model)
	{
		model.addAttribute("certificateForm", new CertificateForm(editProfileService.listCertificate(SecurityUtil.getCurrentProfileId())));
		addCurrentSection(model, "Certificate");
		return "edit/certificate";
	}

	@RequestMapping(value = "/edit/certificate", method = RequestMethod.POST)
	public String postEditCertificate(@ModelAttribute("certificateForm") CertificateForm form, BindingResult bindingResult, Model model)
	{
		if (bindingResult.hasErrors())
		{
			addCurrentSection(model, "Certificate");
			return "edit/certificate";
		}
		editProfileService.updateCertificate(SecurityUtil.getCurrentProfileId(), form);
		return "redirect:/edit/certificate";
	}

	@RequestMapping(value = "/add/certificate", method = RequestMethod.GET)
	public String getAddCertificate(Model model)
	{
		model.addAttribute("certificateForm", new Certificate());
		addCurrentSection(model, "Certificate");
		return "add/certificate";
	}

	@RequestMapping(value = "/add/certificate", method = RequestMethod.POST)
	public String postAddCertificate(@Valid @ModelAttribute("certificateForm") Certificate form, BindingResult bindingResult, Model model)
	{
		if (bindingResult.hasErrors())
		{
			addCurrentSection(model, "Certificate");
			return "add/certificate";
		}
		editProfileService.addCertificate(SecurityUtil.getCurrentProfileId(), form);
		return "redirect:/edit/certificate";
	}

	@RequestMapping(value = "/edit/course", method = RequestMethod.GET)
	public String getEditCourse(Model model)
	{
		model.addAttribute("courseForm", new CourseForm(editProfileService.listCourse(SecurityUtil.getCurrentProfileId())));
		addMinMaxYears(model, courseYearsAgo);
		addMonthNames(model);
		addCurrentSection(model, "Course");
		return "edit/course";
	}

	@RequestMapping(value = "/edit/course", method = RequestMethod.POST)
	public String postEditCourse(@Valid @ModelAttribute("courseForm") CourseForm form, BindingResult bindingResult, Model model)
	{
		if (bindingResult.hasErrors())
		{
			addMinMaxYears(model, courseYearsAgo);
			addMonthNames(model);
			addCurrentSection(model, "Course");
			return "edit/course";
		}
		editProfileService.updateCourse(SecurityUtil.getCurrentProfileId(), form);
		return "redirect:/edit/course";
	}

	@RequestMapping(value = "/add/course", method = RequestMethod.GET)
	public String getAddCourse(Model model)
	{
		model.addAttribute("courseForm", new Course());
		addMinMaxYears(model, courseYearsAgo);
		addMonthNames(model);
		addCurrentSection(model, "Course");
		return "add/course";
	}

	@RequestMapping(value = "/add/course", method = RequestMethod.POST)
	public String postAddCourse(@Valid @ModelAttribute("courseForm") Course form, BindingResult bindingResult, Model model)
	{
		if (bindingResult.hasErrors())
		{
			addMinMaxYears(model, courseYearsAgo);
			addMonthNames(model);
			addCurrentSection(model, "Course");
			return "add/course";
		}
		editProfileService.addCourse(SecurityUtil.getCurrentProfileId(), form);
		return "redirect:/edit/course";
	}

	@RequestMapping(value = "/edit/education", method = RequestMethod.GET)
	public String getEditEducation(Model model)
	{
		model.addAttribute("educationForm", new EducationForm(editProfileService.listEducation(SecurityUtil.getCurrentProfileId())));
		addMinMaxYears(model, educationYearsAgo);
		addCurrentSection(model, "Education");
		return "edit/education";
	}

	@RequestMapping(value = "/edit/education", method = RequestMethod.POST)
	public String postEditEducation(@Valid @ModelAttribute("educationForm") EducationForm form, BindingResult bindingResult, Model model)
	{
		if (bindingResult.hasErrors())
		{
			addMinMaxYears(model, educationYearsAgo);
			addCurrentSection(model, "Education");
			return "edit/education";
		}
		editProfileService.updateEducation(SecurityUtil.getCurrentProfileId(), form);
		return "redirect:/edit/education";
	}

	@RequestMapping(value = "/add/education", method = RequestMethod.GET)
	public String getAddEducation(Model model)
	{
		model.addAttribute("educationForm", new Education());
		addMinMaxYears(model, educationYearsAgo);
		addCurrentSection(model, "Education");
		return "add/education";
	}

	@RequestMapping(value = "/add/education", method = RequestMethod.POST)
	public String postAddEducation(@Valid @ModelAttribute("educationForm") Education form, BindingResult bindingResult, Model model)
	{
		if (bindingResult.hasErrors())
		{
			addMinMaxYears(model, educationYearsAgo);
			addCurrentSection(model, "Education");
			return "add/education";
		}
		editProfileService.addEducation(SecurityUtil.getCurrentProfileId(), form);
		return "redirect:/edit/education";
	}

	@RequestMapping(value = "/edit/language", method = RequestMethod.GET)
	public String getEditLanguage(Model model)
	{
		model.addAttribute("languageForm", new LanguageForm(editProfileService.listLanguage(SecurityUtil.getCurrentProfileId())));
		addCurrentSection(model, "Language");
		return "edit/language";
	}

	@RequestMapping(value = "/edit/language", method = RequestMethod.POST)
	public String postEditLanguage(@Valid @ModelAttribute("languageForm") LanguageForm form, BindingResult bindingResult, Model model)
	{
		if (bindingResult.hasErrors())
		{
			addCurrentSection(model, "Language");
			return "edit/language";
		}
		editProfileService.updateLanguage(SecurityUtil.getCurrentProfileId(), form);
		return "redirect:/edit/language";
	}

	@RequestMapping(value = "/add/language", method = RequestMethod.GET)
	public String getAddLanguage(Model model)
	{
		model.addAttribute("languageForm", new Language());
		addCurrentSection(model, "Language");
		return "add/language";
	}

	@RequestMapping(value = "/add/language", method = RequestMethod.POST)
	public String postAddLanguage(@Valid @ModelAttribute("languageForm") Language form, BindingResult bindingResult, Model model)
	{
		if (bindingResult.hasErrors())
		{
			addCurrentSection(model, "Language");
			return "add/language";
		}
		editProfileService.addLanguage(SecurityUtil.getCurrentProfileId(), form);
		return "redirect:/edit/language";
	}

	@RequestMapping(value = "/edit/hobby", method = RequestMethod.GET)
	public String getEditHobby(Model model)
	{
		model.addAttribute("hobbyForm", new HobbyForm(editProfileService.listHobby(SecurityUtil.getCurrentProfileId()), profileHobbiesMax));
		model.addAttribute("hobbies", editProfileService.listHobbyName());
		addCurrentSection(model, "Hobby");
		return "edit/hobby";
	}

	@RequestMapping(value = "/edit/hobby", method = RequestMethod.POST)
	public String postEditHobby(@Valid @ModelAttribute("hobbyForm") HobbyForm form, BindingResult bindingResult, Model model)
	{
		if (bindingResult.hasErrors())
		{
			model.addAttribute("hobbies", editProfileService.listHobbyName());
			addCurrentSection(model, "Hobby");
			return "edit/hobby";
		}
		editProfileService.updateHobby(SecurityUtil.getCurrentProfileId(), form);
		return "redirect:/edit/hobby";
	}

	@RequestMapping(value = "/edit/additional", method = RequestMethod.GET)
	public String getEditInfo(Model model)
	{
		model.addAttribute("profile", findProfileService.findById(SecurityUtil.getCurrentProfileId()));
		addCurrentSection(model, "Additional");
		return "edit/additional";
	}

	@RequestMapping(value = "/edit/additional", method = RequestMethod.POST)
	public String postEditInfo(@Valid @ModelAttribute("profile") Profile form, BindingResult bindingResult, Model model)
	{
		if (bindingResult.hasErrors())
		{
			addCurrentSection(model, "Additional");
			return "edit/additional";
		}
		editProfileService.updateAdditionalInfo(SecurityUtil.getCurrentProfileId(), form);
		return "redirect:/edit/additional";
	}

	@RequestMapping(value = "/edit/password", method = RequestMethod.GET)
	public String getEditPassword(Model model)
	{
		model.addAttribute("profile", findProfileService.findById(SecurityUtil.getCurrentProfileId()));
		model.addAttribute("changePasswordForm", new ChangePasswordForm());
		return "edit/password";
	}

	@RequestMapping(value = "/edit/password", method = RequestMethod.POST)
	public String postEditPassword(@Valid @ModelAttribute("changePasswordForm") ChangePasswordForm form, BindingResult bindingResult, Model model)
	{
		if (bindingResult.hasErrors())
			return "edit/password";
		
		Profile profile = findProfileService.findById(SecurityUtil.getCurrentProfileId());
		editProfileService.updatePassword(profile.getId(), form);
		return "redirect:/edit/password-change";
	}

	@RequestMapping(value = "/edit/password-change", method = RequestMethod.GET)
	public String getEditPasswordChange()
	{
		return "password-change-success";
	}
	
	private void addMinMaxYears(Model model, int diff)
	{
		LocalDate today = new LocalDate();
		model.addAttribute("maxYear", today.getYear());
		model.addAttribute("minYear", today.getYear() - diff);
	}
	
	private void addCurrentSection(Model model, String section)
	{
		model.addAttribute("section", section);
	}
	
	private void addMonthNames(Model model)
	{
		model.addAttribute("monthName", Constants.MONTH_NAMES);
	}
}