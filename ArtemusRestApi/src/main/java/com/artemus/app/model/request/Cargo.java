package com.artemus.app.model.request;

import io.swagger.annotations.ApiModelProperty;

public class Cargo {

	@ApiModelProperty(value = "The description of good for this cargo",required = false)
	private String descriptionsOfGoods;
	@ApiModelProperty(value = "The harmonized tariff code for this cargo.",required = false)
	private String harmonizeCode;
	@ApiModelProperty(value = "The hazard code for this cargo.",required = false)
	private String hazardCode;
	//private String manufacturer;
	@ApiModelProperty(value = "The name of the Country",required = false,example=" ")
	private String country;
	@ApiModelProperty(value = "Mandatory for ISF-10.If the customer exists in the AMS system then only provide the name else for "
			+ "creating new manufacturer customer provide addressInfo as well",required = false,example=" ")
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
