package net.devstudy.resume.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.devstudy.resume.service.EditProfileService;
import net.devstudy.resume.service.FindProfileService;
import net.devstudy.resume.util.SecurityUtil;

@Controller
public class RemoveProfileController {
	
	@Autowired
	private FindProfileService findProfileService;

	@Autowired
	private EditProfileService editProfileService;

	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public String getRemove(Model model) {
		model.addAttribute("profile", findProfileService.findById(SecurityUtil.getCurrentProfileId()));
		return "remove";
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String postRemove(HttpServletRequest request) {
		editProfileService.removeProfile(SecurityUtil.getCurrentProfileId());
		SecurityUtil.logoutCurrentUser(request);
		return "redirect:/welcome";
	}
}
