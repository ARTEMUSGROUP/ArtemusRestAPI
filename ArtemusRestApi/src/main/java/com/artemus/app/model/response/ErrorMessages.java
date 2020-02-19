package com.artemus.app.model.response;

public enum ErrorMessages {
    AUTHENTICATION_FAILED("Authentication failed"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields");
   
	
	private String errorMessage;

	private ErrorMessages(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
