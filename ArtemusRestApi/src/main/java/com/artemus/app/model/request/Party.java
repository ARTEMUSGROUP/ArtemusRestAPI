package com.artemus.app.model.request;

public class Party {
	private String name;
	private AddressInfo addressInfo;

	// -----------------------
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
			objPartyMessage = objPartyMessage + " addressInfo:{" + addressInfoMessage + "}";
		return objPartyMessage;
	}
}
