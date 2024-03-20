package com.artemus.app.model.request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.github.reinert.jjschema.Attributes;

import io.swagger.v3.oas.annotations.media.Schema;

@SuppressWarnings("deprecation")
public class BillStatus {
	@Schema(description ="The bill of lading number. The BILL OF LADING Number must be only alphanumeric, it should not contain any space or special characters.",required = true)
	@Attributes(required = true, description = "The billOfLading.")
	@NotEmpty(message = "billOfLading cannot be blank")
	@Size(max = 20, message = "The billOfLading must be max 20 letters only")
	private String billOfLading;

	/**
	@Schema(description = "The SCAC for the operator of this Bill. By default the SCAC code of the user is used.",required = false,example=" ")
	@Attributes(required = false, description = "The SCAC for the operator of this Bill.  By default the SCAC code of the user is used.")
	private String scacCode;
	*/
	/**
	 * @return the billOfLading
	 */
	public String getBillOfLading() {
		if (billOfLading == null)
			return billOfLading;
		else
			return billOfLading.toUpperCase();
	}

	/**
	 * @param billOfLading the billOfLading to set
	 */
	public void setBillOfLading(String billOfLading) {
		this.billOfLading = billOfLading;
	}

	@Override
	public String toString() {
		return "BillStatus [billOfLading=" + billOfLading + "]";
	}


	
}
