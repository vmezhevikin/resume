package net.devstudy.resume.annotation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import net.devstudy.resume.validator.MinDigitCountConstraintValidator;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { MinDigitCountConstraintValidator.class })
public @interface MinDigitCount {
	String message() default "MinDigitCount";

	int value() default 1;

	Class<? extends Payload>[] payload() default {};

	Class<?>[] groups() default {};
}
