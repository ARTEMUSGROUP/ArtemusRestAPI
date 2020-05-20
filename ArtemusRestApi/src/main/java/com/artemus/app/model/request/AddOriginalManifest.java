package com.artemus.app.model.request;

import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotBlank;

import com.github.reinert.jjschema.Attributes;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

public class AddOriginalManifest {

	@Schema(description="The voyage number identifying this voyage.",required=true)
	@Attributes(required = true, description = "The voyage number identifying this voyage.")
	@NotBlank(message = "voyageNumber cannot be blank")
	private String voyageNumber;

	@Schema(description="The vessel name for the vessel for this voyage. It must already exist in our system.",required=true)
	@Attributes(required = true, description = "The vessel name for the vessel for this voyage.  It must already exist in our system.")
	@NotBlank(message = "vesselName cannot be blank")
	private String vesselName;

	@Schema(description="Load Port Location Name.Mandatory if loadPortCustomCode is not given.",required=false)
	@Attributes(required = false, description = "The name of this location.  If the location already exists in our system, this and an index are the only required attributes.")
	private String loadPortLocation;

	@Schema(description="Discharge Port Location Name.Mandatory if dischargePortCustomCode is not given.",required=false)
	@Attributes(required = false, description = "The name of this location.  If the location already exists in our system, this and an index are the only required attributes.")
	private String dischargePortLocation;

	@Schema(description="Custom Code of Load Port Location.Mandatory if loadPortLocation is not given.",required=false)
	@Attributes(required = false, description = "“Schedule K” port code for this location.")
	private String loadPortCustomCode;

	//@ApiModelProperty(value = "Custom Code of Discharge Port Location.Mandatory if dischargePortLocation is not given.", required = false)
	@Schema(description="Custom Code of Discharge Port Location.Mandatory if dischargePortLocation is not given.",required=false)
	@Attributes(required = false, description = "“Schedule K” port code for this location.")
	private String dischargePortCustomCode;

	// ---------------------

	@Hidden
	@XmlTransient
	private String loginScac;
	@Hidden
	@XmlTransient
	private int vesselId;
	@Hidden
	@XmlTransient
	private int voyageId;

	public String getVoyageNumber() {
		return voyageNumber;
	}

	public void setVoyageNumber(String voyageNumber) {
		this.voyageNumber = voyageNumber;
	}

	public String getVesselName() {
		return vesselName;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public String getLoadPortLocation() {
		if (loadPortLocation == null) {
			return "";
		}
		return loadPortLocation;
	}

	public void setLoadPortLocation(String loadPortLocation) {
		this.loadPortLocation = loadPortLocation;
	}

	public String getDischargePortLocation() {
		if (dischargePortLocation == null) {
			return "";
		}
		return dischargePortLocation;
	}

	public void setDischargePortLocation(String dischargePortLocation) {
		this.dischargePortLocation = dischargePortLocation;
	}

	public String getLoadPortCustomCode() {
		if (loadPortCustomCode == null) {
			return "";
		}
		return loadPortCustomCode;
	}

	public void setLoadPortCustomCode(String loadPortCustomCode) {
		this.loadPortCustomCode = loadPortCustomCode;
	}

	public String getDischargePortCustomCode() {
		if (dischargePortCustomCode == null) {
			return "";
		}
		return dischargePortCustomCode;
	}

	public void setDischargePortCustomCode(String dischargePortCustomCode) {
		this.dischargePortCustomCode = dischargePortCustomCode;
	}

	public String getLoginScac() {
		return loginScac;
	}

	public void setLoginScac(String loginScac) {
		this.loginScac = loginScac;
	}

	public int getVesselId() {
		return vesselId;
	}

	public void setVesselId(int vesselId) {
		this.vesselId = vesselId;
	}

	public int getVoyageId() {
		return voyageId;
	}

	public void setVoyageId(int voyageId) {
		this.voyageId = voyageId;
	}

}
