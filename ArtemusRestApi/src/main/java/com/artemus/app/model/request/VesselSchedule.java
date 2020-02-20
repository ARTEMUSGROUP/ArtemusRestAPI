package com.artemus.app.model.request;

public class VesselSchedule {

	private String vesselName;
	private String voyageNumber;

	private String countryOfOrigin;
	private String placeOfReceipt;
	private String portOfLoading;
	private String portOfDischarge;
	private String placeOfDelivery;
	private String canadaCustomsOffice;
	private String moveType;

	// -------------------------------
	private int vesselId;
	private int voyageId;
	private String lloydsCode;

	public String getVesselName() {
		return vesselName;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public String getVoyageNumber() {
		return voyageNumber;
	}

	public void setVoyageNumber(String voyageNumber) {
		this.voyageNumber = voyageNumber;
	}

	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}

	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}

	public String getPlaceOfReceipt() {
		return placeOfReceipt;
	}

	public void setPlaceOfReceipt(String placeOfReceipt) {
		this.placeOfReceipt = placeOfReceipt;
	}

	public String getPortOfLoading() {
		return portOfLoading;
	}

	public void setPortOfLoading(String portOfLoading) {
		this.portOfLoading = portOfLoading;
	}

	public String getPortOfDischarge() {
		return portOfDischarge;
	}

	public void setPortOfDischarge(String portOfDischarge) {
		this.portOfDischarge = portOfDischarge;
	}

	public String getPlaceOfDelivery() {
		return placeOfDelivery;
	}

	public void setPlaceOfDelivery(String placeOfDelivery) {
		this.placeOfDelivery = placeOfDelivery;
	}

	public String getCanadaCustomsOffice() {
		return canadaCustomsOffice;
	}

	public void setCanadaCustomsOffice(String canadaCustomsOffice) {
		this.canadaCustomsOffice = canadaCustomsOffice;
	}

	public String getMoveType() {
		return moveType;
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
		return lloydsCode;
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
//		if (placeOfReceipt == null || placeOfReceipt.isEmpty()) {
//			if (objVesselMessage.length() > 0) {
//				objVesselMessage.append(",");
//			}
//			objVesselMessage.append("placeOfReceipt");
//		}
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
//		if (placeOfDelivery == null || placeOfDelivery.isEmpty()) {
//			if (objVesselMessage.length() > 0) {
//				objVesselMessage.append(",");
//			}
//			objVesselMessage.append("placeOfDelivery");
//		}
				
		return objVesselMessage;
	}

}
