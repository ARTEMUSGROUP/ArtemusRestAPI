package com.artemus.app.model.request;

import java.util.ArrayList;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotBlank;
import com.github.reinert.jjschema.Attributes;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;


@XmlRootElement
//@ApiModel(value="Voyage",description = "All details about the Voyage. ")
public class Voyage {

    @Schema(description = "The voyage number identifying this voyage.",required = true)
	@Attributes(required = true, description = "The voyage number identifying this voyage.")
	@NotBlank(message = "voyageNumber cannot be blank")
	@Size(max = 10, message = "The voyageNumber must be max 10 characters only")
	private String voyageNumber;

	@Schema(description = "The vessel name for the vessel for this voyage. It must already exist in our system.",required = true)
	@Attributes(required = true, description = "The vessel name for the vessel for this voyage.  It must already exist in our system.")
	@NotBlank(message = "vesselName cannot be blank")
	private String vesselName;

	@Schema(description = "The SCAC for the operator of this vessel.  By default the SCAC for the vessel will be used.",required = false,example=" ")
	@Attributes(required = false, description = "The SCAC for the operator of this vessel.  By default the SCAC for the vessel will be used.")
	private String scacCode;

	@Schema(description = "The number of crew members on the vessel. Not used for U.S. Customs transmissions.",required = false,example=" ")
	@Attributes(required = false, description = "The number of crew members on the vessel.  Not used for U.S. Customs transmissions.")
	private String crewMembers;

	@Schema(description = "The number of passengers on the vessel. Not used for U.S. Customs transmissions.",required = false,example=" ")
	@Attributes(required = false, description = "The number of passengers on the vessel.  Not used for U.S. Customs transmissions.")
	private String passengers;

	@Schema(description = "Canada customs report number. Not used for U.S. Customs transmissions.",required = false,example=" ")
	@Attributes(required = false, description = "Canada customs report number.  Not used for U.S. Customs transmissions.")
	private String reportNumber;

	@Schema(description = "Defines a starting or stopping point for a voyage.",required = true)
	@Attributes(required = true, description = "Defines a starting or stopping point for a voyage.  ")
	@NotNull(message = "PortDetails cannot be null")
	private ArrayList<PortDetails> PortDetails;

	// -----------------------------------
	@XmlTransient
	@Hidden
	private int voyageId;
	@XmlTransient
	@Hidden
	private int vesselId;
	@XmlTransient
	@Hidden
	private String vesselScacCode;

	public int getVoyageId() {
		return voyageId;
	}

	public void setVoyageId(int voyageId) {
		this.voyageId = voyageId;
	}

	public int getVesselId() {
		return vesselId;
	}

	public void setVesselId(int vesselId) {
		this.vesselId = vesselId;
	}

	public String getVoyageNumber() {
		return voyageNumber.trim();
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

	public String getScacCode() {
		return scacCode;
	}

	public void setScacCode(String scacCode) {
		this.scacCode = scacCode;
	}

	public String getCrewMembers() {
		return crewMembers;
	}

	public void setCrewMembers(String crewMembers) {
		this.crewMembers = crewMembers;
	}

	public String getPassengers() {
		return passengers;
	}

	public void setPassengers(String passengers) {
		this.passengers = passengers;
	}

	public String getReportNumber() {
		return reportNumber;
	}

	public void setReportNumber(String reportNumber) {
		this.reportNumber = reportNumber;
	}

	public ArrayList<PortDetails> getPortDetails() {
		return PortDetails;
	}

	public void setPortDetails(ArrayList<PortDetails> portDetails) {
		PortDetails = portDetails;
	}

	public String getVesselScacCode() {
		return vesselScacCode;
	}

	public void setVesselScacCode(String vesselScacCode) {
		this.vesselScacCode = vesselScacCode;
	}

	@Override
	public String toString() {
		return "Voyage [voyageNumber=" + voyageNumber + ", vesselName=" + vesselName + ", scacCode=" + scacCode
				+ ", crewMembers=" + crewMembers + ", passengers=" + passengers + ", reportNumber=" + reportNumber
				+ ", PortDetails=" + PortDetails + ", voyageId=" + voyageId + ", vesselId=" + vesselId
				+ ", vesselScacCode=" + vesselScacCode + "]";
	}

}
