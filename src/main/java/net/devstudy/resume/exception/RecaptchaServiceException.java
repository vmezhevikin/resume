package net.devstudy.resume.exception;

public class RecaptchaServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 4230534124578400747L;

	public RecaptchaServiceException(String message) {
		super(message);
	}

	public RecaptchaServiceException(Throwable cause) {
		super(cause);
	}

	public RecaptchaServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}