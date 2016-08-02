package net.devstudy.resume.form;

import java.io.Serializable;

import net.devstudy.resume.annotation.constraints.EnglishLanguage;
import net.devstudy.resume.annotation.constraints.FieldMatch;
import net.devstudy.resume.annotation.constraints.PasswordStrength;

@FieldMatch(firstField = "password", secondField = "confirm")
public class ChangePasswordForm implements Serializable {
	
	private static final long serialVersionUID = 2776455703501721852L;

	public ChangePasswordForm() {
		super();
	}

	@EnglishLanguage(withPunctuations = false)
	@PasswordStrength
	private String password;

	private String confirm;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
}
