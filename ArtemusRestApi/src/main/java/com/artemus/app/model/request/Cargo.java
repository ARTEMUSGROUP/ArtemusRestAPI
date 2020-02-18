package com.artemus.app.model.request;

public class Cargo {

	private String descriptionsOfGoods;
	private String harmonizeCode;
	private String hazardCode;
	private String manufacturer;
	private String country;

	public String getDescriptionsOfGoods() {
		return descriptionsOfGoods;
	}

	public void setDescriptionsOfGoods(String descriptionsOfGoods) {
		this.descriptionsOfGoods = descriptionsOfGoods;
	}

	public String getHarmonizeCode() {
		return harmonizeCode;
	}

	public void setHarmonizeCode(String harmonizeCode) {
		this.harmonizeCode = harmonizeCode;
	}

	public String getHazardCode() {
		return hazardCode;
	}

	public void setHazardCode(String hazardCode) {
		this.hazardCode = hazardCode;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
