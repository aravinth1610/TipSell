package org.tipSell.uniCore.securityConstant;

import org.springframework.web.bind.MethodArgumentNotValidException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SecurityMessages {

	// Informational Responses (1xx)
	CONTINUE("AUT1x0INFO", "Continue", "INFORMATIONAL", "INFO"),
	SWITCHING_PROTOCOLS("AUT1x1INFO", "Switching Protocols", "INFORMATIONAL", "INFO"),
	PROCESSING("AUT1x2INFO", "Processing", "INFORMATIONAL", "INFO"),

	// Miscellaneous
	VERIFY_EMAIL("AUT2x0MISC", "Verify your email.", "MISC", "INFO"),
	RESET_PASSWORD("AUT2x0MISC", "Reset your password.", "MISC", "INFO"),
	CLAIMNAME("AUT2x0MISC", "username", "MISC", "INFO"),

	// Client AUTH Errors (4xx)
	UNAUTHORIZED("AUT4x1AE","Authentication is required and has failed or has not yet been provided.", "AUTH_ERROR", "ERROR"),
	ACCESS_DENIED("AUT4x3CE", "You don't have permission to access this resource.", "AUTH_ERROR", "ERROR"),
	TOKEN_UNVERIFIABLE("AUT4x1AE", "Token cannot be verified.", "AUTH_ERROR", "ERROR"),
	INVALID_TOKEN("AUT4x1AE", "Token is not valid.", "AUTH_ERROR", "ERROR"),
	FORBIDDEN("AUT4x3AE", "You need to be logged in to access this resource.", "AUTH_ERROR", "ERROR"),
	ACCOUNT_LOCKED("AUT4x3AE", "Your account has been locked.", "AUTH_ERROR", "ERROR"),
	INCORRECT_CREDENTIALS("AUT4x1AE", "Incorrect username or password.", "AUTH_ERROR", "ERROR"),
	ACCOUNT_DISABLED("AUT4x3AE", "Your account has been disabled.", "AUTH_ERROR", "ERROR"),
	TOKEN_EXPIRED("AUT4x1AE", "User Token is Expired.", "AUTH_ERROR", "ERROR"),

	// Client Errors (4xx)
	INVALID_OPERATION("AUT4x0CE", "You cannot perform this operation.", "CLIENT_ERROR", "ERROR"),
	SAME_EMAIL_EXISTS("AUT4x0CE", "You cannot update with your existing email.", "CLIENT_ERROR", "ERROR"),
	INVALID_PASSWORD("AUT4x0CE", "Invalid Password.", "CLIENT_ERROR", "ERROR"),
	INVALID_OPERATOR("AUT4x0CE", "You cannot perform this operation.", "CLIENT_ERROR", "ERROR"),

	RESOURCE_NOT_FOUND("AUT4x4CE", "The requested resource was not found.", "CLIENT_ERROR", "ERROR"),
	INVALID_ARGUMENT("AUT4x4CE", "Invalid input provided. Please check the input.", "CLIENT_ERROR", "ERROR"),
	METHOD_VALIDATION("AUT4x4CE", "This operation is not allowed due to validation errors.", "CLIENT_ERROR", "ERROR"),
	NOT_FOUND_ERROR("AUT4x4CE", "404 Not Found.", "CLIENT_ERROR", "ERROR"),
	USER_NOT_FOUND("AUT4x4CE", "No user found.", "CLIENT_ERROR", "ERROR"),
	EMAIL_NOT_FOUND("AUT4x4CE", "Email address does not exist.", "CLIENT_ERROR", "ERROR"),
	EMAIL_EXISTS("AUT4x9CE", "User exists with this email address.", "CLIENT_ERROR", "ERROR"),

	METHOD_NOT_ALLOWED("AUT4x5CE", "This operation is not allowed. Only %s operations are allowed.", "CLIENT_ERROR","ERROR"),

	// Server Errors (5xx)
	INTERNAL_SERVER_ERROR("AUT5x0SE", "An error occurred while processing your request.", "SERVER_ERROR", "ERROR"),
	FILE_PROCESSING_ERROR("AUT5x0SE", "There was an error while processing your file.", "SERVER_ERROR", "ERROR"),
	USER_NOT_CREATED("AUT5x0SE", "User is not created.", "SERVER_ERROR", "ERROR");

	private final String status;
	private final String message;
	private final String category;
	private final String severity;

	public String value() {
		return status;
	}

	public String message() {
		return message;
	}

	public String category() {
		return category;
	}

	public String severity() {
		return severity;
	}

}
