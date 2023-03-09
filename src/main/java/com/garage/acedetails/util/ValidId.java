package com.garage.acedetails.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdValidator.class)
public @interface ValidId {
	String message() default "Id is invalid";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
