package net.devstudy.resume.entity;

import java.io.Serializable;
import java.util.Date;

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

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.devstudy.resume.annotation.constraints.EnglishLanguage;

@Entity
@Table(name = "course")
public class Course extends AbstractEntity<Long> implements Serializable, ProfileEntity
{
	private static final long serialVersionUID = -7509905830407382879L;

	@Id
	@Column(unique = true, nullable = false)
	@SequenceGenerator(name = "COURSE_ID_GENERATOR", sequenceName = "course_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COURSE_ID_GENERATOR")
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_profile", nullable = false)
	@JsonIgnore
	private Profile profile;

	@Column(nullable = false, length = 100)
	@EnglishLanguage
	@Size(min = 1, message = "Don't leave it empty")
	private String description;

	@Column(nullable = false, length = 100)
	@EnglishLanguage
	@Size(min = 1, message = "Don't leave it empty")
	@JsonIgnore
	private String school;

	@Column(name = "completion_date")
	@JsonIgnore
	private Date completionDate;

	@Transient
	@JsonIgnore
	private Integer completionMonth;

	@Transient
	@JsonIgnore
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

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getSchool()
	{
		return school;
	}

	public void setSchool(String school)
	{
		this.school = school;
	}

	public Date getCompletionDate()
	{
		return completionDate;
	}

	@Transient
	public String getCompletionDateString()
	{
		LocalDate date = new LocalDate(completionDate);
		return date.toString("MMM yyyy");
	}

	public void setCompletionDate(Date completionDate)
	{
		this.completionDate = completionDate;
	}

	@Transient
	public Integer getCompletionMonth()
	{
		if (completionDate == null)
			return null;
		LocalDate date = new LocalDate(completionDate);
		return date.getMonthOfYear();
	}

	@Transient
	public void setCompletionMonth(Integer completionMonth)
	{
		this.completionMonth = completionMonth;
		setupCompletionDate();
	}

	@Transient
	public Integer getCompletionYear()
	{
		if (completionDate == null)
			return null;
		LocalDate date = new LocalDate(completionDate);
		return date.getYear();
	}

	@Transient
	public void setCompletionYear(Integer completionYear)
	{
		this.completionYear = completionYear;
		setupCompletionDate();
	}

	private void setupCompletionDate()
	{
		if (completionYear != null && completionMonth != null)
		{
			DateTime dateTime = new DateTime(completionYear, completionMonth, 1, 0, 0);
			Date date = new Date(dateTime.getMillis());
			setCompletionDate(date);
		} else
			setCompletionDate(null);
	}

	@Transient
	public boolean hasAllNullFields()
	{
		return id == null && profile == null && description == null && school == null && completionDate == null;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((completionDate == null) ? 0 : completionDate.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((school == null) ? 0 : school.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (description == null)
		{
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (school == null)
		{
			if (other.school != null)
				return false;
		} else if (!school.equals(other.school))
			return false;
		if (completionDate == null)
		{
			if (other.completionDate != null)
				return false;
		} else if (!completionDate.equals(other.completionDate))
			return false;
		return true;
	}

}
