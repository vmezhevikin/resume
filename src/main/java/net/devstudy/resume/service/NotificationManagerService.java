package net.devstudy.resume.service;

import javax.annotation.Nonnull;

import net.devstudy.resume.entity.Profile;

public interface NotificationManagerService {
	
	void sendRestoreAccessLink(@Nonnull Profile profile, @Nonnull String restoreLink);
	
	void sendPasswordChanged(@Nonnull Profile profile);
	
	void sendProfileActive(@Nonnull Profile profile);
}