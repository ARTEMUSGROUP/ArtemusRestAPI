package com.artemus.app.model.request;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.github.reinert.jjschema.Attributes;



@XmlRootElement
public class BillHeader {
	
	@Attributes(required = true, description = "The billOfLading.")
	@NotEmpty(message = "billOfLading cannot be blank")
	@Size(max = 20, message = "The billOfLading must be max 20 letters only")
	private String billOfLading;
	
	@Attributes(required = true, description = "The billType.")
	@NotEmpty(message = "billType cannot be blank")
	@Size(max = 8, message = "The billType must be max 8 letters only")
	private String billType;

	private String hblScac;
	
	private String nvoType;
	
	private String nvoBill;
	
	private String masterBillScac;
	// private String scacBill;
	
	private String masterBill;

	// Shipping Info

	private Party shipper;
	
	private Party bookingParty;
	
	private Party seller;
	
	private Party consolidator;
	
	private Party stuffer;

	// Recipient Info
	@Attributes(required = true, description = "The consignee.")
	@NotNull(message = "consignee cannot be blank")
	private Party consignee;
	
	private Party notify;
	
	private Party importer;
	
	private Party buyer;
	
	private Party shipTo;

	// Notify Parties
	
	private ArrayList<String> notifyParties;

	// Vessel Schedule
	@Attributes(required = true, description = "The vesselSchedule.")
	@NotNull(message = "vesselSchedule cannot be blank")
	private VesselSchedule vesselSchedule;

	
	private ArrayList<Equipment> equipments;

	// Bill Update
	// private boolean isBillUpdate;
	@XmlTransient
	private String loginScac;
	// --------------------------
	@XmlTransient
	public int billLadingId;

	// ISF
	
	@Size(max = 2,min=2, message = "The transmissionType must be max of 2 characters only")
	private String transmissionType;
	
	@Size(max = 2, message = "The shipmentType must be max of 2 digits only")
	private String shipmentType;
	
	private Carnet carnet;

	private Informal informal;

	// -----------------------------
	@XmlTransient
	private String isfErrorDescription;
	@XmlTransient
	private String isfType;
	
	public String getLoginScac() {
		if (loginScac == null)
			return loginScac;
		else
			return loginScac.toUpperCase();
	}

	public void setLoginScac(String loginScac) {
		this.loginScac = loginScac;
	}

	public String getBillOfLading() {
		if (billOfLading == null)
			return billOfLading;
		else
			return billOfLading.toUpperCase();
	}

	public void setBillOfLading(String billOfLading) {
		this.billOfLading = billOfLading;
	}

