package net.devstudy.resume.form;

import org.hibernate.validator.constraints.NotEmpty;

public abstract class RecaptchaForm
{
	@NotEmpty(message = "Required")
	private String recaptchaResponse;

	public String getRecaptchaResponse()
	{
		return recaptchaResponse;
	}

	public void setRecaptchaResponse(String recaptchaResponse)
	{
		this.recaptchaResponse = recaptchaResponse;
	}
}