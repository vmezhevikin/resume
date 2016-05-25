package net.devstudy.resume.repository.storage;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.RepositoryDefinition;

import net.devstudy.resume.entity.HobbyName;

@RepositoryDefinition(domainClass=HobbyName.class, idClass=Long.class)
public interface HobbyNameRepository
{
	List<HobbyName> findAll(Sort sort);
}