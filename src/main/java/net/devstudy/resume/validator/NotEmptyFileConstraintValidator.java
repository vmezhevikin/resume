package net.devstudy.resume.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

import net.devstudy.resume.annotation.constraints.NotEmptyFile;

public class NotEmptyFileConstraintValidator implements ConstraintValidator<NotEmptyFile, MultipartFile>
{
	@Override
	public void initialize(NotEmptyFile constraintAnnotation)
	{
	}

	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context)
	{
		if (value == null)
			return false;
		else
			return !value.isEmpty();
	}
}
