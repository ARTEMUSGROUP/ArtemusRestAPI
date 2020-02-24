package com.artemus.app.model.request;

public class Cargo {

	private String descriptionsOfGoods;
	private String harmonizeCode;
	private String hazardCode;
	private String manufacturer;
	private String country;

	public String getDescriptionsOfGoods() {
		if (descriptionsOfGoods==null)
			descriptionsOfGoods="";
		return descriptionsOfGoods;
	}

	public void setDescriptionsOfGoods(String descriptionsOfGoods) {
		this.descriptionsOfGoods = descriptionsOfGoods;
	}

	public String getHarmonizeCode() {
		if(harmonizeCode==null)
			harmonizeCode="";
		return harmonizeCode;
	}

	public void setHarmonizeCode(String harmonizeCode) {
		this.harmonizeCode = harmonizeCode;
	}

	public String getHazardCode() {
		if(hazardCode==null)
			hazardCode="";
		return hazardCode;
	}

	public void setHazardCode(String hazardCode) {
		this.hazardCode = hazardCode;
	}

	public String getManufacturer() {
		if(manufacturer==null)
			manufacturer="";
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getCountry() {
		if(country==null)
			country="";
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
