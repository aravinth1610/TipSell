package org.tipSell.custom.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tipSell.custom.annotation.validations.IsGrantTypeValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.PARAMETER, ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsGrantTypeValidator.class)
public @interface IsGrantType {

	String message() default "Invalid grant type provided. Please use a valid grant type such as 'Authorization_Code', 'Client_Credential', 'Password', or 'Refresh_Token'.";
    
	Class<?>[] groups() default {};
    
	Class<? extends Payload>[] payload() default {};
	
}
