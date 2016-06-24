package net.devstudy.resume.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.devstudy.resume.entity.Profile;
import net.devstudy.resume.model.CurrentProfile;
import net.devstudy.resume.repository.search.ProfileSearchRepository;
import net.devstudy.resume.repository.storage.ProfileRepository;
import net.devstudy.resume.service.FindProfileService;

@Service
public class FindProfileServiceImpl implements FindProfileService, UserDetailsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FindProfileServiceImpl.class);

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private ProfileSearchRepository profileSearchRepository;

	@Override
	public Profile findByUid(String uid) {
		return profileRepository.findByUid(uid);
	}

	@Override
	public Profile findById(long id) {
		return profileRepository.findById(id);
	}

	@Override
	public Profile findByEmail(String email) {
		return profileRepository.findByEmail(email);
	}

	@Override
	public Page<Profile> findAll(Pageable pageable) {
		return profileRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public Iterable<Profile> findAllForIndexing() {
		Iterable<Profile> allProfiles = profileRepository.findAll();
		for (Profile profile : allProfiles) {
			profile.getCertificate().size();
			profile.getCourse().size();
			profile.getExperience().size();
			profile.getLanguage().size();
			profile.getSkill().size();
		}
		return allProfiles;
	}

	@Override
	public Page<Profile> findBySearchQuery(String query, Pageable pageable) {
		return profileSearchRepository.findByAllSubstantialFields(query, pageable);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Profile profile = findByUniqueId(username);
		if (profile != null) {
			return new CurrentProfile(profile);
		}
		else {
			LOGGER.error("Profile not found by " + username);
			throw new UsernameNotFoundException("Profile not found by " + username);
		}
	}

	@Override
	public Profile findByUniqueId(String anyUniqueId) {
		return profileRepository.findByUidOrEmailOrPhone(anyUniqueId, anyUniqueId, anyUniqueId);
	}

	@Override
	public Profile findByToken(String token) {
		return profileRepository.findByProfileRestoreToken(token);
	}

	@Override
	public List<Profile> findNotCompletedProfilesCreatedBefore(Timestamp date) {
		return profileRepository.findByActiveFalseAndCreatedBefore(date);
	}
}
