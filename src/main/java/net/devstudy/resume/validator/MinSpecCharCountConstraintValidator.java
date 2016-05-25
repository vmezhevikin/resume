package net.devstudy.resume.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.devstudy.resume.annotation.constraints.MinSpecCharCount;

public class MinSpecCharCountConstraintValidator implements ConstraintValidator<MinSpecCharCount, String>
{
	private int count;
	
	private String letters;

	@Override
	public void initialize(MinSpecCharCount constraintAnnotation)
	{
		this.count = constraintAnnotation.value();
		this.letters = constraintAnnotation.specSymbols();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context)
	{
		if (value == null)
			return true;

		int countLetters = 0;
		for (int i = 0; i < value.length(); i++)
		{
			char ch = value.charAt(i);
			if (letters.indexOf(ch) != -1)
				countLetters++;
		}
		
		if (countLetters >= count)
			return true;
		else
			return false;
	}
}
