package net.devstudy.resume.annotation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import net.devstudy.resume.validator.MinSpecCharCountConstraintValidator;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { MinSpecCharCountConstraintValidator.class })
public @interface MinSpecCharCount
{
	String message() default "MinSpecCharCount";

	String specSymbols() default "!@~`#$%^&*()_-+=|\\/{}[].,;:/?";

	int value() default 1;

	Class<? extends Payload>[] payload() default {};

	Class<?>[] groups() default {};
}
