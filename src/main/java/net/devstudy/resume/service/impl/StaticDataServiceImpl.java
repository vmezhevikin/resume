package net.devstudy.resume.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.devstudy.resume.entity.Hobby;
import net.devstudy.resume.entity.StaticHobbyData;
import net.devstudy.resume.entity.StaticSkillData;
import net.devstudy.resume.repository.storage.StaticHobbyDataRepository;
import net.devstudy.resume.repository.storage.StaticSkillDataRepository;
import net.devstudy.resume.service.StaticDataService;

@Service
public class StaticDataServiceImpl implements StaticDataService {
	
	@Autowired
	private StaticSkillDataRepository staticSkillDataRepository;

	@Autowired
	private StaticHobbyDataRepository staticHobbyDataRepository;

	private List<StaticHobbyData> staticHobbyData;
	
	private List<StaticSkillData> staticSkillData;

	@PostConstruct
	private void postConstruct() {
		staticHobbyData = staticHobbyDataRepository.findAll(new Sort("id"));
		staticSkillData = staticSkillDataRepository.findAll(new Sort("id"));
	}

	@Override
	public List<StaticHobbyData> getListHobbyData() {
		return staticHobbyData;
	}

	@Override
	public List<StaticSkillData> getListSkillData() {
		return staticSkillData;
	}

	@Override
	public List<Hobby> getListHobby() {
		List<Hobby> hobbies = new LinkedList<>();
		for (StaticHobbyData hobbyData : staticHobbyData) {
			Hobby hobby = new Hobby();
			hobby.setDescription(hobbyData.getName());
			hobby.setIcon(hobbyData.getIcon());
			hobbies.add(hobby);
		}
		return hobbies;
	}
}
