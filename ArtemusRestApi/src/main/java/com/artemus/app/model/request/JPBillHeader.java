package com.artemus.app.model.request;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotEmpty;

import com.github.reinert.jjschema.Attributes;


@XmlRootElement
public class JPBillHeader {
	
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

	// Recipient Info
	@Attributes(required = true, description = "The consignee.")
	@NotNull(message = "consignee cannot be blank")
	private Party consignee;
	private Party notify;


	// Vessel Schedule
	@Attributes(required = true, description = "The vesselSchedule.")
	@NotNull(message = "vesselSchedule cannot be blank")
	private VesselSchedule vesselSchedule;

	// Japan Equipments
	private ArrayList<JPEquipment> jpequipments;

	// Bill Update
	// private boolean isBillUpdate;
	@XmlTransient
	private String loginScac;
	// --------------------------
	@XmlTransient
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

	public VesselSchedule getVesselSchedule() {
		return vesselSchedule;
	}

	public void setVesselSchedule(VesselSchedule vesselSchedule) {
		this.vesselSchedule = vesselSchedule;
	}

//	public boolean isBillUpdate() {
//		return isBillUpdate;
//	}
//
//	public void setBillUpdate(boolean isBillUpdate) {
//		this.isBillUpdate = isBillUpdate;
//	}

	public ArrayList<JPEquipment> getJpequipments() {
		return jpequipments;
	}

	public void setJpequipments(ArrayList<JPEquipment> jpequipments) {
		this.jpequipments = jpequipments;
	}

	public int getBillLadingId() {
		return billLadingId;
	}

	public void setBillLadingId(int billLadingId) {
		this.billLadingId = billLadingId;
	}


}
