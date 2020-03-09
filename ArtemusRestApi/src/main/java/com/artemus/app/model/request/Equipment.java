package com.artemus.app.model.request;
import java.util.ArrayList;

public class Equipment {

	private String equipmentNo;
	private String equipmentType;
	private ArrayList<String> seals;
	private ArrayList<Package> packages;
	private ArrayList<Cargo> cargos;

	public String getEquipmentNo() {
		if(equipmentNo==null)
			return equipmentNo;
			else	
		return equipmentNo.toUpperCase();
	}

	public void setEquipmentNo(String equipmentNo) {
		this.equipmentNo = equipmentNo;
	}

	public String getEquipmentType() {
		if(equipmentType==null)
			return equipmentType;
		else	
		return equipmentType.toUpperCase();
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
	
	public StringBuffer validateEquipment() {
		StringBuffer objString = new StringBuffer();
		if(equipmentNo==null||equipmentNo.isEmpty()) {
			if (objString.length() > 0) {
				objString.append(",");
			}
			objString.append("equipmentNo");
		}
		if(equipmentType==null||equipmentType.isEmpty()) {
			if (objString.length() > 0) {
				objString.append(",");
			}
			objString.append("equipmentType");
		}
		
		if(packages==null||packages.isEmpty()) {
			if (objString.length() > 0) {
				objString.append(",");
			}
			objString.append("packages");
		}else {
			int packageCount=0;
			StringBuffer objPackageMsg = new StringBuffer();
			for (Package objPackage : this.getPackages()) {
				String str =  objPackage.validatePackage().toString();
				if(str.length()>0) {
					objPackageMsg.append(packageCount+": {"+str+"}");
				}
				packageCount++;
			}
			if (objPackageMsg.length()>0) {
				if (objString.length() > 0) {
					objString.append(",");
				}
				objString.append("packages : { "+objPackageMsg+" }");
			}
		}
		
		if(cargos==null||cargos.isEmpty()) {
			if (objString.length() > 0) {
				objString.append(",");
			}
			objString.append("cargos: must not be empty ");
		}
		
		return objString;
	}

}
