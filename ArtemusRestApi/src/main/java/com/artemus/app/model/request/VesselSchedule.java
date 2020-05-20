package com.artemus.app.model.request;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.github.reinert.jjschema.Attributes;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

public class VesselSchedule {

	@Schema(description = "The vessel name",required = true)
	@Attributes(required = true, description = "The vesselName")
	@NotEmpty(message = "vesselName is required")
	private String vesselName;
	
	@Schema(description = "The voyage number",required = true)
	@Attributes(required = true, description = "The voyageNumber")
	@NotEmpty(message = "voyageNumber cannot be blank")
	private String voyageNumber;

	@Schema(description = "The indicates of country Of Origin",required = true)
	private String countryOfOrigin;
	
	@Schema(description = "The place of receipt",required = false,example=" ")
	private String placeOfReceipt;
	
	@Schema(description = "Indicates the port of loading.",required = true)
	@Attributes(required = true, description = "The portOfLoading")
	@NotEmpty(message = "portOfLoading cannot be blank")
	private String portOfLoading;
	
	@Schema(description = "Indicates the port of Discharge.",required = true)
	@Attributes(required = true, description = "The portOfDischarge")
	@NotEmpty(message = "portOfDischarge cannot be blank")
	private String portOfDischarge;
	
	@Schema(description = "The place of delivery.",required = false,example=" ")
	private String placeOfDelivery;
	
	@Schema(description = "Canada customs office code for this location. Only valid for Canada locations",required = false,example=" ")
	private String canadaCustomsOffice;
	
	@Schema(description = "Type of move, sometimes referred to as type of shipment. Acceptable values:FCL/FCL,FCL/LCL,LCL/FCL,LCL/LCL,PIER/HOUSE,PIER/PIER,BBK/BBK",required = false,example=" ")
	private String moveType;

	// -------------------------------
	@XmlTransient
	@Hidden
	private int vesselId;
	@XmlTransient
	@Hidden
	private int voyageId;
	@XmlTransient
	@Hidden
	private String lloydsCode;
	@XmlTransient
	@Hidden
	private String vesselScac;

	public String getVesselName() {
		if (vesselName == null)
			return vesselName;
		else
			return vesselName.toUpperCase();
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public String getVoyageNumber() {
		if (voyageNumber == null)
			return voyageNumber;
		else
			return voyageNumber.toUpperCase();
	}

	public void setVoyageNumber(String voyageNumber) {
		this.voyageNumber = voyageNumber;
	}

	public String getCountryOfOrigin() {
		if (countryOfOrigin == null)
			return countryOfOrigin;
		else
			return countryOfOrigin.toUpperCase();
	}

	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}

	public String getPlaceOfReceipt() {
		if (placeOfReceipt == null || placeOfReceipt.isEmpty()) {
			placeOfReceipt = "";
		}
		return placeOfReceipt.toUpperCase();
	}

	public void setPlaceOfReceipt(String placeOfReceipt) {
		this.placeOfReceipt = placeOfReceipt;
	}

	public String getPortOfLoading() {
		if (portOfLoading == null)
			return portOfLoading;
		else
			return portOfLoading.toUpperCase();
	}

	public void setPortOfLoading(String portOfLoading) {
		this.portOfLoading = portOfLoading;
	}

	public String getPortOfDischarge() {
		if (portOfDischarge == null)
			return portOfDischarge;
		else
			return portOfDischarge.toUpperCase();
	}

	public void setPortOfDischarge(String portOfDischarge) {
		this.portOfDischarge = portOfDischarge;
	}

	public String getPlaceOfDelivery() {
		if (placeOfDelivery == null || placeOfDelivery.isEmpty()) {
			placeOfDelivery = "";
		}
		return placeOfDelivery.toUpperCase();
	}

	public void setPlaceOfDelivery(String placeOfDelivery) {
		this.placeOfDelivery = placeOfDelivery;
	}

	public String getCanadaCustomsOffice() {
		if (canadaCustomsOffice == null)
			return canadaCustomsOffice;
		else
			return canadaCustomsOffice.toUpperCase();
	}

	public void setCanadaCustomsOffice(String canadaCustomsOffice) {
		this.canadaCustomsOffice = canadaCustomsOffice;
	}

	public String getMoveType() {
		if (moveType == null)
			return moveType;
		else
			return moveType.toUpperCase();
	}

	public void setMoveType(String moveType) {
		this.moveType = moveType;
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

	public String getLloydsCode() {
		if (lloydsCode == null)
			return lloydsCode;
		else
			return lloydsCode.toUpperCase();
	}

	public void setLloydsCode(String lloydsCode) {
		this.lloydsCode = lloydsCode;
	}

	public StringBuffer validateVesselSchedule() {
		StringBuffer objVesselMessage = new StringBuffer();
		if (moveType == null || moveType.isEmpty()) {
			if (objVesselMessage.length() > 0) {
				objVesselMessage.append(",");
			}
			objVesselMessage.append("moveType");
		}
		if (countryOfOrigin == null || countryOfOrigin.isEmpty()) {
			if (objVesselMessage.length() > 0) {
				objVesselMessage.append(",");
			}
			objVesselMessage.append("countryOfOrigin");
		}
		if (vesselName == null || vesselName.isEmpty()) {
			if (objVesselMessage.length() > 0) {
				objVesselMessage.append(",");
			}
			objVesselMessage.append("vesselName");
		}
		if (portOfDischarge == null || portOfDischarge.isEmpty()) {
			if (objVesselMessage.length() > 0) {
				objVesselMessage.append(",");
			}
			objVesselMessage.append("portOfDischarge");
		}
		if (voyageNumber == null || voyageNumber.isEmpty()) {
			if (objVesselMessage.length() > 0) {
				objVesselMessage.append(",");
			}
			objVesselMessage.append("voyageNumber");
		}
		if (portOfLoading == null || portOfLoading.isEmpty()) {
			if (objVesselMessage.length() > 0) {
				objVesselMessage.append(",");
			}
			objVesselMessage.append("portOfLoading");
		}

		return objVesselMessage;
	}

	public String getVesselScac() {
		return vesselScac;
	}

	public void setVesselScac(String vesselScac) {
		this.vesselScac = vesselScac;
	}
	
	public StringBuffer jpvalidateVesselSchedule() {
		StringBuffer objVesselMessage = new StringBuffer();

		if (countryOfOrigin == null || countryOfOrigin.isEmpty()) {
			if (objVesselMessage.length() > 0) {
				objVesselMessage.append(",");
			}
			objVesselMessage.append("countryOfOrigin");
		}
		if (vesselName == null || vesselName.isEmpty()) {
			if (objVesselMessage.length() > 0) {
				objVesselMessage.append(",");
			}
			objVesselMessage.append("vesselName");
		}
		if (portOfDischarge == null || portOfDischarge.isEmpty()) {
			if (objVesselMessage.length() > 0) {
				objVesselMessage.append(",");
			}
			objVesselMessage.append("portOfDischarge");
		}
		if (voyageNumber == null || voyageNumber.isEmpty()) {
			if (objVesselMessage.length() > 0) {
				objVesselMessage.append(",");
			}
			objVesselMessage.append("voyageNumber");
		}
		if (portOfLoading == null || portOfLoading.isEmpty()) {
			if (objVesselMessage.length() > 0) {
				objVesselMessage.append(",");
			}
			objVesselMessage.append("portOfLoading");
		}

		return objVesselMessage;
	}

}
