package net.devstudy.resume.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanUtils;

import net.devstudy.resume.annotation.constraints.FirstFieldLessThanSecond;

public class FirstFieldLessThanSecondConstraintValidator implements ConstraintValidator<FirstFieldLessThanSecond, Object> {

	private String firstField;

	private String secondField;

	@Override
	public void initialize(FirstFieldLessThanSecond constraintAnnotation) {
		this.firstField = constraintAnnotation.firstField();
		this.secondField = constraintAnnotation.secondField();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		try {
			Object first = BeanUtils.getPropertyDescriptor(value.getClass(), firstField).getReadMethod().invoke(value);
			Object second = BeanUtils.getPropertyDescriptor(value.getClass(), secondField).getReadMethod().invoke(value);
			
			if (first == null && second != null) {
				return false;
			}
			if (second == null) {
				return true;
			}
			if (first instanceof Comparable<?> && second instanceof Comparable<?>) {
				return ((Comparable<Object>) first).compareTo((Comparable<Object>) second) <= 0;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}