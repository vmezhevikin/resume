package net.devstudy.resume.component;

import javax.annotation.Nonnull;

public interface NotificationContentResolver {
	@Nonnull String resolve(@Nonnull String template, @Nonnull Object model);
}