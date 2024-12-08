package org.tipSell.custom.annotation.validations;

import org.tipSell.custom.annotation.IsUserRegister;
import org.tipSell.eSecurity.payload.request.RegisterRequest;
import org.tipSell.validations.Services.RequestValidationsServices;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsUserRegisterValidator implements ConstraintValidator<IsUserRegister, RegisterRequest> {

	private final RequestValidationsServices requestValidations;

	private String mailField;
	private String confirmPassword;

	public IsUserRegisterValidator(RequestValidationsServices requestValidations) {
		super();
		this.requestValidations = requestValidations;
	}

	@Override
	public void initialize(IsUserRegister constraintAnnotation) {

		this.mailField = constraintAnnotation.mailField();
		this.confirmPassword = constraintAnnotation.confirmPassword();

	}

	@Override
	public boolean isValid(RegisterRequest userRegisterRequest, ConstraintValidatorContext context) {

		boolean isValid = true;

		if (!requestValidations.isMailValid(userRegisterRequest.getMail())) {
			isValid = false;
			context.buildConstraintViolationWithTemplate("Invalid address.").addPropertyNode(mailField)
					.addConstraintViolation().disableDefaultConstraintViolation();
		}

		if (requestValidations.isMailExists(userRegisterRequest.getMail())) {
			isValid = false;
			context.buildConstraintViolationWithTemplate("Mail ID already exists.").addPropertyNode(mailField)
					.addConstraintViolation().disableDefaultConstraintViolation();
		}

		if (!requestValidations.isPasswordConfirmPasswordMatched(userRegisterRequest.getPassword(),
				userRegisterRequest.getConfirmPassword())) {
			isValid = false;
			context.buildConstraintViolationWithTemplate("Password mismatch.").addPropertyNode(confirmPassword)
					.addConstraintViolation().disableDefaultConstraintViolation();
		}

		return isValid;

	}

}
