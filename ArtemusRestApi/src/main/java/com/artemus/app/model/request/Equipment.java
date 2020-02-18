package com.artemus.app.model.request;
import java.util.ArrayList;

public class Equipment {

	private String equipmentNo;
	private String equipmentType;
	private ArrayList<String> seals;
	private ArrayList<Package> packages;
	private ArrayList<Cargo> cargos;

	public String getEquipmentNo() {
		return equipmentNo;
	}

	public void setEquipmentNo(String equipmentNo) {
		this.equipmentNo = equipmentNo;
	}

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public ArrayList<String> getSeals() {
		return seals;
	}

	public void setSeals(ArrayList<String> seals) {
		this.seals = seals;
	}

	public ArrayList<Package> getPackages() {
		return packages;
	}

	public void setPackages(ArrayList<Package> packages) {
		this.packages = packages;
	}

	public ArrayList<Cargo> getCargos() {
		return cargos;
	}

	public void setCargos(ArrayList<Cargo> cargos) {
		this.cargos = cargos;
	}

}
