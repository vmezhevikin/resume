package net.devstudy.resume.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.devstudy.resume.Constants;
import net.devstudy.resume.entity.HobbyName;
import net.devstudy.resume.entity.Profile;
import net.devstudy.resume.form.SignUpForm;
import net.devstudy.resume.service.EditProfileService;
import net.devstudy.resume.service.FindProfileService;
import net.devstudy.resume.util.SecurityUtil;

@Controller
public class PublicDataController
{
	@Autowired
	private FindProfileService findProfileService;

	@Autowired
	private EditProfileService editProfileService;

	@RequestMapping(value = "/{uid}", method = RequestMethod.GET)
	public String getResume(@PathVariable("uid") String uid, Model model)
	{
		Profile profile = findProfileService.findByUid(uid);
		if (profile == null)
			return "profile-not-found";

		List<HobbyName> hobbies = editProfileService.listHobbyName();
		model.addAttribute("profile", profile);
		model.addAttribute("hobbies", hobbies);
		return "resume";
	}

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String getWelcome(Model model)
	{
		PageRequest pageable = new PageRequest(0, Constants.MAX_PROFILES_PER_PAGE, new Sort("id"));
		Page<Profile> profiles = findProfileService.findAll(pageable);
		model.addAttribute("profiles", profiles.getContent());
		model.addAttribute("page", profiles);
		return "welcome";
	}

	@RequestMapping(value = "/fragment/welcome-more", method = RequestMethod.GET)
	public String getMoreProfiles(Model model, @PageableDefault(size = Constants.MAX_PROFILES_PER_PAGE) @SortDefault(sort = "id") Pageable pageable)
	{
		Page<Profile> profiles = findProfileService.findAll(pageable);
		model.addAttribute("profiles", profiles.getContent());
		return "fragment/welcome-more";
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String getSearch(@ModelAttribute("query") String query, Model model)
	{
		PageRequest pageable = new PageRequest(0, Constants.MAX_PROFILES_PER_PAGE, new Sort("id"));
		Page<Profile> profiles = findProfileService.findBySearchQuery(query, pageable);
		model.addAttribute("profiles", profiles.getContent());
		model.addAttribute("page", profiles);

		return "search";
	}

	@RequestMapping(value = "/fragment/search-more", method = RequestMethod.GET)
	public String getMoreSearch(@ModelAttribute("query") String query, Model model,
			@PageableDefault(size = Constants.MAX_PROFILES_PER_PAGE) @SortDefault(sort = "id") Pageable pageable)
	{
		Page<Profile> profiles = findProfileService.findBySearchQuery(query, pageable);
		model.addAttribute("profiles", profiles.getContent());

		return "fragment/search-more";
	}

	@RequestMapping(value = "/sign-in", method = RequestMethod.GET)
	public String getSignIn(Model model)
	{
		String currentProfileUid = SecurityUtil.getCurrentProfileUid();
		if (currentProfileUid != null)
			return "redirect:/" + currentProfileUid;
		else
			return "sign-in";
	}

	@RequestMapping(value = "/sign-up", method = RequestMethod.GET)
	public String getSignUp(Model model)
	{
		model.addAttribute("signUpForm", new SignUpForm());
		return "sign-up";
	}

	@RequestMapping(value = "/sign-up", method = RequestMethod.POST)
	public String postSignUp(@Valid @ModelAttribute("signUpForm") SignUpForm form, BindingResult bindingResult)
	{
		if (bindingResult.hasErrors())
			return "sign-up";

		Profile profile = editProfileService.createNewProfile(form);
		SecurityUtil.authentificate(profile);
		return "redirect:/sign-up/success";
	}

	@RequestMapping(value = "/sign-up/success", method = RequestMethod.GET)
	public String getSignUpSuccess(Model model)
	{
		Profile profile = findProfileService.findById(SecurityUtil.getCurrentProfileId());
		if (profile == null)
			return "error";
		model.addAttribute("profile", profile);
		return "sign-up-success";
	}

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String getError()
	{
		return "error";
	}

	@RequestMapping(value = "/sign-in-failed", method = RequestMethod.GET)
	public String getSignInFailed(HttpSession session)
	{
		if (session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION") == null)
			return "redirect:/sign-in";
		else
			return "sign-in";
	}

	@RequestMapping(value = "/restore", method = RequestMethod.GET)
	public String getRestore()
	{
		// TODO
		return "restore";
	}

	@RequestMapping(value = "/restore/success", method = RequestMethod.GET)
	public String getRestoreSuccess()
	{
		// TODO
		return "restore-success";
	}

	@RequestMapping(value = "/restore", method = RequestMethod.POST)
	public String postRestore()
	{
		// TODO
		return "restore";
	}

	@RequestMapping(value = "/restore/{token}", method = RequestMethod.GET)
	public String getRestoreToken(@PathVariable("token") String token, Model model)
	{
		// TODO
		return "restore-token";
	}
}
