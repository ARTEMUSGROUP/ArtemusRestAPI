package com.artemus.app.model.request;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BillHeader {
	private String billOfLading;
	private String billType;
	private String hblScac;
	private String nvoType;
	private String nvoBill;
	private String scacBill;
	private String masterBill;

	// Shipping Info
	private Party shipper;
	private Party bookingParty;
	private Party seller;
	private Party consolidator;
	private Party stuffer;

	// Recipient Info
	private Party consignee;
	private Party notify;
	private Party importer;
	private Party buyer;
	private Party shipTo;

	// Notify Parties
	private ArrayList<String> notifyParties;

	// Vessel Schedule
	private VesselSchedule vesselSchedule;

	private ArrayList<Equipment> equipments;

	// Bill Update
	private boolean isBillUpdate;

	private String loginScac;
	// --------------------------
	public int billLadingId;

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
			return hblScac;
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

	public String getNvoBill() {
		if (nvoBill == null)
			return nvoBill;
		else
			return nvoBill.toUpperCase();
	}

	public void setNvoBill(String nvoBill) {
		this.nvoBill = nvoBill;
	}

	public String getScacBill() {
		if (scacBill == null)
			return scacBill;
		else
			return scacBill.toUpperCase();
	}

	public void setScacBill(String scacBill) {
		this.scacBill = scacBill;
	}

	public String getMasterBill() {
		if (masterBill == null)
			return masterBill;
		else
			return masterBill.toUpperCase();
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
		return notifyParties;
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

	public boolean isBillUpdate() {
		return isBillUpdate;
	}

	public void setBillUpdate(boolean isBillUpdate) {
		this.isBillUpdate = isBillUpdate;
	}

	public int getBillLadingId() {
		return billLadingId;
	}

	public void setBillLadingId(int billLadingId) {
		this.billLadingId = billLadingId;
	}

	@Override
	public String toString() {
		return "BillHeader [billOfLading=" + billOfLading + ", billType=" + billType + ", hblScac=" + hblScac
				+ ", nvoType=" + nvoType + ", nvoBill=" + nvoBill + ", scacBill=" + scacBill + ", masterBill="
				+ masterBill + ", shipper=" + shipper + ", bookingParty=" + bookingParty + ", seller=" + seller
				+ ", consolidator=" + consolidator + ", stuffer=" + stuffer + ", consignee=" + consignee + ", notify="
				+ notify + ", importer=" + importer + ", buyer=" + buyer + ", shipTo=" + shipTo + ", notifyParties="
				+ notifyParties + ", vesselSchedule=" + vesselSchedule + ", equipments=" + equipments
				+ ", isBillUpdate=" + isBillUpdate + ", loginScac=" + loginScac + ", billLadingId=" + billLadingId
				+ "]";
	}

}
