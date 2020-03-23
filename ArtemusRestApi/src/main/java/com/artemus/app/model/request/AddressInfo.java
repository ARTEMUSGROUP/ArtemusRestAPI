package com.artemus.app.model.request;

import java.sql.Date;

public class AddressInfo {
	private String addressType;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String country;
	private String state;
	private String zipCode;
	private String phoneNo;
	private String faxNo;
	private String entityType;
	private String entityNumber;
	// -------------------
	private int addressId;

	private String createdUser;
	private String createdDate;
	private String dob;
	private String countryOfIssuance;

	public String getEntityType() {
		if (entityType == null)
			return "";
		else
			return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getEntityNumber() {

			return entityNumber;
	}

	public void setEntityNumber(String entityNumber) {
		this.entityNumber = entityNumber;
	}

	public String getCreatedUser() {
		if (createdUser == null)
			return createdUser;
		else
			return createdUser.toUpperCase();
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getCreatedDate() {
		if (createdDate == null)
			return createdDate;
		else
			return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
  
	  public String getDob() { 
		  if (dob == null) 
			  return ""; 
		  else 
			  return dob; 
		  }
	  
	  public void setDob(String dob) { 
		  this.dob = dob; 
		  }
	 

	public String getCountryOfIssuance() {
		if (countryOfIssuance == null)
			return "";
		else
			return countryOfIssuance.toUpperCase();
	}

	public void setCountryOfIssuance(String countryOfIssuance) {
		this.countryOfIssuance = countryOfIssuance;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getAddressType() {
		if (addressType == null)
			return addressType;
		else
			return addressType.toUpperCase();
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getAddressLine1() {
		if (addressLine1 == null)
			return addressLine1;
		else
			return addressLine1.toUpperCase();
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		if (addressLine2 == null)
			return addressLine2;
		else
			return addressLine2.toUpperCase();
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		if (city == null)
			return city;
		else
			return city.toUpperCase();
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		if (country == null)
			return "";
		else
			return country.toUpperCase();
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		if (state == null)
			return state;
		else
			return state.toUpperCase();
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		if (zipCode == null)
			return zipCode;
		else
			return zipCode.toUpperCase();
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhoneNo() {
		if (phoneNo == null || phoneNo.isEmpty()) {
			phoneNo = "";
			return phoneNo;
		}

		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getFaxNo() {
		if (faxNo == null)
			return faxNo;
		else
			return faxNo.toUpperCase();
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String validateAddressInfo() {
		String objAddressInfoMessage = "";
		if (addressLine1 == null || addressLine1.isEmpty())
			objAddressInfoMessage = " addressLine1 ";
		return objAddressInfoMessage;
	}

}
