package net.devstudy.resume.annotation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import net.devstudy.resume.validator.FirstFieldLessThanSecondConstraintValidator;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { FirstFieldLessThanSecondConstraintValidator.class })
public @interface FirstFieldLessThanSecond {
	String message() default "FirstFieldLessThanSecond";

	String first();

	String second();

	Class<? extends Payload>[] payload() default {};

	Class<?>[] groups() default {};

	@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List {
		FirstFieldLessThanSecond[] value();
	}
}
