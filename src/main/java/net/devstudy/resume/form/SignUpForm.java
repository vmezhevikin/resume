package net.devstudy.resume.form;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import net.devstudy.resume.annotation.constraints.EnglishLanguage;
import net.devstudy.resume.annotation.constraints.FieldMatch;
import net.devstudy.resume.annotation.constraints.PasswordStrength;

@FieldMatch(first = "password", second = "confirm")
public class SignUpForm extends RecaptchaForm implements Serializable {
	
	private static final long serialVersionUID = 5105334805427908062L;

	public SignUpForm() {
		super();
	}

	@EnglishLanguage(withPunctuations = false, withSpecSymbols = false)
	@Size(min = 1, message = "Don't leave it empty")
	@SafeHtml(whitelistType = WhiteListType.NONE, message = "Html is not allowed")
	private String firstName;

	@EnglishLanguage(withPunctuations = false, withSpecSymbols = false)
	@Size(min = 1, message = "Don't leave it empty")
	@SafeHtml(whitelistType = WhiteListType.NONE, message = "Html is not allowed")
	private String lastName;

	@EnglishLanguage(withPunctuations = false)
	@PasswordStrength
	private String password;

	private String confirm;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

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
