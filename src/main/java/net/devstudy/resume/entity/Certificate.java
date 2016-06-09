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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.devstudy.resume.annotation.constraints.EnglishLanguage;
import net.devstudy.resume.annotation.constraints.NotEmptyFile;

@Entity
@Table(name = "certificate")
public class Certificate extends AbstractEntity<Long> implements Serializable, ProfileEntity
{
	private static final long serialVersionUID = -6718545401459519784L;

	@Id
	@Column(unique = true, nullable = false)
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "certificate_seq")
	@SequenceGenerator(name = "CERTIFICATE_ID_GENERATOR", sequenceName = "certificate_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CERTIFICATE_ID_GENERATOR")
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_profile", nullable = false)
	@JsonIgnore
	private Profile profile;

	@Column(nullable = false, length = 50)
	@EnglishLanguage
	@Size(min = 1, message = "Don't leave it empty")
	@SafeHtml(whitelistType = WhiteListType.NONE, message = "Html is not allowed")
	private String description;

	@Column(nullable = false, length = 255)
	@JsonIgnore
	private String img;

	@Column(name = "img_small", nullable = false, length = 255)
	@JsonIgnore
	private String imgSmall;
	
	@Transient
	@NotEmptyFile
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

	public String getImg()
	{
		return img;
	}

	public void setImg(String img)
	{
		this.img = img;
	}

	public String getImgSmall()
	{
		return imgSmall;
	}

	public void setImgSmall(String imgSmall)
	{
		this.imgSmall = imgSmall;
	}

	public MultipartFile getFile()
	{
		return file;
	}

	public void setFile(MultipartFile file)
	{
		this.file = file;
	}

	@Transient
	public boolean hasAllNullFields()
	{
		return id == null && profile == null && description == null && img == null && imgSmall == null;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((img == null) ? 0 : img.hashCode());
		result = prime * result + ((imgSmall == null) ? 0 : imgSmall.hashCode());
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
		Certificate other = (Certificate) obj;
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
		if (img == null)
		{
			if (other.img != null)
				return false;
		} else if (!img.equals(other.img))
			return false;
		if (imgSmall == null)
		{
			if (other.imgSmall != null)
				return false;
		} else if (!imgSmall.equals(other.imgSmall))
			return false;
		return true;
	}
}
