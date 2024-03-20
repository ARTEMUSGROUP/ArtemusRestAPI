package com.artemus.app.model.response;

public class BillStatusResponse {
	private String billOfLading;
	//private String dispositionCode;
	private String dispositionDescription;
	private String sentDate;
	/**
	 * @return the billOfLading
	 */
	public String getBillOfLading() {
		return billOfLading;
	}
	/**
	 * @param billOfLading the billOfLading to set
	 */
	public void setBillOfLading(String billOfLading) {
		this.billOfLading = billOfLading;
	}
	
	
	/**
	 * @return the dispositionDescription
	 */
	public String getDispositionDescription() {
		return dispositionDescription;
	}
	/**
	 * @param dispositionDescription the dispositionDescription to set
	 */
	public void setDispositionDescription(String dispositionDescription) {
		this.dispositionDescription = dispositionDescription;
	}
	/**
	 * @return the sentDate
	 */
	public String getSentDate() {
		return sentDate;
	}
	/**
	 * @param sentDate the sentDate to set
	 */
	public void setSentDate(String sentDate) {
		this.sentDate = sentDate;
	}
	
	@Override
	public String toString() {
		return "BillStatusResponse [billOfLading=" + billOfLading + ", dispositionDescription=" + dispositionDescription
				+ ", sentDate=" + sentDate + "]";
	}
	
	
}
