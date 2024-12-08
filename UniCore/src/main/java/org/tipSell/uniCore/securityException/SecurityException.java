package org.tipSell.uniCore.securityException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.HandlerMethodValidator;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.tipSell.uniCore.customeResponse.ErrorResponse;
import org.tipSell.uniCore.customeResponse.ResponseEntityWrapper;
import org.tipSell.uniCore.securityConstant.SecurityConstant;
import org.tipSell.uniCore.securityConstant.SecurityMessages;
import org.tipSell.uniCore.customeExceptions.CommonCaseException;
import org.tipSell.uniCore.customeExceptions.CommonCaseValidatorException;
import org.tipSell.uniCore.customeExceptions.UnauthorizedException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintDefinitionException;

/**
 * @Author aravinth
 * @since 2024
 *
 *        A sample source file for the code formatter preview
 */
@RestControllerAdvice
public class SecurityException  {
	
	private ResponseEntityWrapper<?> buildErrorResponse(HttpStatus status, String value,String message,String schemaPath, Map<String, ?> validationErrors) {
		
	//	String errorMessage = (message == null ? securityMessage.message() : message);
		
		ErrorResponse<?> errorResponse = new ErrorResponse<>(value, message, schemaPath, validationErrors);
		return new ResponseEntityWrapper<>("Failure", status.getReasonPhrase(), errorResponse);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntityWrapper<?> handleValidationError(MethodArgumentNotValidException e,
			HttpServletRequest request) {
		Map<String, List<String>> errors = new HashMap<>();
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		
		for (FieldError fieldError : fieldErrors) {
			String field = fieldError.getField();
			String errorMessage = fieldError.getDefaultMessage();

			errors.computeIfAbsent(field, k -> new ArrayList<>()).add(errorMessage);
		}

		return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,SecurityMessages.INVALID_ARGUMENT.value(), SecurityMessages.INVALID_ARGUMENT.message(), request.getRequestURI(), errors);

	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CommonCaseValidatorException.class)
	public ResponseEntityWrapper<?> handleCommonCaseValidatorException(CommonCaseValidatorException e, HttpServletRequest request) {
		return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,SecurityMessages.INVALID_ARGUMENT.value(), SecurityMessages.INVALID_ARGUMENT.message(), request.getRequestURI(),e.errors);

	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HandlerMethodValidationException.class)
	public ResponseEntityWrapper<?> handleMethodValidationException(HandlerMethodValidationException e,
			HttpServletRequest request) {
		e.printStackTrace();
		
		 Map<String, List<String>> errors = new HashMap<>();

	        // Iterate over all errors (could be FieldError or ObjectError)
	        e.getAllErrors().forEach(error -> {
	            if (error instanceof FieldError) {
	                FieldError fieldError = (FieldError) error;
	                errors.computeIfAbsent(fieldError.getField(), key -> new ArrayList<>()).add(fieldError.getDefaultMessage());
	            } else if (error instanceof ObjectError) {
	                ObjectError objectError = (ObjectError) error;
	                errors.computeIfAbsent(objectError.getObjectName(), key -> new ArrayList<>()).add(objectError.getDefaultMessage());
	            } else {
	            	String defaultFieldName = extractFieldNameFromResolvable(error);
	                errors.computeIfAbsent(defaultFieldName, key -> new ArrayList<>()).add(error.getDefaultMessage());
	            }
	        });
	        
		return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, SecurityMessages.METHOD_VALIDATION.value(),
				SecurityMessages.METHOD_VALIDATION.message(), request.getRequestURI(), errors);

	}
	
	  // Extract the field or variable name in a fallback case
    private String extractFieldNameFromResolvable(MessageSourceResolvable error) {
        // Try to extract a meaningful field name or object name from the codes if possible
    	System.out.println(error);
        String[] codes = error.getCodes();
        if (codes != null && codes.length > 0) {
            // Use the first code as a potential field name, as it's typically the most relevant one
            return codes[0];
        }

        // Fallback to "unknownField" or another default name if no code is available
        return "unknownField";
    }
	
	

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResponseEntityWrapper<?> handleInternalServerError(Exception e, HttpServletRequest request) {
		// e.getMessage() need to use in logger
		e.printStackTrace();

		if (null != e.getMessage())
			return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, SecurityMessages.INTERNAL_SERVER_ERROR.value(),e.getMessage(), request.getRequestURI(), null);

		return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, SecurityMessages.INTERNAL_SERVER_ERROR.value(), SecurityMessages.INTERNAL_SERVER_ERROR.message(), request.getRequestURI(), null);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CommonCaseException.class)
	public ResponseEntityWrapper<?> handleCommonCaseException(CommonCaseException e, HttpServletRequest request) {
		e.printStackTrace();

		if (null != e.getMessage())
			return buildErrorResponse(HttpStatus.BAD_REQUEST, SecurityMessages.RESOURCE_NOT_FOUND.value(),e.getMessage(), request.getRequestURI(), null);

		return buildErrorResponse(HttpStatus.BAD_REQUEST, SecurityMessages.RESOURCE_NOT_FOUND.value(), SecurityMessages.RESOURCE_NOT_FOUND.message(), request.getRequestURI(), null);
	}
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntityWrapper<?> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
		e.printStackTrace();

		if (null != e.getMessage())
			return buildErrorResponse(HttpStatus.UNAUTHORIZED, SecurityMessages.ACCESS_DENIED.value(), e.getMessage(), request.getRequestURI(), null);

		return buildErrorResponse(HttpStatus.UNAUTHORIZED, SecurityMessages.ACCESS_DENIED.value(), SecurityMessages.ACCESS_DENIED.message(), request.getRequestURI(), null);
	}

