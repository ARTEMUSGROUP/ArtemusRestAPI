package com.artemus.app.model.request;

import javax.xml.bind.annotation.XmlTransient;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;


public class Party {
	@Schema(description = "Name of the Party",required = true,example="string")
	private String name;
	@Schema(description = "Address Information of the Party",required = true)
	private AddressInfo addressInfo;

	// -----------------------
	@XmlTransient
	@Hidden
	private int customerId;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		if (name == null)
			return name;
		else
			return name.toUpperCase();
	}

	public void setName(String name) {
		this.name = name;
	}

	public AddressInfo getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(AddressInfo addressInfo) {
		this.addressInfo = addressInfo;
	}

	public String validateParty() {
		String objPartyMessage = "";
		if (name == null || name.isEmpty())
			objPartyMessage = " name :, ";
		String addressInfoMessage = addressInfo.validateAddressInfo();
		if (addressInfoMessage.length() > 0)
			objPartyMessage = objPartyMessage + " addressInfo:{" + addressInfoMessage +" for Party "+name+ "}";
		return objPartyMessage;
	}
}
