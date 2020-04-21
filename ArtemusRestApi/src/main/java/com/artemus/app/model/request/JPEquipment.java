package com.artemus.app.model.request;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotEmpty;

import com.github.reinert.jjschema.Attributes;

public class JPEquipment {
	
	//@ApiModelProperty(value = "The equipment identifier for this piece of equipment.",required = true)
	@Attributes(required = true, description = "The equipmentNo.")
	@NotEmpty(message = "equipmentNo cannot be blank")
	private String equipmentNo;
	
	@Attributes(required = true, description = "The equipmentType.")
	@NotEmpty(message = "equipmentType cannot be blank")
	//@ApiModelProperty(value = "The size and type code for this piece of equipment.  See Appendix A for the standard list of equipment types.  "
	//		+ "Additional types can be added to customer databases.",required = true)
	private String equipmentType;
	
	//@ApiModelProperty(value = "The ServiceType ID for ServiceType.  Accepted values include: 51,52,53 for following cases. \r\n" + 
	//		"                          51-CY Delivery,\r\n" + 
	//	"                          52-CFS Delivery,\r\n" + 
	//	"                          53-DOOR Delivery",required = false,example="51")
	private String serviceTypeId;
	
	//@ApiModelProperty(value = "The containerOwnership ID for containerOwnership.  Accepted values include: 1,2,3,4,5 for following cases.\r\n" + 
	//	"                           1-Shipper supplied,\r\n" + 
	//	"                           2-Carrier supplied  ,\r\n" + 
	//	"                           3-Consolidator supplied,\r\n" + 
	//	"                           4-Deconsolidator supplied,\r\n" + 
	//	"                           5-Third party supplied",required = false,example="1")
	private String containerOwnershipId;
	
	//@ApiModelProperty(value = "The vanningType ID for vanningType.  Accepted values include: 4,1,16 for following cases:\r\n" + 
	//	"                              4-Shipper Load,\r\n" + 
	//	"                              1-Carrier Load,\r\n" + 
	//	"                              16-Consignee Load",required = false,example="4")
    private String vanningTypeId;
	
    //@ApiModelProperty(value = "The customConvention ID for costomConvention.  Accepted values include: 1,2,3",required = false,example="1")
	private String customConventionId;
	
	//@ApiModelProperty(value = "A comma-separated list of seal numbers for this piece of equipment.",required = true)
	private ArrayList<String> seals;
	
	//@ApiModelProperty(value = "Defines packaging for bill cargo.  Packages my be in a single piece of equipment.  Many cargo items may be in a package.",required = true)
	private ArrayList<Package> packages;
	
	//@ApiModelProperty(value = "Describes the cargo found on this bill of lading.  Cargo items must reference a package.  "
	//	+ "The text of the Cargo element is the description of good for this cargo.  Use a CDATA tag when needed",required = true)
	private ArrayList<Cargo> cargos;
	
	//---------------------------------------------
	@XmlTransient
	private String serviceType;
	@XmlTransient
	private String containerOwnership;
	@XmlTransient
	private String vanningType;
	@XmlTransient
	private String ccid;
	
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
