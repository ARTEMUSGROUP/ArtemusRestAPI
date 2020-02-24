package com.artemus.app.model.request;

import com.github.reinert.jjschema.Attributes;

public class Location {

	@Attributes(required = true, description = "The name of this location.  If the location already exists in our system, this and an index are the only required attributes.")
	private String location;
	
	@Attributes(required = false, description = "The location’s three letter country code, as defined by ISO 3166-1 alpha-3.  If this location does not already exist in our database, this field is required.T")
	private String country;
	
	@Attributes(required = false, description = "The providence for this location.  For U.S. locations, use the two letter state abbreviation.")
	private String providence;
	
	@Attributes(required = false, description = "This can be either “inland” or “marine”, indicating whether this location is a sea port.")
	private String locationType;
	
	@Attributes(required = false, description = "Canada customs office code for this location.  Only valid for Canada locations.")
	private String canadaCustomCode;
	
	@Attributes(required = false, description = "United Nations location code (UN/LOCCODE) for this location.")
	private String unlocode;
	
	@Attributes(required = false, description = "“Schedule K” port code for this location.")
	private String CustomCode;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvidence() {
		return providence;
	}

	public void setProvidence(String providence) {
		this.providence = providence;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public String getCanadaCustomCode() {
		return canadaCustomCode;
	}

	public void setCanadaCustomCode(String canadaCustomCode) {
		this.canadaCustomCode = canadaCustomCode;
	}

	public String getUnlocode() {
		return unlocode;
	}

	public void setUnlocode(String unlocode) {
		this.unlocode = unlocode;
	}

	public String getCustomCode() {
		return CustomCode;
	}

	public void setCustomCode(String customCode) {
		CustomCode = customCode;
	}
	
}
