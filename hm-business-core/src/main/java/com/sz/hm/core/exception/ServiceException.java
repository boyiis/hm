package com.sz.hm.core.exception;

/**
 * 
 */
public class ServiceException extends RuntimeException {

	/**
     *
     */
	private static final long serialVersionUID = -3287463281746412649L;
	private int errorCode;

	public ServiceException() {
		super();
	}

	/**
	 * 
	 * @param message
	 */
	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, int errorCode) {
		this(message);
		this.errorCode = errorCode;
	}

	public ServiceException(int errorCode) {
		this("logic exception");
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
