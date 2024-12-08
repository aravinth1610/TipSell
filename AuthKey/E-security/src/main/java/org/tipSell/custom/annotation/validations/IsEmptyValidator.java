package org.tipSell.custom.annotation.validations;

import org.springframework.stereotype.Component;
import org.tipSell.custom.annotation.IsEmpty;
import org.tipSell.validations.Services.RequestValidationsServices;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IsEmptyValidator implements ConstraintValidator<IsEmpty, Object> {

	private final RequestValidationsServices requestValidations;

	@Override
	public void initialize(IsEmpty constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {

		return requestValidations.isValue(value);
//		if (requestValidations.isValue(value)) {
//			context.buildConstraintViolationWithTemplate("Request Param or Header cannot be empty or blank.")
//					.addConstraintViolation().disableDefaultConstraintViolation();
//			return false;
//		}
//		return true;
	}

}
