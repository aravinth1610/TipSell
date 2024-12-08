package org.tipSell.uniCore.customeExceptions;

public class CommonCaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CommonCaseException() {
		super();
	}

	public CommonCaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommonCaseException(String message) {
		super(message);
	}

}
