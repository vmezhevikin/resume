package net.devstudy.resume.service;

import java.util.List;

import javax.annotation.Nonnull;

import net.devstudy.resume.entity.HobbyName;
import net.devstudy.resume.entity.SkillCategory;

public interface StaticDataService {
	
	@Nonnull List<HobbyName> getListHobbyName();

	@Nonnull List<SkillCategory> getListSkillCategory();
}