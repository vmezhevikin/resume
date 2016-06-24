package net.devstudy.resume.repository.storage;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import net.devstudy.resume.entity.Course;

public interface CourseRepository extends PagingAndSortingRepository<Course, Long> {
	
	List<Course> findByCompletionDateBefore(Date date);

	List<Course> findByProfileId(long idProfile);
}
