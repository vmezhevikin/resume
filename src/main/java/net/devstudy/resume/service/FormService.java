package net.devstudy.resume.service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface FormService {

	@Nonnull Object produceForm(@Nullable String formName);
}