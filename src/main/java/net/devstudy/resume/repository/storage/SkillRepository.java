package net.devstudy.resume.repository.storage;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import net.devstudy.resume.entity.Skill;

public interface SkillRepository extends PagingAndSortingRepository<Skill, Long> {
	
	List<Skill> findByProfileId(long idProfile);
}
