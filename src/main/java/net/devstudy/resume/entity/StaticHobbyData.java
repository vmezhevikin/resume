package net.devstudy.resume.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "static_hobby_data")
public class StaticHobbyData extends AbstractEntity<Long> {
	
	private static final long serialVersionUID = -4937799992479955679L;

	@Id
	@Column(name = "id")
	@SequenceGenerator(name = "STATIC_HOBBY_DATA_ID_GENERATOR", sequenceName = "static_hobby_data_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STATIC_HOBBY_DATA_ID_GENERATOR")
	private Long id;

	@Column(nullable = false, length = 100)
	private String icon;

	@Column(nullable = false, length = 25)
	private String name;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}