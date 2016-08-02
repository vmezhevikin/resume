package net.devstudy.resume.controller;

import java.util.List;

import javax.validation.Valid;

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
import net.devstudy.resume.entity.Hobby;
import net.devstudy.resume.entity.Profile;
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
import net.devstudy.resume.service.FormService;
import net.devstudy.resume.service.StaticDataService;
import net.devstudy.resume.util.FormUtil;
import net.devstudy.resume.util.DataUtil;
import net.devstudy.resume.util.SecurityUtil;

@Controller
public class EditProfileController {

	@Autowired
	private FindProfileService findProfileService;

	@Autowired
	private EditProfileService editProfileService;

	@Autowired
	private StaticDataService staticDataService;
	
	@Autowired
	private FormService formService;

	@Value("${practic.years.ago}")
	private int practicYearsAgo;

	@Value("${course.years.ago}")
	private int courseYearsAgo;

	@Value("${education.years.ago}")
	private int educationYearsAgo;

	@RequestMapping(value = "/my-profile", method = RequestMethod.GET)
	public String getMyProfile(@AuthenticationPrincipal CurrentProfile currentProfile) {
		return "redirect:/" + currentProfile.getUsername();
	}

	@RequestMapping(value = "/edit/general", method = RequestMethod.GET)
	public String getEditGeneral(Model model) {
		addAttributeForm(model, "general");
		model.addAttribute("profile", findProfileService.findById(SecurityUtil.getCurrentProfileId()));
		return "edit/general";
	}

