package net.devstudy.resume.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;

import com.restfb.types.User;

import net.devstudy.resume.entity.Certificate;
import net.devstudy.resume.entity.Contact;
import net.devstudy.resume.entity.Course;
import net.devstudy.resume.entity.Education;
import net.devstudy.resume.entity.Experience;
import net.devstudy.resume.entity.Hobby;
import net.devstudy.resume.entity.HobbyName;
import net.devstudy.resume.entity.Language;
import net.devstudy.resume.entity.Profile;
import net.devstudy.resume.entity.ProfileRestore;
import net.devstudy.resume.entity.Skill;
import net.devstudy.resume.entity.SkillCategory;
import net.devstudy.resume.exception.CantCompleteClientRequestException;
import net.devstudy.resume.form.ChangePasswordForm;
import net.devstudy.resume.form.SignUpForm;
import net.devstudy.resume.repository.search.ProfileSearchRepository;
import net.devstudy.resume.repository.storage.CourseRepository;
import net.devstudy.resume.repository.storage.EducationRepository;
import net.devstudy.resume.repository.storage.ExperienceRepository;
import net.devstudy.resume.repository.storage.HobbyNameRepository;
import net.devstudy.resume.repository.storage.ProfileRepository;
import net.devstudy.resume.repository.storage.SkillCategoryRepository;
import net.devstudy.resume.service.EditProfileService;
import net.devstudy.resume.service.NotificationManagerService;
import net.devstudy.resume.util.DataUtil;
import net.devstudy.resume.util.ImageUtil;
import net.devstudy.resume.util.SecurityUtil;

