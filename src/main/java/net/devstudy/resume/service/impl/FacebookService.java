package net.devstudy.resume.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.restfb.types.User;

import net.devstudy.resume.component.TranslitConverter;
import net.devstudy.resume.entity.Education;
import net.devstudy.resume.entity.Experience;
import net.devstudy.resume.entity.Profile;
import net.devstudy.resume.exception.CantCompleteClientRequestException;
import net.devstudy.resume.repository.search.ProfileSearchRepository;
import net.devstudy.resume.repository.storage.ProfileRepository;
import net.devstudy.resume.service.SocialService;
import net.devstudy.resume.util.DataUtil;
import net.devstudy.resume.util.ProfileDataUtil;
import net.devstudy.resume.util.SecurityUtil;

@Service
public class FacebookService implements SocialService<User> {
	private static final Logger LOGGER = LoggerFactory.getLogger(FacebookService.class);

	@Autowired
	private ProfileRepository profileRepository;

	/*@Autowired
	private ProfileSearchRepository profileSearchRepository;*/

	@Autowired
	private TranslitConverter translitConverter;

	@Value("${generate.uid.alphabet}")
	private String generateUidAlphabet;

	@Value("${generate.uid.suffix.length}")
	private int generateUidSuffixlength;

	@Value("${generate.uid.max.try.count}")
	private int generateUidMaxTryCount;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Profile findProfileViaSocailNetwork(User model) {
		String email = model.getEmail();
		if (StringUtils.isNotBlank(email)) {
			return profileRepository.findByEmail(email);
		}
		else {
			return null;
		}
	}

	@Override
	@Transactional
	public Profile createNewProfileViaSocailNetwork(User user) {
		LOGGER.debug("Profile: creating new profile via Facebook");
		Profile profile = new Profile();
		synchronized (this) {
			checkEmailAddressIsUnique(user.getEmail());
			ProfileDataUtil.setAllProfileCollectionsAsEmty(profile);
			setFieldsFromUser(profile, user);
			profileRepository.save(profile);
			//registerIndexAfterCreateProfileViaFacebook(profile, user);
		}
		return profile;
	}

	private void checkEmailAddressIsUnique(String email) {
		if (StringUtils.isBlank(email)) {
			throw new CantCompleteClientRequestException("Email is blank. Can't create user via Facebook");
		}
		else {
			if (profileRepository.countByEmail(email) != 0) {
				throw new CantCompleteClientRequestException("Profile with email " + email + " already exist. Can't create profile via Facebook");
			}
		}
	}
	
	private void setFieldsFromUser(Profile profile, User user) {
		setDataFromUserName(profile, user);
		profile.setPassword(passwordEncoder.encode(SecurityUtil.generatePassword()));
		profile.setActive(false);
		setCountryAndCityFromUser(profile, user);
		profile.setBirthday(user.getBirthdayAsDate());
		profile.setEmail(user.getEmail());
		profile.setAdditionalInfo(user.getRelationshipStatus());
		setEducationsFromUser(profile, user);
		setExperienceFromUser(profile, user);
	}
	
	private void setDataFromUserName(Profile profile, User user) {
		String firstName = translitConverter.translit(user.getFirstName().replace(" ", ""));
		String lastName = translitConverter.translit(user.getLastName().replace(" ", ""));
		profile.setUid(generateProfileUid(firstName, lastName));
		profile.setFirstName(DataUtil.capitailizeName(firstName));
		profile.setLastName(DataUtil.capitailizeName(lastName));
	}

	private String generateProfileUid(String firstName, String lastName) {
		String baseUid = DataUtil.generateProfileUid(firstName, lastName);
		String uid = baseUid;
		for (int i = 0; profileRepository.countByUid(uid) > 0; i++) {
			uid = DataUtil.regenerateUidWithRandomSuffix(baseUid, generateUidAlphabet, generateUidSuffixlength);
			if (i >= generateUidMaxTryCount) {
				throw new CantCompleteClientRequestException("Can't generate unique uid for profile: " + baseUid + ": maxTryCountToGenerateUid detected");
			}
		}
		return uid;
	}

