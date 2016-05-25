package net.devstudy.resume.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import net.devstudy.resume.entity.Skill;

public class SkillForm implements Serializable
{
	private static final long serialVersionUID = -6468612336014457333L;
	
	@Valid
	private List<Skill> items = new ArrayList<>();

	public SkillForm()
	{
		super();
	}

	public SkillForm(List<Skill> items)
	{
		super();
		this.items = items;
	}

	public List<Skill> getItems()
	{
		return items;
	}

	public void setItems(List<Skill> items)
	{
		this.items = items;
	}
}
