package com.artemus.app.model.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotBlank;

import com.github.reinert.jjschema.Attributes;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

public class PortDetails {


	@Schema(description = "If the port call is a loading port, indicates whether or not this is the last loading port for the voyage. Valid values are “true” and “false”.",required = true)
	@Attributes(required = false, description = "If the port call is a loading port, indicates whether or not this is the last loading port for the voyage.  Valid values are “true” and “false”.")
	private Boolean lastLoadPort;

	@Schema(description = "The date on which the vessel will leave this location.Date format is YYYY-MM-DD.",required = true)
	@Attributes(required = false, description = "The date on which the vessel will leave this location.Date format is YYYY-MM-DD.")
	private String sailingDate;

	@Schema(description = "The date on which the vessel will arrive at this location.",required = true)
	@Attributes(required = true, description = "The date on which the vessel will arrive at this location.")
	@NotBlank(message = "arrivalDate : cannot be blank")
	private String arrivalDate;

	@Schema(description = "Indicates whether or not cargo will be loaded at this port.  Valid values are “true” and “false”.",required = true)
	@Attributes(required = true, description = "Indicates whether or not cargo will be loaded at this port.  Valid values are “true” and “false”.")
	private Boolean load;

	@Schema(description = "Indicates whether or not cargo will be discharged at this port.  Valid values are “true” and “false”.",required = true)
	@Attributes(required = false, description = "Indicates whether or not cargo will be discharged at this port.  Valid values are “true” and “false”.")
	private Boolean discharge;

	@Schema(description = "",required = false,example=" ")
	@Attributes(required = false, description = "")
	private String terminal;

	@Schema(description = "",required = true)
	@NotNull(message = "location : cannot be blank")
	private Location location;

	@XmlTransient
	@Hidden
	int locationIndex;
	
	public Boolean getLastLoadPort() {
		if (lastLoadPort == null)
			lastLoadPort = false;
		return lastLoadPort;
	}

	public void setLastLoadPort(Boolean lastLoadPort) {
		this.lastLoadPort = lastLoadPort;
	}

	public String getSailingDate() {
		return sailingDate;
	}

	public void setSailingDate(String sailingDate) {
		this.sailingDate = sailingDate;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Boolean getLoad() {
		if (load == null)
			load = false;
		return load;
	}

	public void setLoad(Boolean load) {
		this.load = load;
	}

	public Boolean getDischarge() {
		if (discharge == null)
			discharge = false;
		return discharge;
	}

	public void setDischarge(Boolean discharge) {
		this.discharge = discharge;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getLocationIndex() {
		return locationIndex;
	}

	public void setLocationIndex(int locationIndex) {
		this.locationIndex = locationIndex;
	}

	@Override
	public String toString() {
		return "PortDetails [locationIndex=" + locationIndex + ", lastLoadPort=" + lastLoadPort + ", sailingDate="
				+ sailingDate + ", arrivalDate=" + arrivalDate + ", load=" + load + ", discharge=" + discharge
				+ ", terminal=" + terminal + ", location=" + location + "]";
	}

}
