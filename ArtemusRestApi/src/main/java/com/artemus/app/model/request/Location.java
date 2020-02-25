package com.artemus.app.model.request;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.github.reinert.jjschema.Attributes;

public class Location {

	

	@Attributes(required = true, description = "A unique index, identifying this location within this EDI transmission.  This could be your primary key, or just a number starting from one.")
	@NotBlank(message = "location index cannot be blank")
	private int locationIndex;
	
	@Attributes(required = true, description = "The name of this location.  If the location already exists in our system, this and an index are the only required attributes.")
	@NotBlank(message = "location cannot be blank")
	private String location;
	
	@Attributes(required = false, description = "The location’s three letter country code, as defined by ISO 3166-1 alpha-3.  If this location does not already exist in our database, this field is required.T")
	@Size(min = 3, max = 3, message = "country must be 3 letter country code of location")
	private String country;
	
	@Attributes(required = false, description = "The providence for this location.  For U.S. locations, use the two letter state abbreviation.")
	@Size(min = 2, max = 2, message = "providence must be 2 letter state abbreviation")
	private String providence;
	
	@Attributes(required = false, description = "This can be either “inland” or “marine”, indicating whether this location is a sea port.")
	@Pattern(regexp = "inland|marine", flags = Pattern.Flag.CASE_INSENSITIVE)
	private String locationType;
	
	@Attributes(required = false, description = "Canada customs office code for this location.  Only valid for Canada locations.")
	private String canadaCustomCode;
	
	@Attributes(required = false, description = "United Nations location code (UN/LOCCODE) for this location.")
	private String unlocode;
	
	@Attributes(required = false, description = "“Schedule K” port code for this location.")
	private String CustomCode;

	// -------------------------------------------
	    private boolean isCustomForeign;
		
		private int locationId;
		private boolean IsVoyageCreated;
		private String HoldAtLp;
		private String CreatedUser;
		
		
		public int getLocationIndex() {
			return locationIndex;
		}

		public void setLocationIndex(int locationIndex) {
			this.locationIndex = locationIndex;
		}	
		
	public String getCreatedUser() {
			return CreatedUser;
		}

		public void setCreatedUser(String createdUser) {
			CreatedUser = createdUser;
		}

	public String getHoldAtLp() {
			return HoldAtLp;
		}

		public void setHoldAtLp(String holdAtLp) {
			HoldAtLp = holdAtLp;
		}

	public boolean isIsVoyageCreated() {
			return IsVoyageCreated;
		}

		public void setIsVoyageCreated(boolean isVoyageCreated) {
			IsVoyageCreated = isVoyageCreated;
		}

	public int getLocationId() {
			return locationId;
		}

		public void setLocationId(int locationId) {
			this.locationId = locationId;
		}

	public boolean isCustomForeign() {
		return isCustomForeign;
	}

	public void setCustomForeign(boolean isCustomForeign) {
		this.isCustomForeign = isCustomForeign;
	}

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
