package net.devstudy.resume.util;

import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import net.devstudy.resume.entity.Profile;
import net.devstudy.resume.model.CurrentProfile;

public final class SecurityUtil
{
	public static CurrentProfile getCurrentProfile()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null)
			return null;

		Object principal = authentication.getPrincipal();
		if (principal instanceof CurrentProfile)
			return (CurrentProfile) principal;
		else
			return null;
	}

	public static Long getCurrentProfileId()
	{
		CurrentProfile currentProfile = getCurrentProfile();

		if (currentProfile != null)
			return currentProfile.getId();
		else
			return null;
	}

	public static String getCurrentProfileUid()
	{
		CurrentProfile currentProfile = getCurrentProfile();

		if (currentProfile != null)
			return currentProfile.getUsername();
		else
			return null;
	}
	
	public static void authentificate(Profile profile)
	{
		CurrentProfile currentProfile = new CurrentProfile(profile);
		Authentication authentication = new UsernamePasswordAuthenticationToken(currentProfile, currentProfile.getPassword(),
				currentProfile.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	public static String generateNewActionUid()
	{
		return UUID.randomUUID().toString();
	}

	public static String generateNewRestoreAccessToken()
	{
		return UUID.randomUUID().toString().replace("-", "");
	}
}
