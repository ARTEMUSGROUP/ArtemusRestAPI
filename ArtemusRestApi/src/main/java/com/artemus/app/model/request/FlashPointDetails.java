package com.artemus.app.model.request;

import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

public class FlashPointDetails {

	@ApiModelProperty(value = "The Flash Point value",required = false)
	private double flashPoint;
	
	@Size(max = 1, message = "The Flash unit must be max of 1 unit length.C or F.")
	@ApiModelProperty(value = "The Flash Unit.Accepted Values are: C,F",required = false)
	private String flashUnit;

	

	public double getFlashPoint() {
		return flashPoint;
	}

	public void setFlashPoint(double flashPoint) {
		this.flashPoint = flashPoint;
	}

	public String getFlashUnit() {
		return flashUnit;
	}

	public void setFlashUnit(String flashUnit) {
		this.flashUnit = flashUnit;
	}
	
	
	
}
