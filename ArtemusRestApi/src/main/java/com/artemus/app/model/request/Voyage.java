package com.artemus.app.model.request;

import java.util.ArrayList;

import com.github.reinert.jjschema.Attributes;

@Attributes(title = "Voyage", description = "Defines a voyage.")
public class Voyage {
	
	@Attributes(required = true, description = "The voyage number identifying this voyage.")
	private String voyage_num;
	
	@Attributes(required = true, description = "The vessel name for the vessel for this voyage.  It must already exist in our system.")
	private String vesselName;
	
	@Attributes(required = false, description = "The SCAC for the operator of this vessel.  By default the SCAC for the vessel will be used.")
	private String scacCode;
	
	@Attributes(required = false, description = "The number of crew members on the vessel.  Not used for U.S. Customs transmissions.")
	private String crewMembers;
	
	@Attributes(required = false, description = "The number of passengers on the vessel.  Not used for U.S. Customs transmissions.")
	private String passengers;
	
	@Attributes(required = false, description = "Canada customs report number.  Not used for U.S. Customs transmissions.")
	private String reportNumber;
	
	@Attributes(required = false, description = "Defines a starting or stopping point for a voyage.  ")
	private ArrayList<PortDetails> PortDetails;
	
	@Attributes(required = false, description = " Defines a location for this bill.  ")
	private ArrayList<Location> Location;

	public String getVoyage_num() {
		return voyage_num;
	}

	public void setVoyage_num(String voyage_num) {
		this.voyage_num = voyage_num;
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

	public ArrayList<Location> getLocation() {
		return Location;
	}

	public void setLocation(ArrayList<Location> location) {
		Location = location;
	}
	
}
