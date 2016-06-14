package net.devstudy.resume.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.devstudy.resume.annotation.constraints.DateFormat;
import net.devstudy.resume.annotation.constraints.EnglishLanguage;
import net.devstudy.resume.annotation.constraints.Phone;

@Entity
@Table(name = "profile")
@Document(indexName = "profile")
public class Profile extends AbstractEntity<Long>
{
	private static final long serialVersionUID = 4419584168346691423L;

	@Id
	@Column(unique = true, nullable = false)
	@SequenceGenerator(name = "PROFILE_ID_GENERATOR", sequenceName = "profile_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROFILE_ID_GENERATOR")
	private Long id;

	@Column(nullable = false, length = 100)
	private String uid;

	@Column(nullable = false, length = 255)
	@JsonIgnore
	private String password;

	@Column(nullable = false)
	private Boolean active;

	@Column(name = "first_name", nullable = false, length = 50)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 50)
	private String lastName;

	@Transient
	@JsonIgnore
	private String fullName;

	@Column(length = 50)
	@Size(min = 1, message = "Don't leave it empty")
	@EnglishLanguage
	@SafeHtml(whitelistType = WhiteListType.NONE, message = "Html is not allowed")
	private String country;

	@Column(length = 50)
	@Size(min = 1, message = "Don't leave it empty")
	@EnglishLanguage
	@SafeHtml(whitelistType = WhiteListType.NONE, message = "Html is not allowed")
	private String city;

	@Column
	private Date birthday;

	@Transient
	@Size(min = 1, message = "Don't leave it empty")
	@DateFormat
	@JsonIgnore
	private String birthdayString;

	@Column(length = 100)
	@Size(min = 1, message = "Don't leave it empty")
	@EnglishLanguage
	@Email(message = "Not an email address")
	@JsonIgnore
	@SafeHtml(whitelistType = WhiteListType.NONE, message = "Html is not allowed")
	private String email;

	@Column(length = 20)
	@Size(min = 1, message = "Don't leave it empty")
	@Phone
	@JsonIgnore
	@SafeHtml(whitelistType = WhiteListType.NONE, message = "Html is not allowed")
	private String phone;

	@Column(name = "additional_info", length = 2147483647)
	@EnglishLanguage
	@SafeHtml(whitelistType = WhiteListType.NONE, message = "Html is not allowed")
	private String additionalInfo;

	@Column(length = 2147483647)
	@EnglishLanguage
	@SafeHtml(whitelistType = WhiteListType.NONE, message = "Html is not allowed")
	private String objective;

	@Column(length = 2147483647)
	@EnglishLanguage
	@SafeHtml(whitelistType = WhiteListType.NONE, message = "Html is not allowed")
	private String summary;

	@Column(length = 255)
	@JsonIgnore
	private String photo;

	@Column(name = "photo_small", length = 255)
	private String photoSmall;

	@Column(insertable = false)
	@JsonIgnore
	private Timestamp created;

	@OneToMany(targetEntity = Certificate.class, cascade = { CascadeType.MERGE, CascadeType.PERSIST }, mappedBy = "profile", orphanRemoval = true)
	@OrderBy("description ASC")
	private List<Certificate> certificate;

	@OneToMany(targetEntity = Course.class, cascade = { CascadeType.MERGE, CascadeType.PERSIST }, mappedBy = "profile", orphanRemoval = true)
	@OrderBy("completionDate DESC")
	private List<Course> course;

	@OneToMany(targetEntity = Education.class, cascade = { CascadeType.MERGE, CascadeType.PERSIST }, mappedBy = "profile", orphanRemoval = true)
	@OrderBy("completionYear DESC, startingYear DESC")
	@JsonIgnore
	private List<Education> education;

	@OneToMany(targetEntity = Experience.class, cascade = { CascadeType.MERGE, CascadeType.PERSIST }, mappedBy = "profile", orphanRemoval = true)
	@OrderBy("completionDate DESC, startingDate DESC")
	private List<Experience> experience;

	@OneToMany(targetEntity = Hobby.class, cascade = { CascadeType.MERGE, CascadeType.PERSIST }, mappedBy = "profile", orphanRemoval = true)
	@JsonIgnore
	private List<Hobby> hobby;

	@OneToMany(targetEntity = Language.class, cascade = { CascadeType.MERGE, CascadeType.PERSIST }, mappedBy = "profile", orphanRemoval = true)
	private List<Language> language;

	@OneToMany(targetEntity = Skill.class, cascade = { CascadeType.MERGE, CascadeType.PERSIST }, mappedBy = "profile", orphanRemoval = true)
	@OrderBy("id")
	private List<Skill> skill;

	@OneToOne(targetEntity = ProfileRestore.class, cascade = { CascadeType.MERGE, CascadeType.PERSIST }, mappedBy = "profile", orphanRemoval = true)
	@JsonIgnore
	private ProfileRestore profileRestore;

	@Embedded
	@JsonIgnore
	private Contact contact;

	@Transient
	@JsonIgnore
	private MultipartFile file;

	@Override
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getUid()
	{
		return uid;
	}

	public void setUid(String uid)
	{
		this.uid = uid;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Boolean getActive()
	{
		return active;
	}

	public void setActive(Boolean active)
	{
		this.active = active;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	@Transient
	public String getFullName()
	{
		if (fullName == null)
			fullName = firstName + " " + lastName;
		return fullName;
	}

	@Transient
	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public Date getBirthday()
	{
		return birthday;
	}

	public void setBirthday(Date birthday)
	{
		this.birthday = birthday;
	}

	@Transient
	public void setBirthdayString(String birthdayString)
	{
		this.birthdayString = birthdayString;
	}

	@Transient
	public String getBirthdayString()
	{
		if (birthdayString == null && birthday != null)
		{
			LocalDate birthdate = new LocalDate(birthday);
			birthdayString = birthdate.toString("yyyy-MM-dd");
		}
		return birthdayString;
	}

	@Transient
	public int getAge()
	{
		LocalDate birthdate = new LocalDate(birthday);
		LocalDate now = new LocalDate();
		Years age = Years.yearsBetween(birthdate, now);
		return age.getYears();
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getAdditionalInfo()
	{
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo)
	{
		this.additionalInfo = additionalInfo;
	}

	public String getObjective()
	{
		return objective;
	}

	public void setObjective(String objective)
	{
		this.objective = objective;
	}

	public String getSummary()
	{
		return summary;
	}

	public void setSummary(String summary)
	{
		this.summary = summary;
	}

	public String getPhoto()
	{
		return photo;
	}

	public void setPhoto(String photo)
	{
		this.photo = photo;
	}

	public String getPhotoSmall()
	{
		return photoSmall;
	}

	public void setPhotoSmall(String photoSmall)
	{
		this.photoSmall = photoSmall;
	}

	public Timestamp getCreated()
	{
		return created;
	}

	public void setCreated(Timestamp created)
	{
		this.created = created;
	}

	public List<Certificate> getCertificate()
	{
		return certificate;
	}

	public void setCertificate(List<Certificate> certificate)
	{
		this.certificate = certificate;
	}

	public List<Course> getCourse()
	{
		return course;
	}

	public void setCourse(List<Course> course)
	{
		this.course = course;
	}

	public List<Education> getEducation()
	{
		return education;
	}

	public void setEducation(List<Education> education)
	{
		this.education = education;
	}

	public List<Experience> getExperience()
	{
		return experience;
	}

	public void setExperience(List<Experience> experience)
	{
		this.experience = experience;
	}

	public List<Hobby> getHobby()
	{
		return hobby;
	}

	public void setHobby(List<Hobby> hobby)
	{
		this.hobby = hobby;
	}

	@Transient
	public boolean hasHobby(String description)
	{
		if (hobby == null || hobby.size() == 0)
			return false;

		for (Hobby h : hobby)
			if (h.getDescription().equals(description))
				return true;

		return false;
	}

	public List<Language> getLanguage()
	{
		return language;
	}

	public void setLanguage(List<Language> language)
	{
		this.language = language;
	}

	public List<Skill> getSkill()
	{
		return skill;
	}

	public void setSkill(List<Skill> skill)
	{
		this.skill = skill;
	}

	public ProfileRestore getProfileRestore()
	{
		return profileRestore;
	}

	public void setProfileRestore(ProfileRestore profileRestore)
	{
		this.profileRestore = profileRestore;
	}

	public Contact getContact()
	{
		if (contact == null)
			contact = new Contact();
		return contact;
	}

	public void setContact(Contact contact)
	{
		this.contact = contact;
	}

	@Transient
	public MultipartFile getFile()
	{
		return file;
	}

	@Transient
	public void setFile(MultipartFile file)
	{
		this.file = file;
	}

	@Transient
	public void updateListProfile(List<? extends ProfileEntity> list)
	{
		if (list != null)
			for (ProfileEntity entity : list)
				entity.setProfile(this);
	}
}
