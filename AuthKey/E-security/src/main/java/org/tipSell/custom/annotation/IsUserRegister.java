package org.tipSell.custom.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tipSell.custom.annotation.validations.IsEmptyValidator;
import org.tipSell.custom.annotation.validations.IsUserRegisterValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsUserRegisterValidator.class)
public @interface IsUserRegister {

	String message() default "";

	String mailField();

	String password();
	
	String confirmPassword();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
