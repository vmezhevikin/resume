package net.devstudy.resume.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import net.devstudy.resume.entity.Experience;

public class ExperienceForm implements Serializable
{
	private static final long serialVersionUID = 4574778304526438968L;
	
	@Valid
	private List<Experience> items = new ArrayList<>();

	public ExperienceForm()
	{
		super();
	}

	public ExperienceForm(List<Experience> items)
	{
		super();
		this.items = items;
	}

	public List<Experience> getItems()
	{
		return items;
	}

	public void setItems(List<Experience> items)
	{
		this.items = items;
	}
}
