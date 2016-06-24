package net.devstudy.resume.annotation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import net.devstudy.resume.validator.EnglishLanguageConstraintValidator;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { EnglishLanguageConstraintValidator.class })
public @interface EnglishLanguage {
	String message() default "EnglishLanguage";

	// 0123456789
	boolean withNumbers() default true;

	// .,?!-:()'"[]{}; \t\n
	boolean withPunctuations() default true;

	// ~#$%^&*-+=_\\|/@`!'\";:><,.?{}
	boolean withSpecSymbols() default true;

	Class<? extends Payload>[] payload() default {};

	Class<?>[] groups() default {};
}
