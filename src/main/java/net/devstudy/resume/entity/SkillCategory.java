package net.devstudy.resume.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "skill_category")
public class SkillCategory extends AbstractEntity<Long> {
	
	private static final long serialVersionUID = -5449563594231337899L;

	@Id
	@Column
	private Long id;

	@Column(nullable = false, length = 25)
	private String name;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
