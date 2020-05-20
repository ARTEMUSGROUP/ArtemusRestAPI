package com.artemus.app.model.request;

import javax.xml.bind.annotation.XmlTransient;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

public class Informal {
	
	@Schema(description = "Accepted values are: 01,02,03. 01-Sectin 321 Shipments, 02-Informal Shipments, "
			+ "03-General Note 3(e) Shipments.",required = false,example=" ")
	private String shipmentSubType;
	@Schema(description = "The estimated value",required = false)
	private int estimatedValue;
	@Schema(description = "The estimated quantity",required = false)
	private int estimatedQuantity;
	@Schema(description = "A description of the package type.See Appendix C for a list of valid entries",required = false,example=" ")
	private String unitOfMeasure;
	//private estimatedWeight Weight;
	@Schema(description = "Weight in Kilograms",required = false)
	private int estimatedWeight;
	
	//------------------
	@XmlTransient
	@Hidden
	private String unit;
	
	public Informal() {
		super();
	}
	public String getShipmentSubType() {
		
		return shipmentSubType;
	}
	public void setShipmentSubType(String shipmentSubType) {
		this.shipmentSubType = shipmentSubType;
	}
	
	public int getEstimatedValue() {
		return estimatedValue;
	}
	public void setEstimatedValue(int estimatedValue) {
		this.estimatedValue = estimatedValue;
	}
	public int getEstimatedQuantity() {
		return estimatedQuantity;
	}
	
	public void setEstimatedQuantity(int estimatedQuantity) {
		this.estimatedQuantity = estimatedQuantity;
	}
	public String getUnitOfMeasure() {
		return unitOfMeasure.toUpperCase();
	}
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	/*
	 * public estimatedWeight getWeight() { return Weight; } public void
	 * setWeight(estimatedWeight weight) { Weight = weight; }
	 */
	public int getEstimatedWeight() {
		return estimatedWeight;
	}
	public void setEstimatedWeight(int estimatedWeight) {
		this.estimatedWeight = estimatedWeight;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Override
	public String toString() {
		return "Informal [shipmentSubType=" + shipmentSubType + ", estimatedValue=" + estimatedValue
				+ ", estimatedQuantity=" + estimatedQuantity + ", unitOfMeasure=" + unitOfMeasure + ", estimatedWeight="
				+ estimatedWeight + ", unit=" + unit + "]";
	}
	
	
	

}
