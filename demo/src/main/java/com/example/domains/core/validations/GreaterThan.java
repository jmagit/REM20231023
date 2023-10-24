package com.example.domains.core.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GreaterThanValidator.class)
@Documented
public @interface GreaterThan {
	String message() default "{validation.GreaterThan.message}";
	String minor();
	String major();
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
