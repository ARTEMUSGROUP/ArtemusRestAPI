package com.artemus.app.utils;

import com.artemus.app.exceptions.MissingRequiredFieldException;
import com.artemus.app.model.request.BillHeader;
import com.artemus.app.model.request.JPEquipment;

public class JPBillHeaderUtils {

	public void validateRequiredFields(BillHeader objBillHeader) throws MissingRequiredFieldException {
		System.out.println("validateRequiredFields :: ");
		StringBuffer objMessage = new StringBuffer();

		if (objBillHeader.getBillOfLading() == null || objBillHeader.getBillOfLading().isEmpty()) {
			objMessage.append("billOfLading");
		} else {
			if (objBillHeader.getBillOfLading().length() > 20) {
				objMessage.append("billOfLading : The length cannot exceed 20 characters.");
			}
		}

		if (objBillHeader.getBillType() == null || objBillHeader.getBillType().isEmpty()) {
			if (objMessage.length() > 0) {
				objMessage.append(",");
			}
			objMessage.append("billType");
		} else {
			if (objBillHeader.getBillType().equalsIgnoreCase("way bill")
					|| objBillHeader.getBillType().equalsIgnoreCase("empty")
					|| objBillHeader.getBillType().equalsIgnoreCase("Original")) {
				if(objBillHeader.getBillType().equalsIgnoreCase("Original")) {
					objBillHeader.setBillType("Orignal");
				}
			} else {
				if (objMessage.length() > 0) {
					objMessage.append(",");
				}
				objMessage.append("billType : Accepted values ( way bill, empty, Original)");
			}
		}

		if (objBillHeader.getNvoType() == null || objBillHeader.getNvoType().isEmpty()) {
			if (objMessage.length() > 0) {
				objMessage.append(",");
			}
			objMessage.append("nvoType");
		} else {
			if (objBillHeader.getNvoType().equalsIgnoreCase("non NVO")
					|| objBillHeader.getNvoType().equalsIgnoreCase("automated NVO")
					|| objBillHeader.getNvoType().equalsIgnoreCase("non automated NVO")) {
				if (objBillHeader.getNvoType().equalsIgnoreCase("non automated NVO")) {
					if (objBillHeader.getNvoBill() == null || objBillHeader.getNvoBill().isEmpty()) {
						if (objMessage.length() > 0) {
							objMessage.append(",");
						}
						objMessage.append("nvoBill : Required when nvoType is 'non automated NVO'");
					} else {
						if (objBillHeader.getNvoBill().equalsIgnoreCase("House")) {
							if(objBillHeader.getMasterBillScac() == null || objBillHeader.getMasterBillScac().isEmpty()) {
								if (objMessage.length() > 0) {
									objMessage.append(",");
								}
								objMessage.append("scacBill:Required when nvoBill is 'House'");
							}
							if(objBillHeader.getMasterBill() == null || objBillHeader.getMasterBill().isEmpty()) {
								if (objMessage.length() > 0) {
									objMessage.append(",");
								}
								objMessage.append("masterBill:Required when nvoType is 'House'");
							}
						}
					}
				}
			} else {
				if (objMessage.length() > 0) {
					objMessage.append(",");
				}
				objMessage.append("nvoType : Accepted values: (non NVO, automated NVO, non automated NVO)");
			}
		}
		if (objBillHeader.getShipper() == null) {
			if (objMessage.length() > 0) {
				objMessage.append(",");
			}
			objMessage.append("shipper");
		} else {
			String objShipperMessage = objBillHeader.getShipper().validateParty();
			if (objShipperMessage.length() > 0) {
				if (objMessage.length() > 0) {
					objMessage.append(",");
				}
				objMessage.append("shipper:{ " + objShipperMessage + " }");
			}
		}
		if (objBillHeader.getConsignee() == null) {
			if (objMessage.length() > 0) {
				objMessage.append(",");
			}
			objMessage.append("consignee");
		} else {
			String objConsigneerMessage = objBillHeader.getConsignee().validateParty();
			if (objConsigneerMessage.length() > 0) {
				if (objMessage.length() > 0) {
					objMessage.append(",");
				}
				objMessage.append("consignee:{ " + objConsigneerMessage + " }");
			}
		}

		if (objBillHeader.getNotify() == null) {
			if (objMessage.length() > 0) {
				objMessage.append(",");
			}
			objMessage.append("notify");
		} else {
			String objNotifyMessage = objBillHeader.getNotify().validateParty();
			if (objNotifyMessage.length() > 0) {
				if (objMessage.length() > 0) {
					objMessage.append(",");
				}
				objMessage.append("notify:{ " + objNotifyMessage + " }");
			}
		}

		if (objBillHeader.getVesselSchedule() == null) {
			if (objMessage.length() > 0) {
				objMessage.append(",");
			}
			objMessage.append("vesselSchedule");
		} else {
			StringBuffer objVesselMessage = objBillHeader.getVesselSchedule().jpvalidateVesselSchedule();
			if (objVesselMessage.length() > 0) {
				if (objMessage.length() > 0) {
					objMessage.append(",");
				}
				objMessage.append("vesselSchedule: { " + objVesselMessage + " }");
			}
		}

		if (objBillHeader.getJpequipments() == null || objBillHeader.getJpequipments().isEmpty()
				|| objBillHeader.getJpequipments().size() == 0) {
			if (objMessage.length() > 0) {
				objMessage.append(",");
			}
			objMessage.append("equipments");
		} else {
			StringBuffer objEquipmentsMessage = new StringBuffer();
			int equipmentCount = 0;
			for (JPEquipment objEquipment : objBillHeader.getJpequipments()) {
				String str = objEquipment.validateJPEquipment().toString();
				if (str.length() > 0) {
					objEquipmentsMessage.append(equipmentCount + ": {" + str + "}");
				}
				equipmentCount++;
			}

			if (objEquipmentsMessage.length() > 0) {
				if (objMessage.length() > 0) {
					objMessage.append(",");
				}
				objMessage.append("equipments: { " + objEquipmentsMessage + " }");
			}
		}

		if (objMessage.length() > 0) {
			throw new MissingRequiredFieldException("Check for fields { " + objMessage + " }");
		}

	}
	
}
