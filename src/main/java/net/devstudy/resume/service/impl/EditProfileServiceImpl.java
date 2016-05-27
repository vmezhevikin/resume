package net.devstudy.resume.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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

import net.devstudy.resume.entity.Certificate;
import net.devstudy.resume.entity.Contact;
import net.devstudy.resume.entity.Course;
import net.devstudy.resume.entity.Education;
import net.devstudy.resume.entity.Experience;
import net.devstudy.resume.entity.Hobby;
import net.devstudy.resume.entity.HobbyName;
import net.devstudy.resume.entity.Language;
import net.devstudy.resume.entity.Profile;
import net.devstudy.resume.entity.Skill;
import net.devstudy.resume.entity.SkillCategory;
import net.devstudy.resume.exception.CantCompleteClientRequestException;
import net.devstudy.resume.form.CertificateForm;
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
import net.devstudy.resume.util.DataUtil;
import net.devstudy.resume.util.ImageUtil;

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

	@Value("${generate.uid.alphabet}")
	private String generateUidAlphabet;

	@Value("${generate.uid.suffix.length}")
	private int generateUidSuffixlength;

	@Value("${generate.uid.max.try.count}")
	private int generateUidMaxTryCount;
	
	@Value("${webapp.folder}")
	private String dirWebapp;
	
	@Value("${avatar.folder}")
	private String dirAvatar;

	@Value("${certificate.folder}")
	private String dirCertificate;

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
		registerCreateIndexProfileIfTrancationSuccess(profile);
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

	private void registerCreateIndexProfileIfTrancationSuccess(final Profile profile)
	{
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter()
		{
			@Override
			public void afterCommit()
			{
				LOGGER.info("New profile created: {}", profile.getUid());
				profile.getCertificate().clear();
				profile.getCourse().clear();
				profile.getEducation().clear();
				profile.getExperience().clear();
				profile.getHobby().clear();
				profile.getLanguage().clear();
				profile.getSkill().clear();
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
			registerUpdateIndexProfileSkillIfTransactionSuccess(idProfile, listFromForm);
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
		registerUpdateIndexProfileSkillIfTransactionSuccess(idProfile, profile.getSkill());
	}

	private void registerUpdateIndexProfileSkillIfTransactionSuccess(final long idProfile, final List<Skill> updatedSkill)
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
			registerUpdateIndexProfileLanguageIfTransactionSuccess(idProfile, listFromForm);
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
		registerUpdateIndexProfileLanguageIfTransactionSuccess(idProfile, profile.getLanguage());		
	}

	private void registerUpdateIndexProfileLanguageIfTransactionSuccess(final long idProfile, final List<Language> updatedLanguage)
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
			registerUpdateIndexProfileExperienceIfTransactionSuccess(idProfile, listFromForm);
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
		registerUpdateIndexProfileExperienceIfTransactionSuccess(idProfile, profile.getExperience());		
	}

	private void registerUpdateIndexProfileExperienceIfTransactionSuccess(final long idProfile, final List<Experience> updatedExperience)
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
			registerUpdateIndexProfileCourseIfTransactionSuccess(idProfile, listFromForm);
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
		registerUpdateIndexProfileCourseIfTransactionSuccess(idProfile, profile.getCourse());
	}

	private void registerUpdateIndexProfileCourseIfTransactionSuccess(final long idProfile, final List<Course> updatedCourse)
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
					String newImage = ImageUtil.saveFile(dirWebapp, dirCertificate, certificate.getFile());
					String newImageSmall = ImageUtil.getSmallPhotoPath(newImage);
					if (newImage != null && newImageSmall != null)
					{
						ImageUtil.removeFile(dirWebapp, oldImage);
						certificate.setImg(newImage);
						certificate.setImgSmall(newImageSmall);
					}
				}

			profile.updateListProfile(listFromForm);
			profile.getCertificate().clear();
			profile.getCertificate().addAll(listFromForm);
			profileRepository.save(profile);
			registerUpdateIndexProfileCertificateIfTransactionSuccess(idProfile, listFromForm);
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
			String newImage = ImageUtil.saveFile(dirWebapp, dirCertificate, form.getFile());
			String newImageSmall = ImageUtil.getSmallPhotoPath(newImage);
			if (newImage != null && newImageSmall != null)
			{
				ImageUtil.removeFile(dirWebapp, oldImage);
				form.setImg(newImage);
				form.setImgSmall(newImageSmall);
				
				LOGGER.info("Updating profile certificates: updating");
				
				Profile profile = profileRepository.findById(idProfile);
				form.setProfile(profile);
				profile.getCertificate().add(form);
				profileRepository.save(profile);
				registerUpdateIndexProfileCertificateIfTransactionSuccess(idProfile, profile.getCertificate());
			}
		}
		else
			LOGGER.info("Updating profile certificates: no file uploaded");		
	}

	private void registerUpdateIndexProfileCertificateIfTransactionSuccess(final long idProfile, final List<Certificate> updatedCertificate)
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
				String newImage = ImageUtil.saveFile(dirWebapp, dirAvatar, form.getFile());
				String newImageSmall = ImageUtil.getSmallPhotoPath(newImage);
				if (newImage != null && newImageSmall != null)
				{
					ImageUtil.removeFile(dirWebapp, oldImage);
					ImageUtil.removeFile(dirWebapp, oldImageSmall);
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
			registerUpdateIndexProfileGeneralIfTransactionSuccess(idProfile, profile);
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

	private void registerUpdateIndexProfileGeneralIfTransactionSuccess(final long idProfile, final Profile updatedProfile)
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
			if ("".equals(form.getAdditionalInfo()))
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
			if ("".equals(form.getSkype()))
				form.setSkype(null);
			if ("".equals(form.getVkontakte()))
				form.setVkontakte(null);
			if ("".equals(form.getFacebook()))
				form.setFacebook(null);
			if ("".equals(form.getLinkedin()))
				form.setLinkedin(null);
			if ("".equals(form.getGithub()))
				form.setGithub(null);
			if ("".equals(form.getStackoverflow()))
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

			List<Hobby> listHobby = updateListFromSource(listCurrent, listFromForm);

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
	
	private List<Hobby> updateListFromSource(List<Hobby> listCurrent, List<String> listFromForm)
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
		registerRemoveIndexProfileIfTrancationSuccess(idProfile);
	}
	
	private void registerRemoveIndexProfileIfTrancationSuccess(final long idProfile)
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
	public void removeCourse(long idProfile, Course removingCourse)
	{
		LOGGER.info("Removing course {}", removingCourse.getId());
		
		Profile profile = profileRepository.findById(idProfile);
		
		profile.getCourse().remove(removingCourse);
		profileRepository.save(profile);
		registerUpdateIndexProfileCourseIfTransactionSuccess(idProfile, profile.getCourse());
	}

	@Override
	public List<Education> educationBefore(int year)
	{
		return educationRepository.findByCompletionYearLessThan(year);
	}

	@Override
	@Transactional
	public void removeEducation(long idProfile, Education removingEducation)
	{
		LOGGER.info("Removing education {}", removingEducation.getId());
		
		Profile profile = profileRepository.findById(idProfile);
		profile.getEducation().remove(removingEducation);
		profileRepository.save(profile);
		LOGGER.info("Education {} removed", removingEducation.getId());
	}

	@Override
	public List<Experience> experienceBefore(Date date)
	{
		return experienceRepository.findByCompletionDateBefore(date);
	}

	@Override
	@Transactional
	public void removeExperience(long idProfile, Experience removingExperience)
	{
		LOGGER.info("Removing experience {}", removingExperience.getId());
		
		Profile profile = profileRepository.findById(idProfile);
		profile.getExperience().remove(removingExperience);
		profileRepository.save(profile);
		registerUpdateIndexProfileExperienceIfTransactionSuccess(idProfile, profile.getExperience());
	}
}
