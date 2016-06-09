package net.devstudy.resume.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restfb.types.User;

import net.devstudy.resume.entity.Profile;
import net.devstudy.resume.service.EditProfileService;
import net.devstudy.resume.service.FindProfileService;
import net.devstudy.resume.service.SocialService;

@Service
public class FacebookService implements SocialService<User>
{
	@Autowired
	private FindProfileService findProfileService;
	
	@Autowired
	private EditProfileService editProfileService;
	
	@Override
	public Profile loginViaSocailNetwork(User model)
	{
		if (StringUtils.isNotBlank(model.getEmail()))
			return findProfileService.findByEmail(model.getEmail());
		else
			return null;
	}

	@Override
	public Profile signupViaSocailNetwork(User model)
	{
		return editProfileService.createNewProfileViaFacebook(model);
	}
}
