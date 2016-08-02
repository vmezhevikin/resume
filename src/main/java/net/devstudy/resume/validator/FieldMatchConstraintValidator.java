package net.devstudy.resume.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanUtils;

import net.devstudy.resume.annotation.constraints.FieldMatch;

public class FieldMatchConstraintValidator implements ConstraintValidator<FieldMatch, Object> {
	
	private String firstField;

	private String secondField;

	@Override
	public void initialize(FieldMatch constraintAnnotation) {
		this.firstField = constraintAnnotation.firstField();
		this.secondField = constraintAnnotation.secondField();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		try {
			Object first = BeanUtils.getPropertyDescriptor(value.getClass(), firstField).getReadMethod().invoke(value);
			Object second = BeanUtils.getPropertyDescriptor(value.getClass(), secondField).getReadMethod().invoke(value);
			return (first == null && second == null) || first != null && first.equals(second);
		} catch (Exception e) {
			return false;
		}
	}
}