package com.artemus.app.model.request;

import java.sql.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotBlank;

import com.github.reinert.jjschema.Attributes;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

public class AddressInfo {

	
	@Schema(description="The first part of the party’s address, as it will be passed to customs.",required = true)
	@Attributes(required = true, description = "The addressLine1.")
	private String addressLine1;
	@Schema(description="The second line of the party’s address.",required = false)
	private String addressLine2;
	@Schema(description="The party’s city.",required = true)
	@Attributes(required = true, description = "The city")
	@NotNull(message = "city cannot be blank or null")
	private String city;
	@Schema(description="The party’s two letter country code, as defined by ISO 3166-1 alpha-3.",required = true)
	@Attributes(required = true, description = "The country code")
	@Size(max = 2, message = "country must be 2 letter country code")
	@NotNull(message = "country cannot be blank or null")
	private String country;
	@Schema(description="The party’s state.",required = false)
	private String state;
	@Schema(description = "The party's zipcode.",required = false)
	private String zipCode;
	@Schema(description = "The party's phone number",required = false)
	private String phoneNo;
	@Schema(description = "The party's fax number.",required = false)
	private String faxNo;
	@Schema(description = "Entity Type of Customer.For creating new Customer for Consignee or Importer entity number "
		+ "is mandatory.Accepted values will be accepted as per following names with exact syntax only: Employee ID, "
		+ "Importer/Consignee, SSN, DUNS, Passport, CBP Encrypted Consignee ID, DUNS 4, EI.",required = false)
	private String entityType;
	@Schema(description = "Entity Number of Customer.For creating new Customer for Consignee or Importer entity number is mandatory.",required = false)
	private String entityNumber;
	@Schema(description = "The party's Date of Birth.",required = false,example="1993/01/01")
	private String dob;
	private String countryOfIssuance;
	// -------------------
	@Hidden
	@XmlTransient
	private int addressId;
	@Hidden
	@XmlTransient
	private String addressType;
	@Hidden
	@XmlTransient
	private String createdUser;
	@Hidden
	@XmlTransient
	private String createdDate;
	

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
		if (entityNumber == null)
			return "";
		else
			return entityNumber;
	}

	public void setEntityNumber(String entityNumber) {
		this.entityNumber = entityNumber;
	}

	public String getCreatedUser() {
		if (createdUser == null)
			return "";
		else
			return createdUser.toUpperCase();
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getCreatedDate() {
		if (createdDate == null)
			return "";
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
			return "";
		else
			return addressType.toUpperCase();
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getAddressLine1() {
		if (addressLine1 == null)
			return "";
		else
			return addressLine1.toUpperCase();
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		if (addressLine2 == null)
			return "";
		else
			return addressLine2.toUpperCase();
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		if (city == null)
			return "";
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
			return "";
		else
			return state.toUpperCase();
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		if (zipCode == null)
			return "";
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
			return "";
		else
			return faxNo.toUpperCase();
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String validateAddressInfo() {
		String objAddressInfoMessage = "";
		if(country!=null && country.length()!=2)
		{
			objAddressInfoMessage = " country code must be two letters only ";

		}
		if(country==null) {
			objAddressInfoMessage = " country code is mandatory ";
		}
		if(city==null) {
			objAddressInfoMessage = " city attribute is mandatory ";
		}
		if(city!=null && city.isEmpty()) {
			objAddressInfoMessage = " city should not be Blank/Empty ";
		}
		if (addressLine1 == null || addressLine1.isEmpty())
		{
			objAddressInfoMessage = " addressLine1 ";
		}
		return objAddressInfoMessage;
	}

}
