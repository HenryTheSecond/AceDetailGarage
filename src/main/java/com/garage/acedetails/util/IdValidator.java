package com.garage.acedetails.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdValidator implements ConstraintValidator<ValidId, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		try {
			Long.parseLong(value);
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
	}

}