	private void setCountryAndCityFromUser(Profile profile, User user) {
		if (user.getHometown() != null) {
			String[] location = user.getHometown().getName().split(",");
			profile.setCountry(location[1].trim());
			profile.setCity(location[0].trim());
		}
	}

	private void setEducationsFromUser(Profile profile, User user) {
		List<Education> educationsFromFacebook = new ArrayList<>();
		for (com.restfb.types.User.Education educationFacebook : user.getEducation()) {
			Education education = createEducationFromFacebook(educationFacebook);
			education.setProfile(profile);
			educationsFromFacebook.add(education);
		}
		if (!educationsFromFacebook.isEmpty()) {
			profile.setEducation(educationsFromFacebook);
		}
	}

	private void setExperienceFromUser(Profile profile, User user) {
		List<Experience> worksFromFacebook = new ArrayList<>();
		for (com.restfb.types.User.Work workFacebook : user.getWork()) {
			Experience experience = createExperienceFromFacebook(workFacebook);
			experience.setProfile(profile);
			worksFromFacebook.add(experience);
		}
		if (!worksFromFacebook.isEmpty()) {
			profile.setExperience(worksFromFacebook);
		}
	}

	private Education createEducationFromFacebook(com.restfb.types.User.Education educationFacebook) {
		Education education = new Education();
		education.setDepartment("Department");

		if (educationFacebook.getSchool() != null) {
			education.setUniversity(educationFacebook.getSchool().getName());
		}
		else {
			education.setUniversity("University");
		}

		if (educationFacebook.getDegree() != null) {
			education.setSpeciality(educationFacebook.getDegree().getName());
		}
		else {
			education.setSpeciality("Speciality");
		}

		if (educationFacebook.getYear() != null) {
			education.setStartingYear(Integer.parseInt(educationFacebook.getYear().getName()));
			education.setCompletionYear(Integer.parseInt(educationFacebook.getYear().getName()));
		} else {
			education.setStartingYear(DataUtil.getCurrentYear());
			education.setCompletionYear(DataUtil.getCurrentYear());
		}

		return education;
	}

	private Experience createExperienceFromFacebook(com.restfb.types.User.Work workFacebook) {
		Experience experience = new Experience();

		if (workFacebook.getEmployer() != null) {
			experience.setCompany(workFacebook.getEmployer().getName());
		}
		else {
			experience.setCompany("Company");
		}

		if (workFacebook.getStartDate() != null) {
			experience.setStartingDate(workFacebook.getStartDate());
		}
		else {
			experience.setStartingDate(new LocalDate().toDate());
		}

		if (workFacebook.getEndDate() != null) {
			experience.setCompletionDate(workFacebook.getEndDate());
		}
		else {
			experience.setCompletionDate(new LocalDate().toDate());
		}

		if (workFacebook.getPosition() != null) {
			experience.setPosition(workFacebook.getPosition().getName());
		}
		else {
			experience.setPosition("Position");
		}

		if (workFacebook.getDescription() != null) {
			experience.setResponsibility(workFacebook.getDescription());
		}
		else {
			experience.setResponsibility("Responsibilities");
		}

		return experience;
	}

	/*private void registerIndexAfterCreateProfileViaFacebook(final Profile profile, final User user) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void afterCommit() {
				LOGGER.info("Profile {}: profile has been created via Facebook", profile.getUid());
				ProfileDataUtil.setAllProfileCollectionsAsEmty(profile);
				setEducationsFromUser(profile, user);
				setExperienceFromUser(profile, user);
				profileSearchRepository.save(profile);
				LOGGER.info("Profile {}: has been index created", profile.getUid());
			}
		});
	}*/
}