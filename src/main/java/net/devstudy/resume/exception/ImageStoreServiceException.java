package net.devstudy.resume.exception;

public class ImageStoreServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 2274562072047682672L;

	public ImageStoreServiceException(String message) {
		super(message);
	}

	public ImageStoreServiceException(Throwable cause) {
		super(cause);
	}

	public ImageStoreServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}