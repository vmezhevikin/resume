package net.devstudy.resume.repository.storage;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import net.devstudy.resume.entity.Hobby;

public interface HobbyRepository extends PagingAndSortingRepository<Hobby, Long> {
	
	List<Hobby> findByProfileId(long idProfile);
}
