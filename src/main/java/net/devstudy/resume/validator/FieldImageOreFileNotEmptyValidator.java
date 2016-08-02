package net.devstudy.resume.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import net.devstudy.resume.annotation.constraints.FieldImageOrFileNotEmpty;

public class FieldImageOreFileNotEmptyValidator implements ConstraintValidator<FieldImageOrFileNotEmpty, Object> {
	
	private String imageField;

	private String fileField;

	@Override
	public void initialize(FieldImageOrFileNotEmpty constraintAnnotation) {
		this.imageField = constraintAnnotation.imageField();
		this.fileField = constraintAnnotation.fileField();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		try {
			String image = (String) BeanUtils.getPropertyDescriptor(value.getClass(), imageField).getReadMethod().invoke(value);
			MultipartFile file = (MultipartFile) BeanUtils.getPropertyDescriptor(value.getClass(), fileField).getReadMethod().invoke(value);
			if (!file.isEmpty()) {
				return true;
			}
			if (file.isEmpty() && image != null) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}