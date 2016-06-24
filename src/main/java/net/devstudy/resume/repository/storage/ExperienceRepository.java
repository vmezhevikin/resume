package net.devstudy.resume.repository.storage;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import net.devstudy.resume.entity.Experience;

public interface ExperienceRepository extends PagingAndSortingRepository<Experience, Long> {
	
	List<Experience> findByCompletionDateBefore(Date date);

	List<Experience> findByProfileId(long idProfile);
}