	 //@JsonProperty("billType")
	 //@Schema(required = true, description = "The type of bill being transmitted.  Accepted values include: way bill, empty, Original")
	public String getBillType() {
		if (billType == null)
			return billType;
		else
			return billType.toUpperCase();
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getHblScac() {
		if (hblScac == null) {
			return "";
		} else {
			return hblScac.toUpperCase();
		}
	}

	public void setHblScac(String hblScac) {
		this.hblScac = hblScac;
	}

	public String getNvoType() {
		if (nvoType == null)
			return nvoType;
		else
			return nvoType.toUpperCase();
	}

	public void setNvoType(String nvoType) {
		this.nvoType = nvoType;
	}

	//@JsonProperty("nvoBill")
	//@Schema(description = "The type of bill being transmitted.  Accepted values include: Master,House")
	public String getNvoBill() {
		if (nvoBill == null)
			return nvoBill;
		else
			return nvoBill.toUpperCase();
	}

	public void setNvoBill(String nvoBill) {
		this.nvoBill = nvoBill;
	}

	/*
	 * public String getScacBill() { if (scacBill == null) return scacBill; else
	 * return scacBill.toUpperCase(); }
	 * 
	 * public void setScacBill(String scacBill) { this.scacBill = scacBill; }
	 */

	public String getMasterBill() {
		if (masterBill == null)
			return masterBill;
		else
			return masterBill.toUpperCase();
	}

	public String getMasterBillScac() {
		return masterBillScac;
	}

	public void setMasterBillScac(String masterBillScac) {
		this.masterBillScac = masterBillScac;
	}

	public void setMasterBill(String masterBill) {
		this.masterBill = masterBill;
	}

	public Party getShipper() {
		return shipper;
	}

	public void setShipper(Party shipper) {
		this.shipper = shipper;
	}

	public Party getBookingParty() {
		return bookingParty;
	}

	public void setBookingParty(Party bookingParty) {
		this.bookingParty = bookingParty;
	}

	public Party getSeller() {
		return seller;
	}

	public void setSeller(Party seller) {
		this.seller = seller;
	}

	public Party getConsolidator() {
		return consolidator;
	}

	public void setConsolidator(Party consolidator) {
		this.consolidator = consolidator;
	}

	public Party getStuffer() {
		return stuffer;
	}

	public void setStuffer(Party stuffer) {
		this.stuffer = stuffer;
	}

	public Party getConsignee() {
		return consignee;
	}

	public void setConsignee(Party consignee) {
		this.consignee = consignee;
	}

	public Party getNotify() {
		return notify;
	}

	public void setNotify(Party notify) {
		this.notify = notify;
	}

	public Party getImporter() {
		return importer;
	}

	public void setImporter(Party importer) {
		this.importer = importer;
	}

	public Party getBuyer() {
		return buyer;
	}

	public void setBuyer(Party buyer) {
		this.buyer = buyer;
	}

	public Party getShipTo() {
		return shipTo;
	}

	public void setShipTo(Party shipTo) {
		this.shipTo = shipTo;
	}

	public ArrayList<String> getNotifyParties() {
		if (notifyParties == null) {
			return new ArrayList<String>();
		} else {
			return notifyParties;
		}
	}

	public void setNotifyParties(ArrayList<String> notifyParties) {
		this.notifyParties = notifyParties;
	}

	public VesselSchedule getVesselSchedule() {
		return vesselSchedule;
	}

	public void setVesselSchedule(VesselSchedule vesselSchedule) {
		this.vesselSchedule = vesselSchedule;
	}

	public ArrayList<Equipment> getEquipments() {
		return equipments;
	}

	public void setEquipments(ArrayList<Equipment> equipments) {
		this.equipments = equipments;
	}

//	public boolean isBillUpdate() {
//		return isBillUpdate;
//	}
//
//	public void setBillUpdate(boolean isBillUpdate) {
//		this.isBillUpdate = isBillUpdate;
//	}


	public int getBillLadingId() {
		return billLadingId;
	}

	public void setBillLadingId(int billLadingId) {
		this.billLadingId = billLadingId;
	}

	public String getTransmissionType() {
		if(transmissionType==null){
			return "CT";
		}
		return transmissionType.trim();
	}

	public void setTransmissionType(String transmissionType) {
		this.transmissionType = transmissionType;
	}

	public String getShipmentType() {
		if(shipmentType==null) {
			return "01";
		}
		return shipmentType.trim();
	}

	public void setShipmentType(String shipmentType) {
		this.shipmentType = shipmentType;
	}

	public String getIsfType() {
		return isfType;
	}

	public void setIsfType(String isfType) {
		this.isfType = isfType;
	}

	public String getIsfErrorDescription() {
		return isfErrorDescription;
	}

	public void setIsfErrorDescription(String isfErrorDescription) {
		this.isfErrorDescription = isfErrorDescription;
	}

	public Carnet getCarnet() {
		return carnet;
	}

	public void setCarnet(Carnet carnet) {
		this.carnet = carnet;
	}

	public Informal getInformal() {
		return informal;
	}

	public void setInformal(Informal informal) {
		this.informal = informal;
	}

}
