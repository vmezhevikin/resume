package net.devstudy.resume.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import net.devstudy.resume.entity.Education;

public class EducationForm implements Serializable
{
	private static final long serialVersionUID = 4574778304526438968L;
	
	@Valid
	private List<Education> items = new ArrayList<>();

	public EducationForm()
	{
		super();
	}

	public EducationForm(List<Education> items)
	{
		super();
		this.items = items;
	}

	public List<Education> getItems()
	{
		return items;
	}

	public void setItems(List<Education> items)
	{
		this.items = items;
	}
}
