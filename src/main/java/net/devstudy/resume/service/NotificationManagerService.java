package net.devstudy.resume.service;

import net.devstudy.resume.entity.Profile;

public interface NotificationManagerService
{
	void sendRestoreAccessLink(Profile profile, String restoreLink);
	
	void sendPasswordChanged(Profile profile);
}