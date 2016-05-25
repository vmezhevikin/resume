package net.devstudy.resume.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "profile_restore")
public class ProfileRestore
{
	@Id
	@Column(name = "id")
	@SequenceGenerator(name = "SKILLCATEGORY_ID_GENERATOR", sequenceName = "profile_restore_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SKILLCATEGORY_ID_GENERATOR")
	private Long id;

	@OneToOne(optional = false)
	@JoinColumn(name = "id_profile", nullable = false)
	private Profile profile;
}