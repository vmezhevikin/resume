package net.devstudy.resume.repository.storage;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import net.devstudy.resume.entity.Language;

public interface LanguageRepository extends PagingAndSortingRepository<Language, Long> {
	
	List<Language> findByProfileId(long idProfile);
}
