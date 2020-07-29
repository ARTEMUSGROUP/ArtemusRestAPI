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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlRootElement
@ApiModel(value = "Bill Header", description = "All details about the Bill Header. ")
public class BillHeader {
	@ApiModelProperty(value = "The bill of lading number. Do not prepend the BL issuer’s SCAC. We will do that when the bill is transmitted.", required = true)
	@Attributes(required = true, description = "The billOfLading.")
	@NotEmpty(message = "billOfLading cannot be blank")
	@Size(max = 20, message = "The billOfLading must be max 20 letters only")
	private String billOfLading;

	@Attributes(required = true, description = "The billType.")
	@ApiModelProperty(value = "billType for Bill.The billType must be max 8 letters only", required = true)
	@NotEmpty(message = "billType cannot be blank")
	@Size(max = 8, message = "The billType must be max 8 letters only")
	private String billType;
	@ApiModelProperty(value = "House Bill Scac.The House Bill Scac must be 4 letters only.", required = false, example = "DEMO")
	private String hblScac;
	@ApiModelProperty(value = "Indicates whether or not this is an NVO’s house bill, and the type of bill. Accepted values: non NVO, automated NVO, non automated NVO", required = true, example = "non NVO")
	private String nvoType;
	@ApiModelProperty(value = "The type of bill being transmitted. Accepted values include: Master,House", required = false, example = " ")
	private String nvoBill;
	@ApiModelProperty(value = "In the case of an NVO’s house bill, the SCAC of the master carrier.masterBillScac must be max 4 letters only. ", required = false, example = " ")
	private String masterBillScac;
	// private String scacBill;
	@ApiModelProperty(value = "In the case of an NVO’s house bill, this indicates the BL number of the master carrier’s bill", required = false, example = " ")
	private String masterBill;

	// Shipping Info
	@ApiModelProperty(value = "The information related to Shipper", required = true)
	private Party shipper;
	@ApiModelProperty(value = "The information related to BookingParty", required = false)
	private Party bookingParty;
	@ApiModelProperty(value = "The information related to Seller", required = false)
	private Party seller;
	@ApiModelProperty(value = "The information related to Consolidator", required = false)
	private Party consolidator;
	@ApiModelProperty(value = "The information related to Stuffer", required = false)
	private Party stuffer;

	// Recipient Info
	@Attributes(required = true, description = "The consignee.")
	@ApiModelProperty(value = "The information related to Consignee", required = true)
	@NotNull(message = "consignee cannot be blank")
	private Party consignee;
	@ApiModelProperty(value = "The information related to Notify", required = true)
	private Party notify;
	@ApiModelProperty(value = "The information related to Importer", required = false)
	private Party importer;
	@ApiModelProperty(value = "The information related to Buyer", required = false)
	private Party buyer;
	@ApiModelProperty(value = "The information related to ShipTo", required = false)
	private Party shipTo;

	// Notify Parties
	@ApiModelProperty(value = "Array of carrier code's(scac) related to notify parties", required = false)
	private ArrayList<String> notifyParties;

	// Vessel Schedule
	@Attributes(required = true, description = "The vesselSchedule.")
	@ApiModelProperty(value = "The information related to vessel", required = true)
	@NotNull(message = "vesselSchedule cannot be blank")
	private VesselSchedule vesselSchedule;

	@ApiModelProperty(value = "The information related to Equipment", required = true)
	private ArrayList<Equipment> equipments;

	// Bill Update
	// private boolean isBillUpdate;
	@XmlTransient
	private String loginScac;
	// --------------------------
	@XmlTransient
	public int billLadingId;

	// ISF
	@ApiModelProperty(value = "Type of Transmission Type.Accepted values are:CT,FT,FR. By default CT will be selected. "
			+ "CT-Complete transaction, FR-Flexible Range, FT-Flexible Timing", required = false, example = "CT")
	@Size(max = 2, min = 2, message = "The transmissionType must be max of 2 characters only")
	private String transmissionType;
	@ApiModelProperty(value = "Type of Shipment.Accepted values include: 01,02,03,04,05,06,07,08,09,10,11."
			+ "By default 01 will be selected.The following values corresponds as below: 01-Standard or regular filings,"
			+ " 02-To Order Shipments, 03-Household Goods / Personal Effects (HHG / PE), 04-Military Government, 05-Diplomatic Shipment,"
			+ " 06-Carnet, 07-US Goods Returned, 08-FTZ Shipments, 09-International Mail Shipments, 10-Outer Continental Shelf Shipments,"
			+ " 11-Informal.", required = false, example = "01")
	@Size(max = 2, message = "The shipmentType must be max of 2 digits only")
	private String shipmentType;
	@ApiModelProperty(value = "Carnet Information", required = false)
	private Carnet carnet;
	@ApiModelProperty(value = "Informal Information", required = false)
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

	// @JsonProperty("billType")
	// @Schema(required = true, description = "The type of bill being transmitted.
	// Accepted values include: way bill, empty, Original")
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

	// @JsonProperty("nvoBill")
	// @Schema(description = "The type of bill being transmitted. Accepted values
	// include: Master,House")
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
			return "";

		return masterBill.toUpperCase();
	}

	public String getMasterBillScac() {
		if (masterBillScac == null)
			return "";

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
		if (transmissionType == null) {
			return "CT";
		}
		return transmissionType.trim();
	}

	public void setTransmissionType(String transmissionType) {
		this.transmissionType = transmissionType;
	}

	public String getShipmentType() {
		if (shipmentType == null) {
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
