package com.subramanian.karthick.com.automation.pageobject;

public class AutomationErrorException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public enum ErrorKind {

		TIMEOUT,
		AUTOMATION_LOGIC,
		PRODUCT_LOGIC
	}

	protected ErrorKind errorType;

	public AutomationErrorException(String message) {
		this(ErrorKind.PRODUCT_LOGIC, message, null);
	}

	public AutomationErrorException(ErrorKind kind, String message) {
		this(kind, message, null);
	}

	public AutomationErrorException(String message, Throwable cause) {
		this(ErrorKind.AUTOMATION_LOGIC, message, cause);
	}

	public AutomationErrorException(ErrorKind kind, String message, Throwable cause) {
		super(message, cause);
		if (null == kind) {
			kind = ErrorKind.AUTOMATION_LOGIC;
		}
		this.errorType = kind;
	}

	public ErrorKind getErrorType() {
		return this.errorType;
	}

}
