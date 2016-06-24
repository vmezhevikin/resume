package net.devstudy.resume.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.devstudy.resume.entity.Certificate;
import net.devstudy.resume.entity.Contact;
import net.devstudy.resume.entity.Course;
import net.devstudy.resume.entity.Education;
import net.devstudy.resume.entity.Experience;
import net.devstudy.resume.entity.Hobby;
import net.devstudy.resume.entity.Language;
import net.devstudy.resume.entity.Skill;
import net.devstudy.resume.repository.storage.CertificateRepository;
import net.devstudy.resume.repository.storage.CourseRepository;
import net.devstudy.resume.repository.storage.EducationRepository;
import net.devstudy.resume.repository.storage.ExperienceRepository;
import net.devstudy.resume.repository.storage.HobbyRepository;
import net.devstudy.resume.repository.storage.LanguageRepository;
import net.devstudy.resume.repository.storage.ProfileRepository;
import net.devstudy.resume.repository.storage.SkillRepository;
import net.devstudy.resume.service.FindProfileDataService;

@Service
public class FindProfileDataServiceImpl implements FindProfileDataService {
	
	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private SkillRepository skillRepository;

	@Autowired
	private LanguageRepository languageRepository;

	@Autowired
	private ExperienceRepository experienceRepository;

	@Autowired
	private EducationRepository educationRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private CertificateRepository certificateRepository;

	@Autowired
	private HobbyRepository hobbyRepository;

	@Override
	public List<Skill> findListSkill(long idProfile) {
		return skillRepository.findByProfileId(idProfile);
	}

	@Override
	public List<Language> findListLanguage(long idProfile) {
		return languageRepository.findByProfileId(idProfile);
	}

	@Override
	public List<Experience> findListExperience(long idProfile) {
		return experienceRepository.findByProfileId(idProfile);
	}

	@Override
	public List<Education> findListEducation(long idProfile) {
		return educationRepository.findByProfileId(idProfile);
	}

	@Override
	public List<Course> findListCourse(long idProfile) {
		return courseRepository.findByProfileId(idProfile);
	}

	@Override
	public List<Certificate> findListCertificate(long idProfile) {
		return certificateRepository.findByProfileId(idProfile);
	}

	@Override
	public Contact findContact(long idProfile) {
		return profileRepository.findById(idProfile).getContact();
	}

	@Override
	public List<Hobby> findListHobby(long idProfile) {
		return hobbyRepository.findByProfileId(idProfile);
	}

	@Override
	public List<Course> findCoursesBefore(Date date) {
		return courseRepository.findByCompletionDateBefore(date);
	}

	@Override
	public List<Education> findEducationsBefore(int year) {
		return educationRepository.findByCompletionYearLessThan(year);
	}

	@Override
	public List<Experience> findExperiencesBefore(Date date) {
		return experienceRepository.findByCompletionDateBefore(date);
	}
}