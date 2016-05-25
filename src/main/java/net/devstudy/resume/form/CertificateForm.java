package net.devstudy.resume.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import net.devstudy.resume.entity.Certificate;

public class CertificateForm implements Serializable
{
	private static final long serialVersionUID = 8321334102434190729L;

	@Valid
	private List<Certificate> items = new ArrayList<>();

	public CertificateForm()
	{
		super();
	}

	public CertificateForm(List<Certificate> items)
	{
		super();
		this.items = items;
	}

	public List<Certificate> getItems()
	{
		return items;
	}

	public void setItems(List<Certificate> items)
	{
		this.items = items;
	}
}
