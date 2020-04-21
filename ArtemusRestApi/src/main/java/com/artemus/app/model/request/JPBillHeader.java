package com.artemus.app.model.request;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotEmpty;

import com.github.reinert.jjschema.Attributes;


@XmlRootElement
//@ApiModel(value="Japan Bill Header",description = "All details about the Japan Bill Header. ")
public class JPBillHeader {
	//@ApiModelProperty(value ="The bill of lading number. Do not prepend the BL issuer’s SCAC. We will do that when the bill is transmitted.",required = true)
	@Attributes(required = true, description = "The billOfLading.")
	@NotEmpty(message = "billOfLading cannot be blank")
	@Size(max = 20, message = "The billOfLading must be max 20 letters only")
	private String billOfLading;
	
	@Attributes(required = true, description = "The billType.")
	//@ApiModelProperty(value = "billType for Bill.The billType must be max 8 letters only",required = true)
	@NotEmpty(message = "billType cannot be blank")
	@Size(max = 8, message = "The billType must be max 8 letters only")
	private String billType;
	//@ApiModelProperty(value = "House Bill Scac.The House Bill Scac must be 4 letters only.",required = false,example="DEMO")
	private String hblScac;
	//@ApiModelProperty(value = "Indicates whether or not this is an NVO’s house bill, and the type of bill. Accepted values: non NVO, automated NVO, non automated NVO",required = true,example="non NVO")
	private String nvoType;
	//@ApiModelProperty(value = "The type of bill being transmitted. Accepted values include: Master,House",required = false,example=" ")
	private String nvoBill;
	//@ApiModelProperty(value = "In the case of an NVO’s house bill, the SCAC of the master carrier",required = false,example=" ")
	private String masterBillScac;
	// private String scacBill;
	//@ApiModelProperty(value = "n the case of an NVO’s house bill, this indicates the BL number of the master carrier’s bill",required = false,example=" ")
	private String masterBill;

	// Shipping Info
	//@ApiModelProperty(value = "The information related to Shipper",required = true)
	private Party shipper;

	// Recipient Info
	@Attributes(required = true, description = "The consignee.")
	//@ApiModelProperty(value = "The information related to Consignee",required = true)
	@NotNull(message = "consignee cannot be blank")
	private Party consignee;
	//@ApiModelProperty(value = "The information related to Notify",required = true)
	private Party notify;


	// Vessel Schedule
	@Attributes(required = true, description = "The vesselSchedule.")
	//@ApiModelProperty(value = "The information related to vessel",required = true)
	@NotNull(message = "vesselSchedule cannot be blank")
	private VesselSchedule vesselSchedule;

	// Japan Equipments
	//@ApiModelProperty(value = "The information related to Equipment",required = true)
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
