package net.devstudy.resume.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

import net.devstudy.resume.annotation.constraints.FieldMatch;

public class FieldMatchConstraintValidator implements ConstraintValidator<FieldMatch, Object>
{
	private String first;

	private String second;

	@Override
	public void initialize(FieldMatch constraintAnnotation)
	{
		this.first = constraintAnnotation.first();
		this.second = constraintAnnotation.second();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context)
	{
		try
		{
			String firstStr = BeanUtils.getProperty(value, first);
			String secondStr = BeanUtils.getProperty(value, second);

			return firstStr == null && secondStr == null || firstStr != null && firstStr.equals(secondStr);
		} catch (Exception e)
		{
			return false;
		}
	}
}
