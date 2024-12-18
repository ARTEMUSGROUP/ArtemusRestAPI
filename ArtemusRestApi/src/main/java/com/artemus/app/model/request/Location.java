package com.artemus.app.model.request;

import java.text.Normalizer;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.reinert.jjschema.Attributes;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;


public class Location {


	
	@Schema(description = "The name of this location. If the location already exists in our system, this are the only required attributes.",required = true)
	@JsonProperty
	//@ApiModelProperty(value = "The name of this location. If the location already exists in our system, this are the only required attributes.",required = true)
	@Attributes(required = true, description = "The name of this location.  If the location already exists in our system, this and an index are the only required attributes.")
	@NotBlank(message = "location cannot be blank")
	private String location;


	@Schema(description = "The location’s two letter country code.If this location does not already exist in our database, this field is required.",required = false)
	@Attributes(required = false, description = "The location’s three letter country code, as defined by ISO 3166-1 alpha-3.  If this location does not already exist in our database, this field is required.T")
	@Size(max = 2, message = "country must be 2 letter country code of location")
	private String country;

	@Schema(description = "The providence for this location. For U.S. locations, use the two letter state abbreviation.",required = false)
	@Attributes(required = false, description = "The providence for this location.  For U.S. locations, use the two letter state abbreviation.")
	@Size(min = 2, max = 2, message = "providence must be 2 letter state abbreviation")
	private String providence;

	@Schema(description = "This can be either “inland” or “marine”, indicating whether this location is a sea port.",required = false,example="marine")
	@Attributes(required = false, description = "This can be either “inland” or “marine”, indicating whether this location is a sea port.")
	@Pattern(regexp = "inland|marine", flags = Pattern.Flag.CASE_INSENSITIVE)
	private String locationType;

	@Schema(description = "Canada customs office code for this location. Only valid for Canada locations.",required = false,example=" ")
	@Attributes(required = false, description = "Canada customs office code for this location.  Only valid for Canada locations.")
	private String canadaCustomCode;

	@Schema(description = "United Nations location code (UN/LOCCODE) for this location.",required = false)
	@Attributes(required = false, description = "United Nations location code (UN/LOCCODE) for this location.")
	@Valid
	private String unlocode;

	@Schema(description = "“Schedule K” port code for this location.",required = false)
	@Attributes(required = false, description = "“Schedule K” port code for this location.")
	@Valid
	private String customCode;

	@XmlTransient
	@Hidden
	private int locationIndex;
	@XmlTransient
	@Hidden
	private int locationId;
	@XmlTransient
	@Hidden
	private boolean voyageCreated;
	@XmlTransient
	@Hidden
	private String holdAtLp;
	@XmlTransient
	@Hidden
	private String createdUser;
	
	@Hidden
	private boolean customForeign;

	@AssertTrue(message = " Either unlocode or customCode must required")
	@Hidden
	public boolean isValid() {
		if ((unlocode == null || unlocode.isEmpty()) && (customCode == null || customCode.isEmpty())) {
			return false;
		} else {
			return true;
		}
	}

	public int getLocationIndex() {
		return locationIndex;
	}

	public void setLocationIndex(int locationIndex) {
		this.locationIndex = locationIndex;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}


	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location =Normalizer.normalize(location, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
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
		if(unlocode==null)
			return "";
		
		return unlocode.replaceAll("//s", "");
	}

	public void setUnlocode(String unlocode) {
		this.unlocode = unlocode;
	}

	public String getCustomCode() {
		return customCode;
	}

	public void setCustomCode(String customCode) {
		this.customCode = customCode;
	}


	public String getHoldAtLp() {
		return holdAtLp;
	}

	public void setHoldAtLp(String holdAtLp) {
		this.holdAtLp = holdAtLp;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public boolean isVoyageCreated() {
		return voyageCreated;
	}

	public void setVoyageCreated(boolean voyageCreated) {
		this.voyageCreated = voyageCreated;
	}

	public boolean isCustomForeign() {
		return customForeign;
	}

	public void setCustomForeign(boolean customForeign) {
		this.customForeign = customForeign;
	}

	@Override
	public String toString() {
		return "Location [location=" + location + ", country=" + country + ", providence=" + providence
				+ ", locationType=" + locationType + ", canadaCustomCode=" + canadaCustomCode + ", unlocode=" + unlocode
				+ ", customCode=" + customCode + ", locationIndex=" + locationIndex + ", locationId=" + locationId
				+ ", voyageCreated=" + voyageCreated + ", holdAtLp=" + holdAtLp + ", createdUser=" + createdUser
				+ ", customForeign=" + customForeign + "]";
	}



	

}
