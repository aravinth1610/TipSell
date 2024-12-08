package org.tipSell.custom.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tipSell.custom.annotation.validations.IsEmptyValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


@Target({ElementType.PARAMETER, ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsEmptyValidator.class)
public @interface IsEmpty {
	
	String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
	
}
