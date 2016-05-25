package net.devstudy.resume.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

import net.devstudy.resume.annotation.constraints.FirstFieldLessThanSecond;

public class FirstFieldLessThanSecondConstraintValidator implements ConstraintValidator<FirstFieldLessThanSecond, Object>
{
	private String first;

	private String second;

	@Override
	public void initialize(FirstFieldLessThanSecond constraintAnnotation)
	{
		this.first = constraintAnnotation.first();
		this.second = constraintAnnotation.second();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context)
	{
		try
		{
			String firstObj = BeanUtils.getProperty(value, first);
			String secondObj = BeanUtils.getProperty(value, second);
			
			if (firstObj == null && secondObj != null)
				return false;
			
			if (firstObj == null && secondObj == null)
				return true;
			
			if (firstObj != null && secondObj == null)
				return true;

			return firstObj.compareTo(secondObj) <= 0;
		} catch (Exception e)
		{
			return false;
		}
	}
}
