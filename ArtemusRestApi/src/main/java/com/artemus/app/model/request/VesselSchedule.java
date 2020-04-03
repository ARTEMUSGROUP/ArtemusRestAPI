package com.artemus.app.model.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.github.reinert.jjschema.Attributes;

public class VesselSchedule {

	@Attributes(required = true, description = "The vesselName")
	@NotEmpty(message = "vesselName is required")
	private String vesselName;
	@Attributes(required = true, description = "The voyageNumber")
	@NotEmpty(message = "voyageNumber cannot be blank")
	private String voyageNumber;

	private String countryOfOrigin;
	private String placeOfReceipt;
	
	@Attributes(required = true, description = "The portOfLoading")
	@NotEmpty(message = "portOfLoading cannot be blank")
	private String portOfLoading;
	
	@Attributes(required = true, description = "The portOfDischarge")
	@NotEmpty(message = "portOfDischarge cannot be blank")
	private String portOfDischarge;
	private String placeOfDelivery;
	private String canadaCustomsOffice;
	private String moveType;

	// -------------------------------
	private int vesselId;
	private int voyageId;
	private String lloydsCode;
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
