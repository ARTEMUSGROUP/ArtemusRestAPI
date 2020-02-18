package com.artemus.app.model.request;

public class VesselSchedule {

	private String lloydsCode;
	private String voyageNum;

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

	public String getVoyageNum() {
		return voyageNum;
	}

	public void setVoyageNum(String voyageNum) {
		this.voyageNum = voyageNum;
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

}
