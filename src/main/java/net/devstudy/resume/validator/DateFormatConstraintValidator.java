package net.devstudy.resume.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import net.devstudy.resume.annotation.constraints.DateFormat;

public class DateFormatConstraintValidator implements ConstraintValidator<DateFormat, String>
{
	private int adulthoodAge;

	@Override
	public void initialize(DateFormat constraintAnnotation)
	{
		this.adulthoodAge = constraintAnnotation.adulthoodAge();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context)
	{
		if (value == null)
			return true;

		if (!Pattern.matches("^([0-9]{4})-([0-9]{1,2})-([0-9]{1,2})$", value))
			return false;
		try
		{
			String[] part = value.split("-");
			int year = Integer.parseInt(part[0]);
			int month = Integer.parseInt(part[1]);
			int day = Integer.parseInt(part[2]);

			LocalDate date = new LocalDate(year, month, day);
			LocalDate today = new LocalDate();
			int years = Years.yearsBetween(date, today).getYears();

			return years >= adulthoodAge;
		} catch (Exception e)
		{
			return false;
		}
	}
}
