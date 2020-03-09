package com.artemus.app.model.response;

public enum ErrorMessages {
    AUTHENTICATION_FAILED("Authentication failed please check Authorization"),
    INTERNAL_SERVER_ERROR("Internal server processing error"),
    MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
	INVALID_JSON("invalid json please check json format");
   
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
