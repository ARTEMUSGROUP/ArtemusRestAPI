package com.artemus.app.model.request;

import java.util.ArrayList;

import org.hibernate.validator.constraints.NotEmpty;

import com.github.reinert.jjschema.Attributes;

public class JPEquipment {
	
	@Attributes(required = true, description = "The equipmentNo.")
	@NotEmpty(message = "equipmentNo cannot be blank")
	private String equipmentNo;
	@Attributes(required = true, description = "The equipmentType.")
	@NotEmpty(message = "equipmentType cannot be blank")
	private String equipmentType;
	private String serviceType;
	private String containerOwnership;
    private String vanningType;
	private String ccid;
	
	private ArrayList<String> seals;
	private ArrayList<Package> packages;
	private ArrayList<Cargo> cargos;
	
	//---------------------------------------------
	private String serviceTypeId;
	private String containerOwnershipId;
    private String vanningTypeId;
	private String customConventionId;
	
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
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getContainerOwnership() {
		return containerOwnership;
	}
	public void setContainerOwnership(String containerOwnership) {
		this.containerOwnership = containerOwnership;
	}
	public String getVanningType() {
		return vanningType;
	}
	public void setVanningType(String vanningType) {
		this.vanningType = vanningType;
	}
	public String getCcid() {
		return ccid;
	}
	public void setCcid(String ccid) {
		this.ccid = ccid;
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
	
	
	
	public String getServiceTypeId() {
		
		if(serviceTypeId ==null || serviceTypeId.isEmpty()) {
			return "51";
		}
		
		return serviceTypeId;
	}
	public void setServiceTypeId(String serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	public String getContainerOwnershipId() {
		
		if(containerOwnershipId ==null || containerOwnershipId.isEmpty())
		{
			return "1";
		}
		
		return containerOwnershipId;
	}
	public void setContainerOwnershipId(String containerOwnershipId) {
		this.containerOwnershipId = containerOwnershipId;
	}
	public String getVanningTypeId() {
		if(vanningTypeId ==null || vanningTypeId.isEmpty())
		{
			return "4";
		}
		
		return vanningTypeId;
	}
	public void setVanningTypeId(String vanningTypeId) {
		this.vanningTypeId = vanningTypeId;
	}
	public String getCustomConventionId() {
		if(customConventionId ==null || customConventionId.isEmpty())
		{
			return "-1";
		}
		return customConventionId;
	}
	public void setCustomConventionId(String customConventionId) {
		this.customConventionId = customConventionId;
	}
	public StringBuffer validateJPEquipment() {
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
