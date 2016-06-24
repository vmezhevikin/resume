package net.devstudy.resume.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.devstudy.resume.annotation.constraints.Phone;

public class PhoneConstraintValidator implements ConstraintValidator<Phone, String> {
	
	@Override
	public void initialize(Phone constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}

		return Pattern.matches("^([+]{1})([0-9]{3})([0-9]{1,4})([0-9]{7,12})$", value);
	}
}
