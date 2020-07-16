package com.artemus.app.model.request;

import java.util.ArrayList;

import org.hibernate.validator.constraints.NotEmpty;

import com.github.reinert.jjschema.Attributes;

import io.swagger.v3.oas.annotations.media.Schema;

public class Equipment {

	@Schema(description = "The equipment identifier for this piece of equipment.", required = true)
	@Attributes(required = true, description = "The equipmentNo.")
	@NotEmpty(message = "equipmentNo cannot be blank")
	private String equipmentNo;

	@Attributes(required = true, description = "The equipmentType.")
	@NotEmpty(message = "equipmentType cannot be blank")
	@Schema(description = "The size and type code for this piece of equipment. See Appendix A for the standard list of equipment types. "
			+ "Additional types can be added to customer databases.", required = true)
	private String equipmentType;

	@Schema(description = "A comma-separated list of seal numbers for this piece of equipment.", required = true)
	private ArrayList<String> seals;

	@Schema(description = "Defines packaging for bill cargo. Packages my be in a single piece of equipment. Many cargo items may be in a package.", required = true)
	private ArrayList<Package> packages;

	@Schema(description = "Describes the cargo found on this bill of lading. Cargo items must reference a package. "
			+ "The text of the Cargo element is the description of good for this cargo. Use a CDATA tag when needed.", required = true)
	private ArrayList<Cargo> cargos;

	public String getEquipmentNo() {
		if (equipmentNo == null)
			return equipmentNo;
		else
			return equipmentNo.toUpperCase();
	}

	public void setEquipmentNo(String equipmentNo) {
		this.equipmentNo = equipmentNo;
	}

	public String getEquipmentType() {
		if (equipmentType == null)
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

	public StringBuffer validateEquipment(String billType) {
		StringBuffer objString = new StringBuffer();
		if (equipmentNo == null || equipmentNo.isEmpty()) {
			if (objString.length() > 0) {
				objString.append(",");
			}
			objString.append("equipmentNo");
		}
		if (equipmentType == null || equipmentType.isEmpty()) {
			if (objString.length() > 0) {
				objString.append(",");
			}
			objString.append("equipmentType");
		}

		if (!billType.equalsIgnoreCase("empty")) {
			if (packages == null || packages.isEmpty()) {
				if (objString.length() > 0) {
					objString.append(",");
				}
				objString.append("packages");
			} else {
				int packageCount = 0;
				StringBuffer objPackageMsg = new StringBuffer();
				for (Package objPackage : this.getPackages()) {
					String str = objPackage.validatePackage().toString();
					if (str.length() > 0) {
						objPackageMsg.append(packageCount + ": {" + str + "}");
					}
					packageCount++;
				}
				if (objPackageMsg.length() > 0) {
					if (objString.length() > 0) {
						objString.append(",");
					}
					objString.append("packages : { " + objPackageMsg + " }");
				}
			}

			if (cargos == null || cargos.isEmpty()) {
				if (objString.length() > 0) {
					objString.append(",");
				}
				objString.append("cargos: must not be empty ");
			}
		}

		return objString;
	}

}
