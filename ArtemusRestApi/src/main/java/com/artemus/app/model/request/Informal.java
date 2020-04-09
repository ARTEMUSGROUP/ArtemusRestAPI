package com.artemus.app.model.request;

import javax.xml.bind.annotation.XmlTransient;


public class Informal {
	
	
	private String shipmentSubType;
	
	private int estimatedValue;
	
	private int estimatedQuantity;
	
	private String unitOfMeasure;
	//private estimatedWeight Weight;
	
	private int estimatedWeight;
	
	//------------------
	@XmlTransient
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
