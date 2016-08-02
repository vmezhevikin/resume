package net.devstudy.resume.service;

import java.util.List;

import javax.annotation.Nonnull;

import net.devstudy.resume.entity.Hobby;
import net.devstudy.resume.entity.StaticHobbyData;
import net.devstudy.resume.entity.StaticSkillData;

public interface StaticDataService {
	
	@Nonnull List<StaticHobbyData> getListHobbyData();
	
	@Nonnull List<Hobby> getListHobby();

	@Nonnull List<StaticSkillData> getListSkillData();
}