package net.devstudy.resume.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.devstudy.resume.service.NameService;

@Controller
public class PublicDataController
{
	@Autowired
	private NameService nameService;
	
	@RequestMapping(value="/{uid}", method=RequestMethod.GET)
	public String getResume(@PathVariable("uid") String uid, Model model)
	{
		String fullName = nameService.convertName(uid);
		model.addAttribute("fullName", fullName);
		return "resume";
	}
	
	// ???
	@RequestMapping(value="/welcome", method=RequestMethod.GET)
	public String getWelcome()
	{
		return "welcome";
	}
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String getSearch()
	{
		return "search";
	}
	
	@RequestMapping(value="/sign-in", method=RequestMethod.GET)
	public String getSignIn()
	{
		return "sign-in";
	}
	
	@RequestMapping(value="/sign-up", method=RequestMethod.GET)
	public String getSignUp()
	{
		return "sign-up";
	}
	
	@RequestMapping(value="/sign-up", method=RequestMethod.POST)
	public String postSignUp()
	{
		return "sign-up";
	}
	
	@RequestMapping(value="/sign-up/success", method=RequestMethod.GET)
	public String getSignUpSuccess()
	{
		return "sign-up-success";
	}
	
	@RequestMapping(value="/error", method=RequestMethod.GET)
	public String getError()
	{
		return "error";
	}
	
	@RequestMapping(value="/sign-in-failed", method=RequestMethod.GET)
	public String getSignInFailed()
	{
		return "sign-in-failed";
	}
	
	// ???
	@RequestMapping(value="/restore", method=RequestMethod.GET)
	public String getRestore()
	{
		return "restore";
	}
	
	@RequestMapping(value="/restore/success", method=RequestMethod.GET)
	public String getRestoreSuccess()
	{
		return "restore-success";
	}
	
	@RequestMapping(value="/restore", method=RequestMethod.POST)
	public String postRestore()
	{
		return "restore";
	}

	// ???
	@RequestMapping(value="/restore/{token}", method=RequestMethod.GET)
	public String getRestoreToken(@PathVariable("token") String token, Model model)
	{
		String strToken = nameService.convertName(token);
		model.addAttribute("strToken", strToken);
		return "restore-token";
	}
}
