package net.devstudy.resume.service;

import net.devstudy.resume.entity.Profile;

public interface SocialService<T>
{
	Profile loginViaSocailNetwork(T model);
	
	Profile signupViaSocailNetwork(T model);
}
