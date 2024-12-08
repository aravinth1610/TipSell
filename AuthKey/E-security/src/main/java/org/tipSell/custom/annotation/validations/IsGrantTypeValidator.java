package org.tipSell.custom.annotation.validations;

import org.tipSell.custom.annotation.IsEmpty;
import org.tipSell.custom.annotation.IsGrantType;
import org.tipSell.validations.Services.RequestValidationsServices;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IsGrantTypeValidator implements ConstraintValidator<IsGrantType, String> {

	private final RequestValidationsServices requestValidations;

	@Override
	public void initialize(IsGrantType constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return requestValidations.isGrantTypeExists(value);
	}

}
