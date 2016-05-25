package net.devstudy.resume.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RemoveProfileController
{
	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public String getRemove()
	{
		// TODO
		return "remove";
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String postRemove()
	{
		// TODO
		return "remove";
	}
}
