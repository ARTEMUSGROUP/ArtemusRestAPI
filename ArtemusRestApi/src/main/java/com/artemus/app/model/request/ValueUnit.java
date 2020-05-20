package com.artemus.app.model.request;

import io.swagger.v3.oas.annotations.media.Schema;

public class ValueUnit {
	@Schema(description = "Value",required = false)
	private double value;
	@Schema(description = "Unit",required = false,example=" ")
	private String unit;

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getUnit() {
		if (unit == null) {
			unit = "";
		}
		return unit.toUpperCase();
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public StringBuffer validateValueUnit() {
		StringBuffer errorStr= new StringBuffer();
		if(value<=0) {
			errorStr.append(" value: must be greater than 0 ");
		}
		return errorStr;
	}

}
