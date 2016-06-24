package net.devstudy.resume.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.devstudy.resume.annotation.constraints.MinUpperCharCount;

public class MinUpperCharCountConstraintValidator implements ConstraintValidator<MinUpperCharCount, String> {
	
	private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private int count;

	@Override
	public void initialize(MinUpperCharCount constraintAnnotation) {
		this.count = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}

		int countLetters = 0;
		for (int i = 0; i < value.length(); i++) {
			char ch = value.charAt(i);
			if (LETTERS.indexOf(ch) != -1) {
				countLetters++;
			}
		}

		if (countLetters >= count) {
			return true;
		}
		else {
			return false;
		}
	}
}
