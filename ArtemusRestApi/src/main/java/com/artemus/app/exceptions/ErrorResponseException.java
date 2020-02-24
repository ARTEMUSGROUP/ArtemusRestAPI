package com.artemus.app.exceptions;

public class ErrorResponseException extends RuntimeException {
	private static final long serialVersionUID = 1197387672493722906L;

	public ErrorResponseException(String message) {
        super(message);
    }
}
