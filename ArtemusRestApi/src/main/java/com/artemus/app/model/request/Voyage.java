package com.artemus.app.model.request;

import java.util.ArrayList;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import com.github.reinert.jjschema.Attributes;


@Attributes(title = "Voyage", description = "Defines a voyage.")
public class Voyage {

	@Attributes(required = true, description = "The voyage number identifying this voyage.")
	@NotBlank(message = "voyageNumber cannot be blank")
	private String voyageNumber;

	@Attributes(required = true, description = "The vessel name for the vessel for this voyage.  It must already exist in our system.")
	@NotBlank(message = "vesselName cannot be blank")
	private String vesselName;

	@Attributes(required = false, description = "The SCAC for the operator of this vessel.  By default the SCAC for the vessel will be used.")
	private String scacCode;

	@Attributes(required = false, description = "The number of crew members on the vessel.  Not used for U.S. Customs transmissions.")
	private String crewMembers;

	@Attributes(required = false, description = "The number of passengers on the vessel.  Not used for U.S. Customs transmissions.")
	private String passengers;

	@Attributes(required = false, description = "Canada customs report number.  Not used for U.S. Customs transmissions.")
	private String reportNumber;

	@Attributes(required = true, description = "Defines a starting or stopping point for a voyage.  ")
	@NotNull(message = "PortDetails cannot be null")
	@Valid
	private ArrayList<PortDetails> PortDetails;

	@Attributes(required = true, description = " Defines a location for this bill.  ")
	@NotNull(message = "locations cannot be null")
	@Valid
	private ArrayList<Location> locations;

   // -----------------------------------
	private int voyageId;
	private int vesselId;
	
	
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

	public ArrayList<Location> getLocations() {
		return locations;
	}

	public void setLocations(ArrayList<Location> locations) {
		this.locations = locations;
	}

}
