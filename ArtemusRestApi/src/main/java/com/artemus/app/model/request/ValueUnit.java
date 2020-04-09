package com.artemus.app.model.request;


public class ValueUnit {
	
	private double value;
	
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
