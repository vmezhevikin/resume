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
import net.devstudy.resume.form.CertificateForm;
import net.devstudy.resume.form.ChangePasswordForm;
import net.devstudy.resume.form.CourseForm;
import net.devstudy.resume.form.EducationForm;
import net.devstudy.resume.form.ExperienceForm;
import net.devstudy.resume.form.HobbyForm;
import net.devstudy.resume.form.LanguageForm;
import net.devstudy.resume.form.SignUpForm;
import net.devstudy.resume.form.SkillForm;
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
	
	@Value("${webapp.folder}")
	private String webappFolder;
	
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
	public List<HobbyName> listHobbyName()
	{
		return hobbyNameRepository.findAll(new Sort("id"));
	}

	@Override
	public List<SkillCategory> listSkillCategory()
	{
		return skillCategoryRepository.findAll(new Sort("id"));
	}

	@Override
	public List<Skill> listSkill(long idProfile)
	{
		return profileRepository.findById(idProfile).getSkill();
	}

	@Override
	@Transactional
	public void updateSkill(long idProfile, SkillForm skillForm)
	{
		LOGGER.info("Updating profile skills");

		Profile profile = profileRepository.findById(idProfile);
		List<Skill> listCurrent = profile.getSkill();
		List<Skill> listFromForm = skillForm.getItems();

		if (CollectionUtils.isEqualCollection(listFromForm, listCurrent))
			LOGGER.info("Updating profile skills: nothing to update");
		else
		{
			LOGGER.info("Updating profile skills: profile skills have been changed");

			Iterator<Skill> iterator = listFromForm.iterator();
			while (iterator.hasNext())
				if (iterator.next().hasAllNullFields())
					iterator.remove();
			
			profile.updateListProfile(listFromForm);
			profile.getSkill().clear();
			profile.getSkill().addAll(listFromForm);
			profileRepository.save(profile);
			updateIndexAfterEditSkill(idProfile, listFromForm);
		}
	}

	@Override
	@Transactional
	public void addSkill(long idProfile, Skill form)
	{
		LOGGER.info("Updating profile skills: adding");
		
		Profile profile = profileRepository.findById(idProfile);
		form.setProfile(profile);
		profile.getSkill().add(form);
		profileRepository.save(profile);		
		updateIndexAfterEditSkill(idProfile, profile.getSkill());
	}

	private void updateIndexAfterEditSkill(final long idProfile, final List<Skill> updatedSkill)
	{
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter()
		{
			@Override
			public void afterCommit()
			{
				LOGGER.info("Profile skills updated");
				Profile profile = profileSearchRepository.findOne(idProfile);
				profile.getSkill().clear();
				profile.getSkill().addAll(updatedSkill);
				profileSearchRepository.save(profile);
				LOGGER.info("Profile skills index updated");
			}
		});
	}

	@Override
	public List<Language> listLanguage(long idProfile)
	{
		return profileRepository.findById(idProfile).getLanguage();
	}

	@Override
	@Transactional
	public void updateLanguage(long idProfile, LanguageForm form)
	{
		LOGGER.info("Updating profile languages");

		Profile profile = profileRepository.findById(idProfile);
		List<Language> listCurrent = profile.getLanguage();
		List<Language> listFromForm = form.getItems();

		if (CollectionUtils.isEqualCollection(listFromForm, listCurrent))
			LOGGER.info("Updating profile languages: nothing to update");
		else
		{
			LOGGER.info("Updating profile languages: profile languages have been changed");

			Iterator<Language> iterator = listFromForm.iterator();
			while (iterator.hasNext())
				if (iterator.next().hasAllNullFields())
					iterator.remove();

			profile.updateListProfile(listFromForm);
			profile.getLanguage().clear();
			profile.getLanguage().addAll(listFromForm);
			profileRepository.save(profile);
			updateIndexAfterEditLanguage(idProfile, listFromForm);
		}
	}

	@Override
	@Transactional
	public void addLanguage(long idProfile, Language form)
	{
		LOGGER.info("Updating profile languages: adding");
		
		Profile profile = profileRepository.findById(idProfile);
		form.setProfile(profile);
		profile.getLanguage().add(form);
		profileRepository.save(profile);
		updateIndexAfterEditLanguage(idProfile, profile.getLanguage());		
	}

	private void updateIndexAfterEditLanguage(final long idProfile, final List<Language> updatedLanguage)
	{
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter()
		{
			@Override
			public void afterCommit()
			{
				LOGGER.info("Profile languages updated");
				Profile profile = profileSearchRepository.findOne(idProfile);
				profile.getLanguage().clear();
				profile.getLanguage().addAll(updatedLanguage);
				profileSearchRepository.save(profile);
				LOGGER.info("Profile languages index updated");
			}
		});
	}

	@Override
	public List<Experience> listExperience(long idProfile)
	{
		return profileRepository.findById(idProfile).getExperience();
	}

	@Override
	@Transactional
	public void updateExperience(long idProfile, ExperienceForm form)
	{
		LOGGER.debug("Updating profile experience");

		Profile profile = profileRepository.findById(idProfile);
		List<Experience> listCurrent = profile.getExperience();
		List<Experience> listFromForm = form.getItems();

		if (CollectionUtils.isEqualCollection(listFromForm, listCurrent))
			LOGGER.debug("Updating profile experience: nothing to update");
		else
		{
			LOGGER.debug("Updating profile experience: profile experience has been changed");

			Iterator<Experience> iterator = listFromForm.iterator();
			while (iterator.hasNext())
				if (iterator.next().hasAllNullFields())
					iterator.remove();

			profile.updateListProfile(listFromForm);
			profile.getExperience().clear();
			profile.getExperience().addAll(listFromForm);
			profileRepository.save(profile);
			updateIndexAfterEditExperience(idProfile, listFromForm);
		}
	}
	
	@Override
	@Transactional
	public void addExperience(long idProfile, Experience form)
	{
		LOGGER.debug("Updating profile experience: adding");
		
		Profile profile = profileRepository.findById(idProfile);
		form.setProfile(profile);
		profile.getExperience().add(form);
		profileRepository.save(profile);
		updateIndexAfterEditExperience(idProfile, profile.getExperience());		
	}

	private void updateIndexAfterEditExperience(final long idProfile, final List<Experience> updatedExperience)
	{
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter()
		{
			@Override
			public void afterCommit()
			{
				LOGGER.info("Profile experience updated");
				Profile profile = profileSearchRepository.findOne(idProfile);
				profile.getExperience().clear();
				profile.getExperience().addAll(updatedExperience);
				profileSearchRepository.save(profile);
				LOGGER.info("Profile experience index updated");
			}
		});
	}

	@Override
	public List<Education> listEducation(long idProfile)
	{
		return profileRepository.findById(idProfile).getEducation();
	}

	@Override
	@Transactional
	public void updateEducation(long idProfile, EducationForm form)
	{
		LOGGER.info("Updating profile education");

		Profile profile = profileRepository.findById(idProfile);
		List<Education> listCurrent = profile.getEducation();
		List<Education> listFromForm = form.getItems();

		if (CollectionUtils.isEqualCollection(listFromForm, listCurrent))
			LOGGER.info("Updating profile education: nothing to update");
		else
		{
			LOGGER.info("Updating profile education: profile education has been changed");

			Iterator<Education> iterator = listFromForm.iterator();
			while (iterator.hasNext())
				if (iterator.next().hasAllNullFields())
					iterator.remove();

			profile.updateListProfile(listFromForm);
			profile.getEducation().clear();
			profile.getEducation().addAll(listFromForm);
			profileRepository.save(profile);
			
			LOGGER.info("Profile education updated");
		}
	}

	@Override
	@Transactional
	public void addEducation(long idProfile, Education form)
	{
		LOGGER.info("Updating profile education: adding");
		
		Profile profile = profileRepository.findById(idProfile);
		form.setProfile(profile);
		profile.getEducation().add(form);
		profileRepository.save(profile);
		
		LOGGER.info("Profile education updated");
	}

	@Override
	public List<Course> listCourse(long idProfile)
	{
		return profileRepository.findById(idProfile).getCourse();
	}

	@Override
	@Transactional
	public void updateCourse(long idProfile, CourseForm form)
	{
		LOGGER.info("Updating profile courses");

		Profile profile = profileRepository.findById(idProfile);
		List<Course> listCurrent = profile.getCourse();
		List<Course> listFromForm = form.getItems();

		if (CollectionUtils.isEqualCollection(listFromForm, listCurrent))
			LOGGER.info("Updating profile courses: nothing to update");
		else
		{
			LOGGER.info("Updating profile courses: profile courses have been changed");

			Iterator<Course> iterator = listFromForm.iterator();
			while (iterator.hasNext())
				if (iterator.next().hasAllNullFields())
					iterator.remove();

			profile.updateListProfile(listFromForm);
			profile.getCourse().clear();
			profile.getCourse().addAll(listFromForm);
			profileRepository.save(profile);
			updateIndexAfterEditCourse(idProfile, listFromForm);
		}
	}

	@Override
	@Transactional
	public void addCourse(long idProfile, Course form)
	{
		LOGGER.info("Updating profile courses: adding");
		
		Profile profile = profileRepository.findById(idProfile);
		form.setProfile(profile);
		profile.getCourse().add(form);
		profileRepository.save(profile);
		updateIndexAfterEditCourse(idProfile, profile.getCourse());
	}

	private void updateIndexAfterEditCourse(final long idProfile, final List<Course> updatedCourse)
	{
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter()
		{
			@Override
			public void afterCommit()
			{
				LOGGER.info("Profile courses updated");
				Profile profile = profileSearchRepository.findOne(idProfile);
				profile.getCourse().clear();
				profile.getCourse().addAll(updatedCourse);
				profileSearchRepository.save(profile);
				LOGGER.info("Profile courses index updated");
			}
		});
	}

	@Override
	public List<Certificate> listCertificate(long idProfile)
	{
		return profileRepository.findById(idProfile).getCertificate();
	}

	@Override
	@Transactional
	public void updateCertificate(long idProfile, CertificateForm form)
	{
		LOGGER.info("Updating profile certificates: updating");

		Profile profile = profileRepository.findById(idProfile);
		List<Certificate> listCurrent = profile.getCertificate();
		List<Certificate> listFromForm = form.getItems();

		if (CollectionUtils.isEqualCollection(listFromForm, listCurrent))
			LOGGER.info("Updating profile certificates: nothing to update");
		else
		{
			LOGGER.info("Updating profile certificates: profile certificates have been changed, updating certificates");

			Iterator<Certificate> iterator = listFromForm.iterator();
			while (iterator.hasNext())
				if (iterator.next().hasAllNullFields())
					iterator.remove();
			
			for (Certificate certificate : listFromForm)
				if (certificate.getImg() == null && certificate.getFile() != null)
				{
					String oldImage = certificate.getImg();
					String newImage = ImageUtil.saveFile(webappFolder, certificateFolder, certificate.getFile());
					String newImageSmall = ImageUtil.getSmallPhotoPath(newImage);
					if (newImage != null && newImageSmall != null)
					{
						ImageUtil.removeFile(webappFolder, oldImage);
						certificate.setImg(newImage);
						certificate.setImgSmall(newImageSmall);
					}
				}

			profile.updateListProfile(listFromForm);
			profile.getCertificate().clear();
			profile.getCertificate().addAll(listFromForm);
			profileRepository.save(profile);
			updateIndexAfterEditCertificate(idProfile, listFromForm);
		}
	}

	@Override
	@Transactional
	public void addCertificate(long idProfile, Certificate form)
	{
		LOGGER.info("Updating profile certificates: adding");
		
		if (form.getImg() == null && form.getFile() != null)
		{
			String oldImage = form.getImg();
			String newImage = ImageUtil.saveFile(webappFolder, certificateFolder, form.getFile());
			String newImageSmall = ImageUtil.getSmallPhotoPath(newImage);
			if (newImage != null && newImageSmall != null)
			{
				ImageUtil.removeFile(webappFolder, oldImage);
				form.setImg(newImage);
				form.setImgSmall(newImageSmall);
				
				LOGGER.info("Updating profile certificates: updating");
				
				Profile profile = profileRepository.findById(idProfile);
				form.setProfile(profile);
				profile.getCertificate().add(form);
				profileRepository.save(profile);
				updateIndexAfterEditCertificate(idProfile, profile.getCertificate());
			}
		}
		else
			LOGGER.info("Updating profile certificates: no file uploaded");		
	}

	private void updateIndexAfterEditCertificate(final long idProfile, final List<Certificate> updatedCertificate)
	{
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter()
		{
			@Override
			public void afterCommit()
			{
				LOGGER.info("Profile certificates updated");
				Profile profile = profileSearchRepository.findOne(idProfile);
				profile.getCertificate().clear();
				profile.getCertificate().addAll(updatedCertificate);
				profileSearchRepository.save(profile);
				LOGGER.info("Profile certificates index updated");
			}
		});
	}

	@Override
	@Transactional
	public void updateGeneralInfo(long idProfile, Profile form)
	{
		LOGGER.info("Updating profile general");

		Profile profile = profileRepository.findById(idProfile);
		if (!hasDifference(profile, form))
			LOGGER.info("Updating profile general: nothing to update");
		else
		{
			LOGGER.info("Updating profile general: profile has been changed");

			MultipartFile file = form.getFile();
			if (file != null)
			{
				String oldImage = profile.getPhoto();
				String oldImageSmall = profile.getPhotoSmall();
				String newImage = ImageUtil.saveFile(webappFolder, avatarFolder, form.getFile());
				String newImageSmall = ImageUtil.getSmallPhotoPath(newImage);
				if (newImage != null && newImageSmall != null)
				{
					ImageUtil.removeFile(webappFolder, oldImage);
					ImageUtil.removeFile(webappFolder, oldImageSmall);
					profile.setPhoto(newImage);
					profile.setPhotoSmall(newImageSmall);
				}
			}
			profile.setBirthday(DataUtil.generateDateFromString(form.getBirthdayString()));
			profile.setCountry(form.getCountry());
			profile.setCity(form.getCity());
			profile.setEmail(form.getEmail());
			profile.setPhone(form.getPhone());
			profile.setObjective(form.getObjective());
			profile.setSummary(form.getSummary());
			profile.setActive(true);
			profileRepository.save(profile);
			updateIndexAfterEditGeneralInfo(idProfile, profile);
		}
	}

	private boolean hasDifference(Profile profile, Profile form)
	{
		if (form.getFile() != null)
			return true;
		if (!profile.getBirthday().equals(form.getBirthday()))
			return true;
		if (!profile.getCountry().equals(form.getCountry()))
			return true;
		if (!profile.getCity().equals(form.getCity()))
			return true;
		if (!profile.getEmail().equals(form.getEmail()))
			return true;
		if (!profile.getPhone().equals(form.getPhone()))
			return true;
		if (!profile.getObjective().equals(form.getObjective()))
			return true;
		if (!profile.getSummary().equals(form.getSummary()))
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
				LOGGER.info("Profile general updated");
				Profile profile = profileSearchRepository.findOne(idProfile);
				profileSearchRepository.delete(profile);
				profileSearchRepository.save(updatedProfile);
				LOGGER.info("Profile general index updated");
			}
		});
	}

	@Override
	@Transactional
	public void updateAdditionalInfo(long idProfile, Profile form)
	{
		LOGGER.info("Updating profile additional");

		Profile profile = profileRepository.findById(idProfile);
		makeEmptyAdditionalNulls(form);
		if (!hasDifference(profile.getAdditionalInfo(), form.getAdditionalInfo()))
			LOGGER.info("Updating profile additional: nothing to update");
		else
		{
			LOGGER.info("Updating profile additional: profile has been changed");

			profile.setAdditionalInfo(form.getAdditionalInfo());
			profileRepository.save(profile);
		}
		LOGGER.info("Profile additional updated");
	}
	
	private void makeEmptyAdditionalNulls(Profile form)
	{
		if (form != null)
		{
			if (StringUtils.isBlank(form.getAdditionalInfo()))
				form.setAdditionalInfo(null);
		}
	}

	private boolean hasDifference(String current, String fromForm)
	{
		if (current == null && fromForm == null)
			return false;
		else if (current == null || fromForm == null)
			return true;
		else
			return !current.equals(fromForm);
	}

	@Override
	public Contact contact(long idProfile)
	{
		return profileRepository.findById(idProfile).getContact();
	}

	@Override
	@Transactional
	public void updateContact(long idProfile, Contact form)
	{
		LOGGER.info("Updating profile contacts");

		Profile profile = profileRepository.findById(idProfile);
		Contact contact = profile.getContact();
		makeEmptyFieldsNulls(form);
		if (!hasDifference(contact, form))
			LOGGER.info("Updating profile contacts: nothing to update");
		else
		{
			LOGGER.info("Updating profile contacts: contacts have been changed");

			profile.setContact(form);
			profileRepository.save(profile);
		}
		LOGGER.info("Profile contacts updated");
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

	private boolean hasDifference(Contact current, Contact fromForm)
	{
		if (current == null && fromForm == null)
			return false;
		else if (current == null || fromForm == null)
			return true;
		else
			return !current.equals(fromForm);
	}

	@Override
	public List<Hobby> listHobby(long idProfile)
	{
		return profileRepository.findById(idProfile).getHobby();
	}

	@Override
	@Transactional
	public void updateHobby(long idProfile, HobbyForm form)
	{
		LOGGER.info("Updating profile hobbies");

		Profile profile = profileRepository.findById(idProfile);
		List<Hobby> listCurrent = profile.getHobby();
		List<String> listFromForm = form.getCheckedItems();

		if (!hasDifference(listCurrent, listFromForm))
			LOGGER.info("Updating profile hobbies: nothing to update");
		else
		{
			LOGGER.debug("Updating profile hobbies: profile hobbies have been changed");

			List<Hobby> listHobby = updateListOfHobbiesFromSource(listCurrent, listFromForm);

			profile.updateListProfile(listHobby);
			profile.getHobby().clear();
			profile.getHobby().addAll(listHobby);
			profileRepository.save(profile);
		}
		LOGGER.info("Profile hobbies updated");
	}

	private boolean hasDifference(List<Hobby> listCurrent, List<String> listFromForm)
	{
		if (listCurrent.size() == 0 && listFromForm == null)
			return false;

		if (listCurrent.size() != 0 && listFromForm == null)
			return true;

		if (listCurrent.size() != listFromForm.size())
			return true;

		for (Hobby hobby : listCurrent)
			if (!listFromForm.contains(hobby.getDescription()))
				return true;

		return false;
	}
	
	private List<Hobby> updateListOfHobbiesFromSource(List<Hobby> listCurrent, List<String> listFromForm)
	{
		List<Hobby> listHobby = new ArrayList<>();
		
		for (String hoobyDes : listFromForm)
		{
			boolean addedFromCurrentList = false;
			for (Hobby hobbyFromCurrent : listCurrent)
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
	public List<Profile> notCompletedProfilesCreatedBefore(Timestamp date)
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
	public List<Course> coursesBefore(Date date)
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
	public List<Education> educationsBefore(int year)
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
	public List<Experience> experiencesBefore(Date date)
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
		if (user.getHometown() != null)
		{
			String[] location = user.getHometown().getName().split(",");
			profile.setCountry(location[1].trim());
			profile.setCity(location[0].trim());
		}
		profile.setBirthday(user.getBirthdayAsDate());
		profile.setEmail(user.getEmail());
		profile.setAdditionalInfo(user.getRelationshipStatus());
		
		List<Education> educationsFromFacebook = new ArrayList<>();
		for (com.restfb.types.User.Education educationFacebook : user.getEducation())
		{
			Education education = createEducationFromFacebook(educationFacebook);
			education.setProfile(profile);
			educationsFromFacebook.add(education);
		}
		if (!educationsFromFacebook.isEmpty())
			profile.setEducation(educationsFromFacebook);
		
		List<Experience> worksFromFacebook = new ArrayList<>();
		for (com.restfb.types.User.Work workFacebook : user.getWork())
		{
			Experience experience = createExperienceFromFacebook(workFacebook);
			experience.setProfile(profile);
			worksFromFacebook.add(experience);
		}
		if (!worksFromFacebook.isEmpty())
			profile.setExperience(worksFromFacebook);
	
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