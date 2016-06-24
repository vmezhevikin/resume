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

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.devstudy.resume.annotation.constraints.EnglishLanguage;
import net.devstudy.resume.annotation.constraints.FirstFieldLessThanSecond;

@Entity
@Table(name = "experience")
@FirstFieldLessThanSecond(first = "startingDateStr", second = "completionDateStr")
public class Experience extends AbstractEntity<Long> implements Serializable, ProfileEntity {
	
	private static final long serialVersionUID = 6158879936084081673L;

	@Id
	@Column(unique = true, nullable = false)
	@SequenceGenerator(name = "EXPERIENCE_ID_GENERATOR", sequenceName = "experience_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXPERIENCE_ID_GENERATOR")
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_profile", nullable = false)
	@JsonIgnore
	private Profile profile;

	@Column(nullable = false, length = 100)
	@EnglishLanguage
	@Size(min = 1, message = "Don't leave it empty")
	@SafeHtml(whitelistType = WhiteListType.NONE, message = "Html is not allowed")
	private String company;

	@Column(nullable = false, length = 100)
	@EnglishLanguage
	@Size(min = 1, message = "Don't leave it empty")
	@SafeHtml(whitelistType = WhiteListType.NONE, message = "Html is not allowed")
	private String position;

	@Column(name = "starting_date", nullable = false)
	@JsonIgnore
	private Date startingDate;

	@Column(name = "completion_date")
	@JsonIgnore
	private Date completionDate;

	@Column(nullable = false, length = 2147483647)
	@EnglishLanguage
	@Size(min = 1, message = "Don't leave it empty")
	@SafeHtml(whitelistType = WhiteListType.NONE, message = "Html is not allowed")
	@JsonIgnore
	private String responsibility;

	@Column(length = 255)
	@EnglishLanguage
	@URL(message = "Not a URL address")
	@SafeHtml(whitelistType = WhiteListType.NONE, message = "Html is not allowed")
	@JsonIgnore
	private String demo;

	@Column(length = 255)
	@EnglishLanguage
	@URL(message = "Not a URL address")
	@SafeHtml(whitelistType = WhiteListType.NONE, message = "Html is not allowed")
	@JsonIgnore
	private String code;

	@Transient
	@JsonIgnore
	private Integer startingMonth;

	@Transient
	@JsonIgnore
	private Integer startingYear;

	@Transient
	@JsonIgnore
	private Integer completionMonth;

	@Transient
	@JsonIgnore
	private Integer completionYear;

	@Transient
	@JsonIgnore
	private String startingDateStr;

	@Transient
	@JsonIgnore
	private String completionDateStr;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Date getStartingDate() {
		return startingDate;
	}

	@Transient
	public String getStartingDateString() {
		LocalDate date = new LocalDate(startingDate);
		return date.toString("MMM yyyy");
	}

	public void setStartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}

	public Date getCompletionDate() {
		return this.completionDate;
	}

	@Transient
	public String getCompletionDateString() {
		LocalDate date = new LocalDate(completionDate);
		return date.toString("MMM yyyy");
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	@Transient
	public Integer getStartingMonth() {
		if (startingDate == null)
			return null;
		LocalDate date = new LocalDate(startingDate);
		return date.getMonthOfYear();
	}

	@Transient
	public void setStartingMonth(Integer startingMonth) {
		this.startingMonth = startingMonth;
		setupStartingDate();
	}

	@Transient
	public Integer getStartingYear() {
		if (startingDate == null)
			return null;
		LocalDate date = new LocalDate(startingDate);
		return date.getYear();
	}

	@Transient
	public void setStartingYear(Integer startingYear) {
		this.startingYear = startingYear;
		setupStartingDate();
	}

	@Transient
	public String getStartingDateStr() {
		return startingDateStr;
	}

	@Transient
	public void setStartingDateStr(String startingDateStr) {
		this.startingDateStr = startingDateStr;
	}

	@Transient
	public String getCompletionDateStr() {
		return completionDateStr;
	}

	@Transient
	public void setCompletionDateStr(String completionDateStr) {
		this.completionDateStr = completionDateStr;
	}

	private void setupStartingDate() {
		if (startingYear != null && startingMonth != null) {
			DateTime dateTime = new DateTime(startingYear, startingMonth, 1, 0, 0);
			Date date = new Date(dateTime.getMillis());
			setStartingDate(date);
			setStartingDateStr(startingYear + "-" + startingMonth);
		} else {
			setStartingDate(null);
			setStartingDateStr(null);
		}
	}

	@Transient
	public Integer getCompletionMonth() {
		if (completionDate == null) {
			return null;
		}
		LocalDate date = new LocalDate(completionDate);
		return date.getMonthOfYear();
	}

	@Transient
	public void setCompletionMonth(Integer completionMonth) {
		this.completionMonth = completionMonth;
		setupCompletionDate();
	}

	@Transient
	public Integer getCompletionYear() {
		if (completionDate == null) {
			return null;
		}
		LocalDate date = new LocalDate(completionDate);
		return date.getYear();
	}

	@Transient
	public void setCompletionYear(Integer completionYear) {
		this.completionYear = completionYear;
		setupCompletionDate();
	}

	@Transient
	@Override
	public boolean hasNullSubstantionalFields() {
		return id == null && profile == null && company == null && position == null && startingDate == null
				&& completionDate == null;
	}

	private void setupCompletionDate() {
		if (completionYear != null && completionMonth != null) {
			DateTime dateTime = new DateTime(completionYear, completionMonth, 1, 0, 0);
			Date date = new Date(dateTime.getMillis());
			setCompletionDate(date);
			setCompletionDateStr(completionYear + "-" + completionMonth);
		} else {
			setCompletionDate(null);
			setCompletionDateStr(null);
		}
	}

	public String getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}

	public String getDemo() {
		return demo;
	}

	public void setDemo(String demo) {
		this.demo = demo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((completionDate == null) ? 0 : completionDate.hashCode());
		result = prime * result + ((demo == null) ? 0 : demo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((responsibility == null) ? 0 : responsibility.hashCode());
		result = prime * result + ((startingDate == null) ? 0 : startingDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Experience other = (Experience) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (completionDate == null) {
			if (other.completionDate != null)
				return false;
		} else if (!completionDate.equals(other.completionDate))
			return false;
		if (demo == null) {
			if (other.demo != null)
				return false;
		} else if (!demo.equals(other.demo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (responsibility == null) {
			if (other.responsibility != null)
				return false;
		} else if (!responsibility.equals(other.responsibility))
			return false;
		if (startingDate == null) {
			if (other.startingDate != null)
				return false;
		} else if (!startingDate.equals(other.startingDate))
			return false;
		return true;
	}
}