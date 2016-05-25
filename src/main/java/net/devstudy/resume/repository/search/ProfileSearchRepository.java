package net.devstudy.resume.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import net.devstudy.resume.entity.Profile;

public interface ProfileSearchRepository extends ElasticsearchRepository<Profile, Long>
{
	Page<Profile> findByObjectiveContainsOrSummaryContainsOrAdditionalInfoContainsOrLanguageNameContainsOrCertificateDescriptionContainsOrCourseDescriptionContainsOrExperienceCompanyContainsOrExperiencePositionContainsOrSkillCategoryContainsOrSkillDescriptionContains
		(String objective, String summary, String additionalInfo, String languageName, String certificateDescription, 
		String courseDescription, String experienceCompany, String experiencePosition, String skillCategory, String skillDescription, Pageable pageable);
	
	@Query("{\"bool\" : { \"should\" : [ { \"query_string\" : { \"query\" : \"?0~\", \"fields\" : [ \"summary\" ], \"analyze_wildcard\" : true }} ] }}")
	Page<Profile> findBySummary(String query, Pageable pageable);
	
	@Query("{\"bool\" : { \"should\" : [ { \"query_string\" : { \"query\" : \"?0~\", \"fields\" : [ \"summary\", \"objective\", \"additionalInfo\", \"language.name\", \"certificate.description\", \"course.description\", \"experience.company\", \"experience.position\", \"skill.category\", \"skill.description\" ], \"analyze_wildcard\" : true }} ] }}")
	Page<Profile> findByAllSubstantialFields(String query, Pageable pageable);
}