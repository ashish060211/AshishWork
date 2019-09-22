package com.mcdonald.dto;

public class BusinessException extends RuntimeException {
	private static final String DEFAULT_ERROR_CODE = "ERROR";

	private final String errorCode;

	private static final long serialVersionUID = 1L;

	public BusinessException() {
		super();
		errorCode = DEFAULT_ERROR_CODE;
	}

	public BusinessException(final String message) {
		super(message);
		errorCode = DEFAULT_ERROR_CODE;
	}

	public BusinessException(final String message, final String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public BusinessException(final String message, final Throwable cause, final String errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public BusinessException(final Throwable cause) {
		super(cause);
		errorCode = DEFAULT_ERROR_CODE;
	}

	public BusinessException(final String message, final Throwable cause) {
		super(message, cause);
		errorCode = DEFAULT_ERROR_CODE;
	}

	public BusinessException(final String message, final String errorCode, final Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	@Override
	public String getMessage() {
		if (DEFAULT_ERROR_CODE.equalsIgnoreCase(getErrorCode())) {
			return super.getMessage();
		} else {
			return getErrorCode() + "-" + super.getMessage();
		}
	}


}
