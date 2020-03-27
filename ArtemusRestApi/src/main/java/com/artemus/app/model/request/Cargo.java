package com.artemus.app.model.request;


public class Cargo {

	private String descriptionsOfGoods;
	private String harmonizeCode;
	private String hazardCode;
	//private String manufacturer;
	private String country;
	private Party manufacturer;

	public String getDescriptionsOfGoods() {
		if (descriptionsOfGoods==null)
			descriptionsOfGoods="";
		return descriptionsOfGoods.toUpperCase();
	}

	public void setDescriptionsOfGoods(String descriptionsOfGoods) {
		this.descriptionsOfGoods = descriptionsOfGoods;
	}

	public String getHarmonizeCode() {
		if(harmonizeCode==null)
			harmonizeCode="";
		return harmonizeCode.toUpperCase();
	}

	public void setHarmonizeCode(String harmonizeCode) {
		this.harmonizeCode = harmonizeCode;
	}

	public String getHazardCode() {
		if(hazardCode==null)
			hazardCode="";
		return hazardCode.toUpperCase();
	}

	public void setHazardCode(String hazardCode) {
		this.hazardCode = hazardCode;
	}

	public Party getManufacturer() {
		
		return manufacturer;
	}

	public void setManufacturer(Party manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getCountry() {
		if(country==null)
			country="";
		return country.toUpperCase();
	}

	public void setCountry(String country) {
		this.country = country;
	}

	
	
	
}