//	@ExceptionHandler(NoResultException.class)
//	public ResponseEntity<ResponseEntityWrapper<?>> handleNotFoundError(NoResultException e, HttpServletRequest request) {
//		return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,null,e.getMessage(), request.getRequestURI());
//	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntityWrapper<?> handleMissingServletRequestParameterException(MissingRequestHeaderException e,
			HttpServletRequest request) {
		e.printStackTrace();

		return buildErrorResponse(HttpStatus.BAD_REQUEST, SecurityMessages.RESOURCE_NOT_FOUND.value(), e.getMessage(), request.getRequestURI(), null);
	}

	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntityWrapper<?> handleUnsupportedMethodError(HttpRequestMethodNotSupportedException e,
			HttpServletRequest request) {
		e.printStackTrace();
		HttpMethod supportedMethod = Objects.requireNonNull(e.getSupportedHttpMethods()).iterator().next();
		return buildErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, SecurityMessages.METHOD_NOT_ALLOWED.value(),String.format(SecurityMessages.METHOD_NOT_ALLOWED.message(), supportedMethod), request.getRequestURI(),null);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(IOException.class)
	public ResponseEntityWrapper<?> handleIOException(IOException e, Integer errorCode, String errorMessage,
			HttpServletRequest request) {
		e.printStackTrace();
		return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, SecurityMessages.INTERNAL_SERVER_ERROR.value(),SecurityMessages.INTERNAL_SERVER_ERROR.message(), request.getRequestURI(), null);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntityWrapper<?> illegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
		e.printStackTrace();
		return buildErrorResponse(HttpStatus.BAD_REQUEST, SecurityMessages.INVALID_ARGUMENT.value(), SecurityMessages.INVALID_ARGUMENT.message(), request.getRequestURI(), null);
	}

	// their is 4004 error
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntityWrapper<?> UnauthorizedException(UnauthorizedException e, HttpServletRequest request) {
		e.printStackTrace();

		if (null != e.getMessage())
			return buildErrorResponse(HttpStatus.FORBIDDEN, SecurityMessages.FORBIDDEN.value(),e.getMessage(), request.getRequestURI(), null);			
			
		return buildErrorResponse(HttpStatus.FORBIDDEN, SecurityMessages.FORBIDDEN.value(), SecurityMessages.FORBIDDEN.message(), request.getRequestURI(), null);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntityWrapper<?> UnauthorizedException(NoResourceFoundException e, HttpServletRequest request) {
		e.printStackTrace();
		return buildErrorResponse(HttpStatus.NOT_FOUND, SecurityMessages.NOT_FOUND_ERROR.value(), SecurityMessages.NOT_FOUND_ERROR.message(), request.getRequestURI(), null);
	}

	
	
	
//	    @ExceptionHandler(BadCredentialsException.class)
//	    public ResponseEntity<ErrorResponse> handleIncorrectCredentialsError(BadCredentialsException e) {
//	        return buildErrorResponse(HttpStatus.BAD_REQUEST, AppConstants.INCORRECT_CREDENTIALS, null);
//	    }
//
//
//	    @ExceptionHandler(LockedException.class)
//	    public ResponseEntity<ErrorResponse> handleLockedException(LockedException e) {
//	        return buildErrorResponse(HttpStatus.FORBIDDEN, AppConstants.ACCOUNT_LOCKED, null);
//	    }
//
//	    @ExceptionHandler(DisabledException.class)
//	    public ResponseEntity<ErrorResponse> handleAccountDisabledException(DisabledException e) {
//	        return buildErrorResponse(HttpStatus.BAD_REQUEST, AppConstants.ACCOUNT_DISABLED, null);
//	    }
//
//	    @ExceptionHandler(TokenExpiredException.class)
//	    public ResponseEntity<ErrorResponse> handleTokenExpiredException(TokenExpiredException e) {
//	        return buildErrorResponse(HttpStatus.UNAUTHORIZED, AppConstants.INVALID_TOKEN, null);
//	    }
//
//	    @ExceptionHandler(SignatureVerificationException.class)
//	    public ResponseEntity<ErrorResponse> handleInvalidTokenException(SignatureVerificationException e) {
//	        return buildErrorResponse(HttpStatus.UNAUTHORIZED, AppConstants.INVALID_TOKEN, null);
//	    }
//
//	    @ExceptionHandler(AuthenticationServiceException.class)
//	    public ResponseEntity<ErrorResponse> handleInvalidTokenException(AuthenticationServiceException e) {
//	        return buildErrorResponse(HttpStatus.BAD_REQUEST, AppConstants.INCORRECT_CREDENTIALS, null);
//	    }
//
//	    @ExceptionHandler(EmailExistsException.class)
//	    public ResponseEntity<ErrorResponse> handleEmailExistsException(EmailExistsException e) {
//	        return buildErrorResponse(HttpStatus.BAD_REQUEST, AppConstants.EMAIL_EXISTS, null);
//	    }
//
//	    @ExceptionHandler(EmailNotFoundException.class)
//	    public ResponseEntity<ErrorResponse> handleEmailNotFoundException(EmailNotFoundException e) {
//	        return buildErrorResponse(HttpStatus.BAD_REQUEST, AppConstants.EMAIL_NOT_FOUND, null);
//	    }
//
//	    @ExceptionHandler(SameEmailUpdateException.class)
//	    public ResponseEntity<ErrorResponse> handleSameEmailUpdateException(SameEmailUpdateException e) {
//	        return buildErrorResponse(HttpStatus.BAD_REQUEST, AppConstants.SAME_EMAIL, null);
//	    }
//

}