@Service
public class EditProfileServiceImpl implements EditProfileService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(EditProfileServiceImpl.class);

	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private ProfileSearchRepository profileSearchRepository;

	@Autowired
	private SkillCategoryRepository skillCategoryRepository;

	@Autowired
	private HobbyNameRepository hobbyNameRepository;
	
	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private EducationRepository educationRepository;

	@Autowired
	private ExperienceRepository experienceRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private NotificationManagerService notificationManagerService;

	@Value("${generate.uid.alphabet}")
	private String generateUidAlphabet;

	@Value("${generate.uid.suffix.length}")
	private int generateUidSuffixlength;

	@Value("${generate.uid.max.try.count}")
	private int generateUidMaxTryCount;
	
	@Value("${media.folder}")
	private String mediaFolder;
	
	@Value("${avatar.folder}")
	private String avatarFolder;

	@Value("${certificate.folder}")
	private String certificateFolder;
	
	@Value("${email.restorelink.address}")
	private String emailRestorelinkAddress;

	@Override
	@Transactional
	public Profile createNewProfile(SignUpForm form)
	{
		LOGGER.info("Creating new profile");
		Profile profile = new Profile();
		profile.setUid(generateProfileUid(form));
		profile.setFirstName(DataUtil.capitailizeName(form.getFirstName()));
		profile.setLastName(DataUtil.capitailizeName(form.getLastName()));
		profile.setPassword(passwordEncoder.encode(form.getPassword()));
		profile.setActive(false);
		profileRepository.save(profile);
		registerIndexAfterCreateProfile(profile);
		return profile;
	}

	private String generateProfileUid(SignUpForm form)
	{
		String baseUid = DataUtil.generateProfileUid(form);
		String uid = baseUid;
		for (int i = 0; profileRepository.countByUid(uid) > 0; i++)
		{
			uid = DataUtil.regenerateUidWithRandomSuffix(baseUid, generateUidAlphabet, generateUidSuffixlength);
			if (i >= generateUidMaxTryCount)
				throw new CantCompleteClientRequestException(
						"Can't generate unique uid for profile: " + baseUid + ": maxTryCountToGenerateUid detected");
		}
		return uid;
	}

	private void registerIndexAfterCreateProfile(final Profile profile)
	{
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter()
		{
			@SuppressWarnings("unchecked")
			@Override
			public void afterCommit()
			{
				LOGGER.info("New profile created: {}", profile.getUid());
				profile.setCertificate(Collections.EMPTY_LIST);
				profile.setCourse(Collections.EMPTY_LIST);
				profile.setEducation(Collections.EMPTY_LIST);
				profile.setExperience(Collections.EMPTY_LIST);
				profile.setHobby(Collections.EMPTY_LIST);
				profile.setLanguage(Collections.EMPTY_LIST);
				profile.setSkill(Collections.EMPTY_LIST);
				profileSearchRepository.save(profile);
				LOGGER.info("New profile index created: {}", profile.getUid());
			}
		});
	}

	@Override
	public List<HobbyName> findListHobbyName()
	{
		return hobbyNameRepository.findAll(new Sort("id"));
	}

	@Override
	public List<SkillCategory> findListSkillCategory()
	{
		return skillCategoryRepository.findAll(new Sort("id"));
	}

	@Override
	public List<Skill> findListSkill(long idProfile)
	{
		return profileRepository.findById(idProfile).getSkill();
	}

	@Override
	@Transactional
	public void updateSkill(long idProfile, List<Skill> editedList)
	{
		LOGGER.info("Updating profile skills {}", idProfile);

		Profile profile = profileRepository.findById(idProfile);
		List<Skill> currentList = profile.getSkill();

		if (CollectionUtils.isEqualCollection(editedList, currentList))
			LOGGER.info("Updating profile skills: nothing to update");
		else
		{
			LOGGER.info("Updating profile skills: profile skills have been changed");

			Iterator<Skill> iterator = editedList.iterator();
			while (iterator.hasNext())
				if (iterator.next().hasAllNullFields())
					iterator.remove();
			
			profile.updateListProfile(editedList);
			profile.getSkill().clear();
			profile.getSkill().addAll(editedList);
			profileRepository.save(profile);
			updateIndexAfterEditSkill(idProfile, editedList);
		}
	}

	@Override
	@Transactional
	public void addSkill(long idProfile, Skill newSkill)
	{
		LOGGER.info("Updating profile skills, adding: {}", idProfile);
		
		Profile profile = profileRepository.findById(idProfile);
		newSkill.setProfile(profile);
		profile.getSkill().add(newSkill);
		profileRepository.save(profile);		
		updateIndexAfterEditSkill(idProfile, profile.getSkill());
	}

	private void updateIndexAfterEditSkill(final long idProfile, final List<Skill> updatedList)
	{
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter()
		{
			@Override
			public void afterCommit()
			{
				LOGGER.info("Profile skills updated: {}", idProfile);
				Profile profile = profileSearchRepository.findOne(idProfile);
				profile.getSkill().clear();
				profile.getSkill().addAll(updatedList);
				profileSearchRepository.save(profile);
				LOGGER.info("Profile skills index updated: {}", idProfile);
			}
		});
	}

	@Override
	public List<Language> findListLanguage(long idProfile)
	{
		return profileRepository.findById(idProfile).getLanguage();
	}

	@Override
	@Transactional
	public void updateLanguage(long idProfile, List<Language> editedList)
	{
		LOGGER.info("Updating profile languages: {}", idProfile);

		Profile profile = profileRepository.findById(idProfile);
		List<Language> currentList = profile.getLanguage();

		if (CollectionUtils.isEqualCollection(editedList, currentList))
			LOGGER.info("Updating profile languages: nothing to update");
		else
		{
			LOGGER.info("Updating profile languages: profile languages have been changed");

			Iterator<Language> iterator = editedList.iterator();
			while (iterator.hasNext())
				if (iterator.next().hasAllNullFields())
					iterator.remove();

			profile.updateListProfile(editedList);
			profile.getLanguage().clear();
			profile.getLanguage().addAll(editedList);
			profileRepository.save(profile);
			updateIndexAfterEditLanguage(idProfile, editedList);
		}
	}

	@Override
	@Transactional
	public void addLanguage(long idProfile, Language newLanguage)
	{
		LOGGER.info("Updating profile languages, adding: {}", idProfile);
		
		Profile profile = profileRepository.findById(idProfile);
		newLanguage.setProfile(profile);
		profile.getLanguage().add(newLanguage);
		profileRepository.save(profile);
		updateIndexAfterEditLanguage(idProfile, profile.getLanguage());		
	}

	private void updateIndexAfterEditLanguage(final long idProfile, final List<Language> updatedList)
	{
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter()
		{
			@Override
			public void afterCommit()
			{
				LOGGER.info("Profile languages updated: {}", idProfile);
				Profile profile = profileSearchRepository.findOne(idProfile);
				profile.getLanguage().clear();
				profile.getLanguage().addAll(updatedList);
				profileSearchRepository.save(profile);
				LOGGER.info("Profile languages index updated: {}", idProfile);
			}
		});
	}

	@Override
	public List<Experience> findListExperience(long idProfile)
	{
		return profileRepository.findById(idProfile).getExperience();
	}

	@Override
	@Transactional
	public void updateExperience(long idProfile, List<Experience> editedList)
	{
		LOGGER.debug("Updating profile experience: {}", idProfile);

		Profile profile = profileRepository.findById(idProfile);
		List<Experience> currentList = profile.getExperience();

		if (CollectionUtils.isEqualCollection(editedList, currentList))
			LOGGER.debug("Updating profile experience: nothing to update");
		else
		{
			LOGGER.debug("Updating profile experience: profile experience has been changed");

			Iterator<Experience> iterator = editedList.iterator();
			while (iterator.hasNext())
				if (iterator.next().hasAllNullFields())
					iterator.remove();

			profile.updateListProfile(editedList);
			profile.getExperience().clear();
			profile.getExperience().addAll(editedList);
			profileRepository.save(profile);
			updateIndexAfterEditExperience(idProfile, editedList);
		}
	}
	
	@Override
	@Transactional
	public void addExperience(long idProfile, Experience newExperience)
	{
		LOGGER.debug("Updating profile experience, adding: {}", idProfile);
		
		Profile profile = profileRepository.findById(idProfile);
		newExperience.setProfile(profile);
		profile.getExperience().add(newExperience);
		profileRepository.save(profile);
		updateIndexAfterEditExperience(idProfile, profile.getExperience());		
	}

	private void updateIndexAfterEditExperience(final long idProfile, final List<Experience> updatedList)
	{
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter()
		{
			@Override
			public void afterCommit()
			{
				LOGGER.info("Profile experience updated: {}", idProfile);
				Profile profile = profileSearchRepository.findOne(idProfile);
				profile.getExperience().clear();
				profile.getExperience().addAll(updatedList);
				profileSearchRepository.save(profile);
				LOGGER.info("Profile experience index updated: {}", idProfile);
			}
		});
	}

	@Override
	public List<Education> findListEducation(long idProfile)
	{
		return profileRepository.findById(idProfile).getEducation();
	}

	@Override
	@Transactional
	public void updateEducation(long idProfile, List<Education> editedList)
	{
		LOGGER.info("Updating profile education");

		Profile profile = profileRepository.findById(idProfile);
		List<Education> currentList = profile.getEducation();

		if (CollectionUtils.isEqualCollection(editedList, currentList))
			LOGGER.info("Updating profile education: nothing to update");
		else
		{
			LOGGER.info("Updating profile education: profile education has been changed");

			Iterator<Education> iterator = editedList.iterator();
			while (iterator.hasNext())
				if (iterator.next().hasAllNullFields())
					iterator.remove();

			profile.updateListProfile(editedList);
			profile.getEducation().clear();
			profile.getEducation().addAll(editedList);
			profileRepository.save(profile);
			
			LOGGER.info("Profile education updated: {}", idProfile);
		}
	}

	@Override
	@Transactional
	public void addEducation(long idProfile, Education newEducation)
	{
		LOGGER.info("Updating profile education, adding: {}", idProfile);
		
		Profile profile = profileRepository.findById(idProfile);
		newEducation.setProfile(profile);
		profile.getEducation().add(newEducation);
		profileRepository.save(profile);
		
		LOGGER.info("Profile education updated: {}", idProfile);
	}

	@Override
	public List<Course> findListCourse(long idProfile)
	{
		return profileRepository.findById(idProfile).getCourse();
	}

	@Override
	@Transactional
	public void updateCourse(long idProfile, List<Course> editedList)
	{
		LOGGER.info("Updating profile courses: {}", idProfile);

		Profile profile = profileRepository.findById(idProfile);
		List<Course> currentList = profile.getCourse();

		if (CollectionUtils.isEqualCollection(editedList, currentList))
			LOGGER.info("Updating profile courses: nothing to update");
		else
		{
			LOGGER.info("Updating profile courses: profile courses have been changed");

			Iterator<Course> iterator = editedList.iterator();
			while (iterator.hasNext())
				if (iterator.next().hasAllNullFields())
					iterator.remove();

			profile.updateListProfile(editedList);
			profile.getCourse().clear();
			profile.getCourse().addAll(editedList);
			profileRepository.save(profile);
			updateIndexAfterEditCourse(idProfile, editedList);
		}
	}

	@Override
	@Transactional
	public void addCourse(long idProfile, Course newCourse)
	{
		LOGGER.info("Updating profile courses, adding: {}", idProfile);
		
		Profile profile = profileRepository.findById(idProfile);
		newCourse.setProfile(profile);
		profile.getCourse().add(newCourse);
		profileRepository.save(profile);
		updateIndexAfterEditCourse(idProfile, profile.getCourse());
	}

	private void updateIndexAfterEditCourse(final long idProfile, final List<Course> updatedList)
	{
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter()
		{
			@Override
			public void afterCommit()
			{
				LOGGER.info("Profile courses updated: {}", idProfile);
				Profile profile = profileSearchRepository.findOne(idProfile);
				profile.getCourse().clear();
				profile.getCourse().addAll(updatedList);
				profileSearchRepository.save(profile);
				LOGGER.info("Profile courses index updated: {}", idProfile);
			}
		});
	}

	@Override
	public List<Certificate> findListCertificate(long idProfile)
	{
		return profileRepository.findById(idProfile).getCertificate();
	}

	@Override
	@Transactional
	public void updateCertificate(long idProfile, List<Certificate> editedList)
	{
		LOGGER.info("Updating profile certificates: {}", idProfile);

		Profile profile = profileRepository.findById(idProfile);
		List<Certificate> currentList = profile.getCertificate();

		if (CollectionUtils.isEqualCollection(editedList, currentList))
			LOGGER.info("Updating profile certificates: nothing to update");
		else
		{
			LOGGER.info("Updating profile certificates: profile certificates have been changed, updating certificates");

			Iterator<Certificate> iterator = editedList.iterator();
			while (iterator.hasNext())
				if (iterator.next().hasAllNullFields())
					iterator.remove();
			
			for (Certificate certificate : editedList)
				if (certificate.getImg() == null && certificate.getFile() != null)
				{
					String oldImage = certificate.getImg();
					String newImage = ImageUtil.saveFile(mediaFolder, certificateFolder, certificate.getFile());
					String newImageSmall = ImageUtil.getSmallPhotoPath(newImage);
					if (newImage != null && newImageSmall != null)
					{
						ImageUtil.removeFile(mediaFolder, oldImage);
						certificate.setImg(newImage);
						certificate.setImgSmall(newImageSmall);
					}
				}

			profile.updateListProfile(editedList);
			profile.getCertificate().clear();
			profile.getCertificate().addAll(editedList);
			profileRepository.save(profile);
			updateIndexAfterEditCertificate(idProfile, editedList);
		}
	}

	@Override
	@Transactional
	public void addCertificate(long idProfile, Certificate newCertificate)
	{
		LOGGER.info("Updating profile certificates, adding: {}", idProfile);
		
		if (newCertificate.getImg() == null && newCertificate.getFile() != null)
		{
			String oldImage = newCertificate.getImg();
			String newImage = ImageUtil.saveFile(mediaFolder, certificateFolder, newCertificate.getFile());
			String newImageSmall = ImageUtil.getSmallPhotoPath(newImage);
			if (newImage != null && newImageSmall != null)
			{
				ImageUtil.removeFile(mediaFolder, oldImage);
				newCertificate.setImg(newImage);
				newCertificate.setImgSmall(newImageSmall);
				
				LOGGER.info("Updating profile certificates: updating");
				
				Profile profile = profileRepository.findById(idProfile);
				newCertificate.setProfile(profile);
				profile.getCertificate().add(newCertificate);
				profileRepository.save(profile);
				updateIndexAfterEditCertificate(idProfile, profile.getCertificate());
			}
		}
		else
			LOGGER.info("Updating profile certificates: no file uploaded");		
	}

	private void updateIndexAfterEditCertificate(final long idProfile, final List<Certificate> updatedList)
	{
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter()
		{
			@Override
			public void afterCommit()
			{
				LOGGER.info("Profile certificates updated: {}", idProfile);
				Profile profile = profileSearchRepository.findOne(idProfile);
				profile.getCertificate().clear();
				profile.getCertificate().addAll(updatedList);
				profileSearchRepository.save(profile);
				LOGGER.info("Profile certificates index updated: {}", idProfile);
			}
		});
	}

	@Override
	@Transactional
	public void updateGeneralInfo(long idProfile, Profile editedProfile)
	{
		LOGGER.info("Updating profile general: {}", idProfile);

		Profile profile = profileRepository.findById(idProfile);
		boolean profileWasActiveBeforeEdit = profile.getActive();
		if (!profileShouldBeUpdated(profile, editedProfile))
			LOGGER.info("Updating profile general: nothing to update");
		else
		{
			LOGGER.info("Updating profile general: profile has been changed");

			MultipartFile file = editedProfile.getFile();
			if (file != null)
			{
				String oldImage = profile.getPhoto();
				String oldImageSmall = profile.getPhotoSmall();
				String newImage = ImageUtil.saveFile(mediaFolder, avatarFolder, editedProfile.getFile());
				String newImageSmall = ImageUtil.getSmallPhotoPath(newImage);
				if (newImage != null && newImageSmall != null)
				{
					ImageUtil.removeFile(mediaFolder, oldImage);
					ImageUtil.removeFile(mediaFolder, oldImageSmall);
					profile.setPhoto(newImage);
					profile.setPhotoSmall(newImageSmall);
				}
			}
			profile.setBirthday(DataUtil.generateDateFromString(editedProfile.getBirthdayString()));
			profile.setCountry(editedProfile.getCountry());
			profile.setCity(editedProfile.getCity());
			profile.setEmail(editedProfile.getEmail());
			profile.setPhone(editedProfile.getPhone());
			profile.setObjective(editedProfile.getObjective());
			profile.setSummary(editedProfile.getSummary());
			profile.setActive(true);
			profileRepository.save(profile);
			
			updateIndexAfterEditGeneralInfo(idProfile, profile);
			if (!profileWasActiveBeforeEdit)
				sendProfileActivatedNotification(profile);
		}
	}

	private boolean profileShouldBeUpdated(Profile profile, Profile editedProfile)
	{
		if (editedProfile.getFile() != null)
			return true;
		if (!profile.getBirthday().equals(editedProfile.getBirthday()))
			return true;
		if (!profile.getCountry().equals(editedProfile.getCountry()))
			return true;
		if (!profile.getCity().equals(editedProfile.getCity()))
			return true;
		if (!profile.getEmail().equals(editedProfile.getEmail()))
			return true;
		if (!profile.getPhone().equals(editedProfile.getPhone()))
			return true;
		if (!profile.getObjective().equals(editedProfile.getObjective()))
			return true;
		if (!profile.getSummary().equals(editedProfile.getSummary()))
			return true;
		return false;
	}

	private void updateIndexAfterEditGeneralInfo(final long idProfile, final Profile updatedProfile)
	{
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter()
		{
			@Override
			public void afterCommit()
			{
				LOGGER.info("Profile general updated: {}", idProfile);
				Profile profile = profileSearchRepository.findOne(idProfile);
				profileSearchRepository.delete(profile);
				profileSearchRepository.save(updatedProfile);
				LOGGER.info("Profile general index updated: {}", idProfile);
			}
		});
	}

	private void sendProfileActivatedNotification(final Profile profile)
	{
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter()
		{
			@Override
			public void afterCommit()
			{
				LOGGER.info("Profile was activated {}", profile.getId());
				notificationManagerService.sendProfileActive(profile);
			}
		});
	}

	@Override
	@Transactional
	public void updateAdditionalInfo(long idProfile, Profile editedProfile)
	{
		LOGGER.info("Updating profile additional: {}", idProfile);

		Profile profile = profileRepository.findById(idProfile);
		makeEmptyAdditionalNulls(editedProfile);
		if (!profileShouldBeUpdated(profile.getAdditionalInfo(), editedProfile.getAdditionalInfo()))
			LOGGER.info("Updating profile additional: nothing to update");
		else
		{
			LOGGER.info("Updating profile additional: profile has been changed");

			profile.setAdditionalInfo(editedProfile.getAdditionalInfo());
			profileRepository.save(profile);
		}
		LOGGER.info("Profile additional updated: {}", idProfile);
	}
	
	private void makeEmptyAdditionalNulls(Profile form)
	{
		if (form != null)
		{
			if (StringUtils.isBlank(form.getAdditionalInfo()))
				form.setAdditionalInfo(null);
		}
	}

	private boolean profileShouldBeUpdated(String currentAdditionalInfo, String editedAdditionalInfo)
	{
		if (currentAdditionalInfo == null && editedAdditionalInfo == null)
			return false;
		else if (currentAdditionalInfo == null || editedAdditionalInfo == null)
			return true;
		else
			return !currentAdditionalInfo.equals(editedAdditionalInfo);
	}

	@Override
	public Contact findContact(long idProfile)
	{
		return profileRepository.findById(idProfile).getContact();
	}

	@Override
	@Transactional
	public void updateContact(long idProfile, Contact newContact)
	{
		LOGGER.info("Updating profile contacts: {}", idProfile);

		Profile profile = profileRepository.findById(idProfile);
		Contact contact = profile.getContact();
		makeEmptyFieldsNulls(newContact);
		if (!profileShouldBeUpdated(contact, newContact))
			LOGGER.info("Updating profile contacts: nothing to update");
		else
		{
			LOGGER.info("Updating profile contacts: contacts have been changed");

			profile.setContact(newContact);
			profileRepository.save(profile);
		}
		LOGGER.info("Profile contacts updated: {}", idProfile);
	}
	
	private void makeEmptyFieldsNulls(Contact form)
	{
		if (form != null)
		{
			if (StringUtils.isBlank(form.getSkype()))
				form.setSkype(null);
			if (StringUtils.isBlank(form.getVkontakte()))
				form.setVkontakte(null);
			if (StringUtils.isBlank(form.getFacebook()))
				form.setFacebook(null);
			if (StringUtils.isBlank(form.getLinkedin()))
				form.setLinkedin(null);
			if (StringUtils.isBlank(form.getGithub()))
				form.setGithub(null);
			if (StringUtils.isBlank(form.getStackoverflow()))
				form.setStackoverflow(null);
		}
	}

	private boolean profileShouldBeUpdated(Contact currentContact, Contact editedContact)
	{
		if (currentContact == null && editedContact == null)
			return false;
		else if (currentContact == null || editedContact == null)
			return true;
		else
			return !currentContact.equals(editedContact);
	}

	@Override
	public List<Hobby> findListHobby(long idProfile)
	{
		return profileRepository.findById(idProfile).getHobby();
	}

	@Override
	@Transactional
	public void updateHobby(long idProfile, List<String> editedList)
	{
		LOGGER.info("Updating profile hobbies: {}", idProfile);

		Profile profile = profileRepository.findById(idProfile);
		List<Hobby> currentList = profile.getHobby();

		if (!profileShouldBeUpdated(currentList, editedList))
			LOGGER.info("Updating profile hobbies: nothing to update");
		else
		{
			LOGGER.debug("Updating profile hobbies: profile hobbies have been changed");

			List<Hobby> listHobby = updateListOfHobbiesFromSource(currentList, editedList);

			profile.updateListProfile(listHobby);
			profile.getHobby().clear();
			profile.getHobby().addAll(listHobby);
			profileRepository.save(profile);
		}
		LOGGER.info("Profile hobbies updated: {}", idProfile);
	}

	private boolean profileShouldBeUpdated(List<Hobby> currentList, List<String> editedList)
	{
		if (currentList.size() == 0 && editedList == null)
			return false;

		if (currentList.size() != 0 && editedList == null)
			return true;

		if (currentList.size() != editedList.size())
			return true;

		for (Hobby hobby : currentList)
			if (!editedList.contains(hobby.getDescription()))
				return true;

		return false;
	}
	
	private List<Hobby> updateListOfHobbiesFromSource(List<Hobby> currentList, List<String> listFromForm)
	{
		List<Hobby> listHobby = new ArrayList<>();
		
		for (String hoobyDes : listFromForm)
		{
			boolean addedFromCurrentList = false;
			for (Hobby hobbyFromCurrent : currentList)
				if (hoobyDes.equals(hobbyFromCurrent.getDescription()))
					addedFromCurrentList = listHobby.add(hobbyFromCurrent);
			if (!addedFromCurrentList)
			{
				Hobby hoobyNew = new Hobby();
				hoobyNew.setDescription(hoobyDes);
				listHobby.add(hoobyNew);
			}
		}
		
		return listHobby;
	}

	@Override
	public List<Profile> findNotCompletedProfilesCreatedBefore(Timestamp date)
	{
		return profileRepository.findByActiveFalseAndCreatedBefore(date);
	}

	@Override
	@Transactional
	public void removeProfile(long idProfile)
	{
		LOGGER.info("Removing profile {}", idProfile);
		
		Profile profile = profileRepository.findById(idProfile);
		profileRepository.delete(profile);
		updateIndexAfterRemoveProfile(idProfile);
	}
	
	private void updateIndexAfterRemoveProfile(final long idProfile)
	{
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter()
		{
			@Override
			public void afterCommit()
			{
				LOGGER.info("Profile removed: {}", idProfile);
				Profile profile = profileSearchRepository.findOne(idProfile);
				profileSearchRepository.delete(profile);
				LOGGER.info("Profile index removed: {}", idProfile);
			}
		});
	}

	@Override
	public List<Course> findCoursesBefore(Date date)
	{
		return courseRepository.findByCompletionDateBefore(date);
	}

	@Override
	@Transactional
	public void removeCourse(long idCourse)
	{
		LOGGER.info("Removing course {}", idCourse);
		
		Course removingCourse = courseRepository.findOne(idCourse);
		Profile profile = removingCourse.getProfile();
		
		profile.getCourse().remove(removingCourse);
		profileRepository.save(profile);
		updateIndexAfterEditCourse(profile.getId(), profile.getCourse());
	}

	@Override
	public List<Education> findEducationsBefore(int year)
	{
		return educationRepository.findByCompletionYearLessThan(year);
	}

	@Override
	@Transactional
	public void removeEducation(long idEducation)
	{
		LOGGER.info("Removing education {}", idEducation);
		
		Education removingEducation = educationRepository.findOne(idEducation);
		Profile profile = removingEducation.getProfile();
		
		profile.getEducation().remove(removingEducation);
		profileRepository.save(profile);
		LOGGER.info("Education {} removed", idEducation);
	}

	@Override
	public List<Experience> findExperiencesBefore(Date date)
	{
		return experienceRepository.findByCompletionDateBefore(date);
	}

	@Override
	@Transactional
	public void removeExperience(long idExperience)
	{
		LOGGER.info("Removing experience {}", idExperience);
		
		Experience removingExperience = experienceRepository.findOne(idExperience);
		Profile profile = removingExperience.getProfile();
		
		profile.getExperience().remove(removingExperience);
		profileRepository.save(profile);
		updateIndexAfterEditExperience(profile.getId(), profile.getExperience());
	}

	@Override
	@Transactional
	public void addRestoreToken(long idProfile, String token)
	{
		LOGGER.info("Creating restore token for profile {}", idProfile);
		
		Profile profile = profileRepository.findById(idProfile);
		ProfileRestore restore = new ProfileRestore();
		restore.setId(profile.getId());
		restore.setProfile(profile);
		restore.setToken(token);
		profile.setProfileRestore(restore);
		profileRepository.save(profile);
		
		notificationManagerService.sendRestoreAccessLink(profile, emailRestorelinkAddress + token);
	}

	@Override
	@Transactional
	public void removeRestoreToken(long idProfile)
	{
		LOGGER.info("Removing restore token for profile {}", idProfile);
		
		Profile profile = profileRepository.findById(idProfile);
		profile.setProfileRestore(null);
		profileRepository.save(profile);
	}

	@Override
	@Transactional
	public void updatePassword(long idProfile, ChangePasswordForm form)
	{
		LOGGER.info("Updating password for profile {}", idProfile);
		
		Profile profile = profileRepository.findById(idProfile);
		profile.setPassword(passwordEncoder.encode(form.getPassword()));
		profileRepository.save(profile);
		
		notificationManagerService.sendPasswordChanged(profile);
	}

	@Override
	@Transactional
	public Profile createNewProfileViaFacebook(User user)
	{
		LOGGER.info("Creating new profile via Facebook");

		Profile profile = new Profile();
		profile.setUid(generateProfileUid(user));
		profile.setFirstName(DataUtil.capitailizeName(user.getFirstName()));
		profile.setLastName(DataUtil.capitailizeName(user.getLastName()));
		profile.setPassword(passwordEncoder.encode(SecurityUtil.generatePassword()));
		profile.setActive(false);
		setCountryFromUser(profile, user);
		profile.setBirthday(user.getBirthdayAsDate());
		profile.setEmail(user.getEmail());
		profile.setAdditionalInfo(user.getRelationshipStatus());
		setEducationsFromUser(profile, user);
		setExperienceFromUser(profile, user);
		profileRepository.save(profile);
		registerIndexAfterCreateProfileViaFacebook(profile);
		return profile;
	}

	private String generateProfileUid(User user)
	{
		String baseUid = DataUtil.generateProfileUid(user);
		String uid = baseUid;
		for (int i = 0; profileRepository.countByUid(uid) > 0; i++)
		{
			uid = DataUtil.regenerateUidWithRandomSuffix(baseUid, generateUidAlphabet, generateUidSuffixlength);
			if (i >= generateUidMaxTryCount)
				throw new CantCompleteClientRequestException(
						"Can't generate unique uid for profile: " + baseUid + ": maxTryCountToGenerateUid detected");
		}
		return uid;
	}

	private void setCountryFromUser(Profile profile, User user)
	{
		if (user.getHometown() != null)
		{
			String[] location = user.getHometown().getName().split(",");
			profile.setCountry(location[1].trim());
			profile.setCity(location[0].trim());
		}
	}

	private void setEducationsFromUser(Profile profile, User user)
	{
		List<Education> educationsFromFacebook = new ArrayList<>();
		for (com.restfb.types.User.Education educationFacebook : user.getEducation())
		{
			Education education = createEducationFromFacebook(educationFacebook);
			education.setProfile(profile);
			educationsFromFacebook.add(education);
		}
		if (!educationsFromFacebook.isEmpty())
			profile.setEducation(educationsFromFacebook);
	}

	private void setExperienceFromUser(Profile profile, User user)
	{
		List<Experience> worksFromFacebook = new ArrayList<>();
		for (com.restfb.types.User.Work workFacebook : user.getWork())
		{
			Experience experience = createExperienceFromFacebook(workFacebook);
			experience.setProfile(profile);
			worksFromFacebook.add(experience);
		}
		if (!worksFromFacebook.isEmpty())
			profile.setExperience(worksFromFacebook);
	}
	
	private Education createEducationFromFacebook(com.restfb.types.User.Education educationFacebook)
	{
		Education education = new Education();
		education.setDepartment("From FB");
		education.setSpeciality("From FB");		
		if (educationFacebook.getSchool() != null)
			education.setUniversity(educationFacebook.getSchool().getName());
		else
			education.setUniversity("From FB");		
		if (educationFacebook.getDegree() != null)
			education.setSpeciality(educationFacebook.getDegree().getName());
		else
			education.setSpeciality("From FB");
		if (educationFacebook.getYear() != null)
		{
			education.setStartingYear(Integer.parseInt(educationFacebook.getYear().getName()));
			education.setCompletionYear(Integer.parseInt(educationFacebook.getYear().getName()));
		}
		else
		{
			LocalDate today = new LocalDate();
			education.setStartingYear(today.getYear());
			education.setCompletionYear(today.getYear());
		}
		
		return education;
	}
	
	private Experience createExperienceFromFacebook(com.restfb.types.User.Work workFacebook)
	{
		Experience experience = new Experience();

		if (workFacebook.getEmployer() != null)
			experience.setCompany(workFacebook.getEmployer().getName());
		else
			experience.setCompany("From FB");
		if (workFacebook.getStartDate() != null)
			experience.setStartingDate(workFacebook.getStartDate());
		else
			experience.setStartingDate(new LocalDate().toDate());
		if (workFacebook.getEndDate() != null)
			experience.setCompletionDate(workFacebook.getEndDate());
		else
			experience.setCompletionDate(new LocalDate().toDate());
		if (workFacebook.getPosition() != null)
			experience.setPosition(workFacebook.getPosition().getName());
		else
			experience.setPosition("From FB");
		if (workFacebook.getDescription() != null)
			experience.setResponsibility(workFacebook.getDescription());
		else
			experience.setResponsibility("From FB");
		
		return experience;
	}
	
	private void registerIndexAfterCreateProfileViaFacebook(final Profile profile)
	{
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter()
		{
			@SuppressWarnings("unchecked")
			@Override
			public void afterCommit()
			{
				LOGGER.info("New profile via Facebook created: {}", profile.getUid());
				profile.setCertificate(Collections.EMPTY_LIST);
				profile.setCourse(Collections.EMPTY_LIST);
				if (profile.getEducation() == null)
					profile.setEducation(Collections.EMPTY_LIST);
				if (profile.getExperience() == null)
					profile.setExperience(Collections.EMPTY_LIST);
				profile.setHobby(Collections.EMPTY_LIST);
				profile.setLanguage(Collections.EMPTY_LIST);
				profile.setSkill(Collections.EMPTY_LIST);
				profileSearchRepository.save(profile);
				LOGGER.info("New profile index created: {}", profile.getUid());
			}
		});
	}
}