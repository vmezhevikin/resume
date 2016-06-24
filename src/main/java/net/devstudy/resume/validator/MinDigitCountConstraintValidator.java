package net.devstudy.resume.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.devstudy.resume.annotation.constraints.MinDigitCount;

public class MinDigitCountConstraintValidator implements ConstraintValidator<MinDigitCount, String> {
	
	private static final String DIGITS = "0123456789";

	private int count;

	@Override
	public void initialize(MinDigitCount constraintAnnotation) {
		this.count = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}

		int countDigits = 0;
		for (int i = 0; i < value.length(); i++) {
			char ch = value.charAt(i);
			if (DIGITS.indexOf(ch) != -1) {
				countDigits++;
			}
		}

		if (countDigits >= count) {
			return true;
		}
		else {
			return false;
		}
	}
}