	@RequestMapping(value = "/edit/general", method = RequestMethod.POST)
	public String postEditGeneral(@Valid @ModelAttribute("form") Profile form, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "edit/general";
		}
		Profile preparedForm = FormUtil.setBlankItemsAsNulls(form);
		editProfileService.updateGeneralInfo(SecurityUtil.getCurrentProfileId(), preparedForm);
		if (!form.getFile().isEmpty()) {
			return "redirect:/edit/general";
		} else {
			addAttributeMessage(model, "General information: updated!");
			return "edit/general";
		}
	}

	@RequestMapping(value = "/edit/contact", method = RequestMethod.GET)
	public String getEditContact(Model model) {
		addAttributeForm(model, "contact");
		return "edit/contact";
	}

	@RequestMapping(value = "/edit/contact", method = RequestMethod.POST)
	public String postEditContact(@Valid @ModelAttribute("form") Contact form, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "edit/contact";
		}
		Contact preparedForm = FormUtil.setBlankItemsAsNulls(form);
		editProfileService.updateContact(SecurityUtil.getCurrentProfileId(), preparedForm);
		addAttributeMessage(model, "Contact information: updated!");
		return "edit/contact";
	}

	@RequestMapping(value = "/edit/skill", method = RequestMethod.GET)
	public String getEditSkill(Model model) {
		addAttributeForm(model, "skill");
		addAttributeSkillCategories(model);
		return "edit/skill";
	}

	@RequestMapping(value = "/edit/skill", method = RequestMethod.POST)
	public String postEditSkill(@Valid @ModelAttribute("form") SkillForm form, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			addAttributeSkillCategories(model);
			return "edit/skill";
		}
		editProfileService.updateSkill(SecurityUtil.getCurrentProfileId(), form.getItems());
		addAttributeSkillCategories(model);
		addAttributeMessage(model, "Skills: updated!");
		return "edit/skill";
	}

	@RequestMapping(value = "/edit/experience", method = RequestMethod.GET)
	public String getEditExperience(Model model) {
		addAttributeForm(model, "experience");
		addAttributesMinMaxYearForExperience(model);
		addAttributeMonthName(model);
		return "edit/experience";
	}

	@RequestMapping(value = "/edit/experience", method = RequestMethod.POST)
	public String postEditExperience(@Valid @ModelAttribute("form") ExperienceForm form,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			addAttributesMinMaxYearForExperience(model);
			addAttributeMonthName(model);
			return "edit/experience";
		}
		editProfileService.updateExperience(SecurityUtil.getCurrentProfileId(), form.getItems());
		addAttributesMinMaxYearForExperience(model);
		addAttributeMonthName(model);
		addAttributeMessage(model, "Experience: updated!");
		return "edit/experience";
	}

	@RequestMapping(value = "/edit/certificate", method = RequestMethod.GET)
	public String getEditCertificate(Model model) {
		addAttributeForm(model, "certificate-edit");
		return "edit/certificate";
	}

	@RequestMapping(value = "/edit/certificate", method = RequestMethod.POST)
	public String postEditCertificate(@ModelAttribute("form") CertificateForm form, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "edit/certificate";
		}
		editProfileService.updateCertificate(SecurityUtil.getCurrentProfileId(), form.getItems());
		addAttributeMessage(model, "Certificates: updated!");
		return "edit/certificate";
	}

	@RequestMapping(value = "/add/certificate", method = RequestMethod.GET)
	public String getAddCertificate(Model model) {
		addAttributeForm(model, "certificate-add");
		return "add/certificate";
	}

	@RequestMapping(value = "/add/certificate", method = RequestMethod.POST)
	public String postAddCertificate(@Valid @ModelAttribute("form") Certificate form,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "add/certificate";
		}
		editProfileService.addCertificate(SecurityUtil.getCurrentProfileId(), form);
		addAttributeForm(model, "certificate-add");
		addAttributeMessage(model, "Certificate: added new record!");
		return "add/certificate";
	}

	@RequestMapping(value = "/edit/course", method = RequestMethod.GET)
	public String getEditCourse(Model model) {
		addAttributeForm(model, "course");
		addAttributesMinMaxYearForCourse(model);
		addAttributeMonthName(model);
		return "edit/course";
	}

	@RequestMapping(value = "/edit/course", method = RequestMethod.POST)
	public String postEditCourse(@Valid @ModelAttribute("form") CourseForm form, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			addAttributesMinMaxYearForCourse(model);
			addAttributeMonthName(model);
			return "edit/course";
		}
		editProfileService.updateCourse(SecurityUtil.getCurrentProfileId(), form.getItems());
		addAttributesMinMaxYearForCourse(model);
		addAttributeMonthName(model);
		addAttributeMessage(model, "Courses: updated!");
		return "edit/course";
	}

	@RequestMapping(value = "/edit/education", method = RequestMethod.GET)
	public String getEditEducation(Model model) {
		addAttributeForm(model, "education");
		addAttributesMinMaxYearForEducation(model);
		return "edit/education";
	}

	@RequestMapping(value = "/edit/education", method = RequestMethod.POST)
	public String postEditEducation(@Valid @ModelAttribute("form") EducationForm form, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			addAttributesMinMaxYearForEducation(model);
			return "edit/education";
		}
		editProfileService.updateEducation(SecurityUtil.getCurrentProfileId(), form.getItems());
		addAttributesMinMaxYearForEducation(model);
		addAttributeMessage(model, "Education: updated!");
		return "edit/education";
	}

	@RequestMapping(value = "/edit/language", method = RequestMethod.GET)
	public String getEditLanguage(Model model) {
		addAttributeForm(model, "language");
		return "edit/language";
	}

	@RequestMapping(value = "/edit/language", method = RequestMethod.POST)
	public String postEditLanguage(@Valid @ModelAttribute("form") LanguageForm form, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "edit/language";
		}
		editProfileService.updateLanguage(SecurityUtil.getCurrentProfileId(), form.getItems());
		addAttributeMessage(model, "Languages: updated!");
		return "edit/language";
	}

	@RequestMapping(value = "/edit/hobby", method = RequestMethod.GET)
	public String getEditHobby(Model model) {
		addAttributeForm(model, "hobby");
		return "edit/hobby";
	}

	@RequestMapping(value = "/edit/hobby", method = RequestMethod.POST)
	public String postEditHobby(@Valid @ModelAttribute("form") HobbyForm form, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "edit/hobby";
		}
		List<Hobby> preparedList = FormUtil.getCheckedItems(form.getItems());
		editProfileService.updateHobby(SecurityUtil.getCurrentProfileId(), preparedList);
		addAttributeMessage(model, "Hobbies: updated!");
		return "edit/hobby";
	}

	@RequestMapping(value = "/edit/additional", method = RequestMethod.GET)
	public String getEditInfo(Model model) {
		addAttributeForm(model, "additional");
		return "edit/additional";
	}

	@RequestMapping(value = "/edit/additional", method = RequestMethod.POST)
	public String postEditInfo(@Valid @ModelAttribute("form") Profile form, BindingResult bindingResult, Model model) {
		if (bindingResult.getFieldErrorCount() != 0) {
			return "edit/additional";
		}
		editProfileService.updateAdditionalInfo(SecurityUtil.getCurrentProfileId(), form);
		addAttributeMessage(model, "Additional information: updated!");
		return "edit/additional";
	}

	@RequestMapping(value = "/edit/password", method = RequestMethod.GET)
	public String getEditPassword(Model model) {
		addAttributeForm(model, "password");
		return "edit/password";
	}

	@RequestMapping(value = "/edit/password", method = RequestMethod.POST)
	public String postEditPassword(@Valid @ModelAttribute("form") ChangePasswordForm form, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors())
			return "edit/password";

		Profile profile = findProfileService.findById(SecurityUtil.getCurrentProfileId());
		editProfileService.updatePassword(profile.getId(), form);
		return "redirect:/edit/password-change";
	}

	@RequestMapping(value = "/edit/password-change", method = RequestMethod.GET)
	public String getEditPasswordChange() {
		return "password-change-success";
	}

	private void addAttributesMinMaxYearForEducation(Model model) {
		model.addAttribute("maxYear", DataUtil.getCurrentYear());
		model.addAttribute("minYear", DataUtil.getCurrentYear() - educationYearsAgo);
	}

	private void addAttributesMinMaxYearForExperience(Model model) {
		model.addAttribute("maxYear", DataUtil.getCurrentYear());
		model.addAttribute("minYear", DataUtil.getCurrentYear() - practicYearsAgo);
	}

	private void addAttributesMinMaxYearForCourse(Model model) {
		model.addAttribute("maxYear", DataUtil.getCurrentYear());
		model.addAttribute("minYear", DataUtil.getCurrentYear() - courseYearsAgo);
	}

	private void addAttributeMonthName(Model model) {
		model.addAttribute("monthName", Constants.MONTH_NAMES);
	}

	private void addAttributeMessage(Model model, String message) {
		model.addAttribute("message", message);
	}

	private void addAttributeSkillCategories(Model model) {
		model.addAttribute("skillCategories", staticDataService.getListSkillData());
	}

	private void addAttributeForm(Model model, String formName) {
		model.addAttribute("form", formService.produceForm(formName));
	}
}