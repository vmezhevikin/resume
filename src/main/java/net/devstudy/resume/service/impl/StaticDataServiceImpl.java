package net.devstudy.resume.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.devstudy.resume.entity.HobbyName;
import net.devstudy.resume.entity.SkillCategory;
import net.devstudy.resume.repository.storage.HobbyNameRepository;
import net.devstudy.resume.repository.storage.SkillCategoryRepository;
import net.devstudy.resume.service.StaticDataService;

@Service
public class StaticDataServiceImpl implements StaticDataService {
	
	@Autowired
	private SkillCategoryRepository skillCategoryRepository;

	@Autowired
	private HobbyNameRepository hobbyNameRepository;

	private List<HobbyName> hobbyName;

	private List<SkillCategory> skillCategory;

	@PostConstruct
	private void postConstruct() {
		hobbyName = hobbyNameRepository.findAll(new Sort("id"));
		skillCategory = skillCategoryRepository.findAll(new Sort("id"));
	}

	@Override
	public List<HobbyName> getListHobbyName() {
		return hobbyName;
	}

	@Override
	public List<SkillCategory> getListSkillCategory() {
		return skillCategory;
	}
}
