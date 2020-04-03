package com.artemus.app.model.request;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.github.reinert.jjschema.Attributes;

public class Vessel {

	@Attributes(required = true, description = "The vessel name identifying this vessel.")
	@NotBlank(message = "vesselName cannot be blank")
	@Size(max = 23, message = "The vesselName must be 23 letters only")
	private String vesselName;

	private String vesselType;

	@Attributes(required = true, description = "The lloydsCode identifying this vessel.")
	@NotBlank(message = "lloydsCode cannot be blank")
	@Pattern(regexp = "[0-9]+", message = "The lloydsCode must be Numeric only")
	@Size(max = 7, message = "The lloydsCode must be 7 numbers only")
	private String lloydsCode;
	private String owner;

	@Attributes(required = true, description = "The CountryCode for this vessel.")
	@NotBlank(message = "countryCode cannot be blank")
	@Size(max = 2, message = "The countryCode must be 2 letters only")
	private String countryCode;

	@Attributes(required = true, description = "The usaScacCode for this vessel.")
	@NotBlank(message = "usaScacCode cannot be blank.It is Mandatory")
	@Size(max = 4, message = "The usaScacCode must be 4 letters only")
	private String usaScacCode;

	private String callSign;
	@Size(max = 4, message = "The jpnCarrierCode must be 4 letters only")
	private String jpnCarrierCode;
	@Size(max = 4, message = "The canadaCarrierCode must be 4 letters only")
	private String canadaCarrierCode;
	private String masterOfVessel;
	// -------------------------------
	private int vesselId;
	private String loginScac;

	public String getLoginScac() {
		return loginScac;
	}

	public void setLoginScac(String loginScac) {
		this.loginScac = loginScac;
	}

	public String getVesselName() {
		return vesselName.toUpperCase();
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public String getVesselType() {
		return vesselType;
	}

	public void setVesselType(String vesselType) {
		this.vesselType = vesselType;
	}

	public String getLloydsCode() {
		return lloydsCode;
	}

	public void setLloydsCode(String lloydsCode) {
		this.lloydsCode = lloydsCode;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getUsaScacCode() {
		return usaScacCode.toUpperCase();
	}

	public void setUsaScacCode(String usaScacCode) {
		this.usaScacCode = usaScacCode;
	}

	public String getCallSign() {
		if(callSign==null) {
			return "";
		}
		return callSign;
	}

	public void setCallSign(String callSign) {
		this.callSign = callSign;
	}

	public String getJpnCarrierCode() {
		if (jpnCarrierCode == null) {
			return "";
		} else {
			return jpnCarrierCode;
		}
	}

	public void setJpnCarrierCode(String jpnCarrierCode) {
		this.jpnCarrierCode = jpnCarrierCode;
	}

	public String getCanadaCarrierCode() {
		if(canadaCarrierCode==null) {
			return "";
		}
		return canadaCarrierCode;
	}

	public void setCanadaCarrierCode(String canadaCarrierCode) {
		this.canadaCarrierCode = canadaCarrierCode;
	}

	public String getMasterOfVessel() {
		if(masterOfVessel==null) {
			return "";
		}
		return masterOfVessel;
	}

	public void setMasterOfVessel(String masterOfVessel) {
		this.masterOfVessel = masterOfVessel;
	}


	public int getVesselId() {
		return vesselId;
	}

	public void setVesselId(int vesselId) {
		this.vesselId = vesselId;
	}

	@Override
	public String toString() {
		return "Vessel [vesselName=" + vesselName + ", vesselType=" + vesselType + ", lloydsCode=" + lloydsCode
				+ ", owner=" + owner + ", countryCode=" + countryCode + ", usaScacCode=" + usaScacCode + ", callSign="
				+ callSign + ", jpnCarrierCode=" + jpnCarrierCode + ", canadaCarrierCode=" + canadaCarrierCode
				+ ", masterOfVessel=" + masterOfVessel + ", vesselId=" + vesselId + ", loginScac=" + loginScac + "]";
	}



}