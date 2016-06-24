package net.devstudy.resume.service;

import javax.annotation.Nonnull;

import net.devstudy.resume.model.NotificationMessage;

public interface NotificationTemplateService {
	
	@Nonnull NotificationMessage createNotificationMessage(@Nonnull String templateName, @Nonnull Object model);
}