package net.devstudy.resume.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import net.devstudy.resume.annotation.constraints.EnglishLanguage;
import net.devstudy.resume.annotation.constraints.FirstFieldLessThanSecond;

@Entity
@Table(name = "education")
@FirstFieldLessThanSecond(first = "startingYear", second = "completionYear")
public class Education extends AbstractEntity<Long> implements Serializable, ProfileEntity
{
	private static final long serialVersionUID = 8257785827490293025L;

	@Id
	@Column(unique = true, nullable = false)
	@SequenceGenerator(name = "EDUCATION_ID_GENERATOR", sequenceName = "education_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EDUCATION_ID_GENERATOR")
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_profile", nullable = false)
	private Profile profile;

	@Column(nullable = false, length = 100)
	@EnglishLanguage
	@Size(min = 1, message = "Don't leave it empty")
	@SafeHtml(whitelistType = WhiteListType.NONE, message = "Html is not allowed")
	private String speciality;

	@Column(nullable = false, length = 100)
	@EnglishLanguage
	@Size(min = 1, message = "Don't leave it empty")
	@SafeHtml(whitelistType = WhiteListType.NONE, message = "Html is not allowed")
	private String university;

	@Column(nullable = false, length = 100)
	@EnglishLanguage
	@Size(min = 1, message = "Don't leave it empty")
	@SafeHtml(whitelistType = WhiteListType.NONE, message = "Html is not allowed")
	private String department;

	@Column(name = "starting_year", nullable = false)
	private Integer startingYear;

	@Column(name = "completion_year")
	private Integer completionYear;

	@Override
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Profile getProfile()
	{
		return profile;
	}

	public void setProfile(Profile profile)
	{
		this.profile = profile;
	}

	public String getSpeciality()
	{
		return speciality;
	}

	public void setSpeciality(String speciality)
	{
		this.speciality = speciality;
	}

	public String getUniversity()
	{
		return university;
	}

	public void setUniversity(String university)
	{
		this.university = university;
	}

	public String getDepartment()
	{
		return department;
	}

	public void setDepartment(String department)
	{
		this.department = department;
	}

	public Integer getStartingYear()
	{
		return startingYear;
	}

	public void setStartingYear(Integer startingYear)
	{
		this.startingYear = startingYear;
	}

	public Integer getCompletionYear()
	{
		return completionYear;
	}

	public void setCompletionYear(Integer completionYear)
	{
		this.completionYear = completionYear;
	}

	@Transient
	public boolean hasAllNullFields()
	{
		return id == null && profile == null && speciality == null && university == null && department == null && startingYear == null
				&& completionYear == null;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((completionYear == null) ? 0 : completionYear.hashCode());
		result = prime * result + ((department == null) ? 0 : department.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((startingYear == null) ? 0 : startingYear.hashCode());
		result = prime * result + ((university == null) ? 0 : university.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Education other = (Education) obj;
		if (completionYear == null)
		{
			if (other.completionYear != null)
				return false;
		} else if (!completionYear.equals(other.completionYear))
			return false;
		if (department == null)
		{
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (id == null)
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (startingYear == null)
		{
			if (other.startingYear != null)
				return false;
		} else if (!startingYear.equals(other.startingYear))
			return false;
		if (university == null)
		{
			if (other.university != null)
				return false;
		} else if (!university.equals(other.university))
			return false;
		return true;
	}
}