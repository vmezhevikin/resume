package net.devstudy.resume.repository.storage;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import net.devstudy.resume.entity.Education;

public interface EducationRepository extends PagingAndSortingRepository<Education, Long>
{
	List<Education> findByCompletionYearLessThan(Integer year);
}
