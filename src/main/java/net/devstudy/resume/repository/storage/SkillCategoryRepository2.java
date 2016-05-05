package net.devstudy.resume.repository.storage;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.RepositoryDefinition;

import net.devstudy.resume.entity.SkillCategory;

public interface SkillCategoryRepository2 extends PagingAndSortingRepository<SkillCategory, Long>
{
	List<SkillCategory> findAll(Sort sort);
}