package com.artemus.app.model.request;


public class Carnet {

	
	private String carnetNumber;

	private String carnetCountry;
	
	
	
	public Carnet() {
		super();
	}
	public String getCarnetNumber() {
		if(carnetNumber!=null || !carnetNumber.isEmpty()) {
			return carnetNumber;
		}else {
			return "";
		}
	}
	public void setCarnetNumber(String carnetNumber) {
		this.carnetNumber = carnetNumber;
	}
	public String getCarnetCountry() {
		if(carnetCountry==null || carnetCountry.isEmpty()) {
			return "";
		}else {
			return carnetCountry;
		}
	}
	public void setCarnetCountry(String carnetCountry) {
		this.carnetCountry = carnetCountry;
	}
	
	
	
}
