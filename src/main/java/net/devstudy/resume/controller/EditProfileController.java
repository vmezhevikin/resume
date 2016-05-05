package net.devstudy.resume.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.devstudy.resume.entity.SkillCategory;
import net.devstudy.resume.repository.storage.SkillCategoryRepository;

@Controller
public class EditProfileController
{
	@Autowired
	private SkillCategoryRepository skillCategoryRepository;
	
	// ???
	@RequestMapping(value = "/my-profile", method = RequestMethod.GET)
	public String getMyProfile()
	{
		return "my-profile";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String getEdit()
	{
		return "edit";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String postEdit()
	{
		return "edit";
	}

	@RequestMapping(value = "/edit/contacts", method = RequestMethod.GET)
	public String getEditContacts()
	{
		return "edit-contacts";
	}

	@RequestMapping(value = "/edit/contacts", method = RequestMethod.POST)
	public String postEditContacts()
	{
		return "edit-contacts";
	}

	@RequestMapping(value = "/edit/skills", method = RequestMethod.GET)
	public String getEditSkills(Model model)
	{
		List<SkillCategory> skillCategories =  skillCategoryRepository.findAll(new Sort("id"));
		model.addAttribute("skillCategories", skillCategories);
		return "edit-skills";
	}

	@RequestMapping(value = "/edit/skills", method = RequestMethod.POST)
	public String postEditSkills()
	{
		return "edit-skills";
	}

	@RequestMapping(value = "/edit/practics", method = RequestMethod.GET)
	public String getEditPractics()
	{
		return "edit-practics";
	}

	@RequestMapping(value = "/edit/practics", method = RequestMethod.POST)
	public String postEditPractics()
	{
		return "edit-practics";
	}

	@RequestMapping(value = "/edit/certificates", method = RequestMethod.GET)
	public String getEditCertificates()
	{
		return "edit-certificates";
	}

	@RequestMapping(value = "/edit/certificates", method = RequestMethod.POST)
	public String postEditCertificates()
	{
		return "edit-certificates";
	}

	@RequestMapping(value = "/edit/certificates/upload", method = RequestMethod.POST)
	public String postEditCertificatesUpload()
	{
		return "edit-certificates-upload";
	}

	@RequestMapping(value = "/edit/courses", method = RequestMethod.GET)
	public String getEditCourses()
	{
		return "edit-courses";
	}

	@RequestMapping(value = "/edit/courses", method = RequestMethod.POST)
	public String postEditCourses()
	{
		return "edit-courses";
	}

	@RequestMapping(value = "/edit/education", method = RequestMethod.GET)
	public String getEditEducation()
	{
		return "edit-education";
	}

	@RequestMapping(value = "/edit/education", method = RequestMethod.POST)
	public String postEditEducation()
	{
		return "edit-education";
	}

	@RequestMapping(value = "/edit/languages", method = RequestMethod.GET)
	public String getEditLanguages()
	{
		return "edit-languages";
	}

	@RequestMapping(value = "/edit/languages", method = RequestMethod.POST)
	public String postEditLanguages()
	{
		return "edit-languages";
	}

	@RequestMapping(value = "/edit/hobbies", method = RequestMethod.GET)
	public String getEditHobbies()
	{
		return "edit-hobbies";
	}

	@RequestMapping(value = "/edit/hobbies", method = RequestMethod.POST)
	public String postEditHobbies()
	{
		return "edit-hobbies";
	}

	@RequestMapping(value = "/edit/info", method = RequestMethod.GET)
	public String getEditInfo()
	{
		return "edit-info";
	}

	@RequestMapping(value = "/edit/info", method = RequestMethod.POST)
	public String postEditInfo()
	{
		return "edit-info";
	}

	@RequestMapping(value = "/edit/password", method = RequestMethod.GET)
	public String getEditPassword()
	{
		return "edit-password";
	}

	@RequestMapping(value = "/edit/password", method = RequestMethod.POST)
	public String postEditPassword()
	{
		return "edit-password";
	}
}
