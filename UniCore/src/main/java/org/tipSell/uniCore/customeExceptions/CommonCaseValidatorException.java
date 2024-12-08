package org.tipSell.uniCore.customeExceptions;

import java.util.List;
import java.util.Map;

public class CommonCaseValidatorException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public Map<String, List<String>> errors;

	public CommonCaseValidatorException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommonCaseValidatorException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public CommonCaseValidatorException(Map<String, List<String>> errors) {
		super();
		this.errors = errors;
	}

	
}
