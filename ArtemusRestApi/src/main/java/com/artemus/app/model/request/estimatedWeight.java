package com.artemus.app.model.request;

public class estimatedWeight {

	private int value;
	private String unit;
	public int getValue() {
	if(value==0) {
			return 0;
		}
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getUnit() {
		if(unit==null) {
			return "";
		}
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
}
