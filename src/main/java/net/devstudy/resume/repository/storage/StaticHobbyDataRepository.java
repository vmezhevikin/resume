package net.devstudy.resume.repository.storage;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.RepositoryDefinition;

import net.devstudy.resume.entity.StaticHobbyData;

@RepositoryDefinition(domainClass = StaticHobbyData.class, idClass = Long.class)
public interface StaticHobbyDataRepository {
	
	List<StaticHobbyData> findAll(Sort sort);
}