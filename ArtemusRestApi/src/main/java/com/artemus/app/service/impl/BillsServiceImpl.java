package com.artemus.app.service.impl;

import java.util.Iterator;

import com.artemus.app.dao.BillsDAO;
import com.artemus.app.dao.CustomerProfileDAO;
import com.artemus.app.dao.VesselVoyageDAO;
import com.artemus.app.model.request.BillHeader;
import com.artemus.app.model.request.Cargo;
import com.artemus.app.model.request.Equipment;
import com.artemus.app.model.request.Package;
import com.artemus.app.service.BillsService;
import com.artemus.app.utils.BillHeaderUtils;

public class BillsServiceImpl implements BillsService {
	BillHeaderUtils objUtils = new BillHeaderUtils();
	StringBuilder errorMessage;
	boolean isError;

	public void createBill(BillHeader objBillHeader) {
		// Validate JSON
		objUtils.validateRequiredFields(objBillHeader);
		// Call for DAO
		CustomerProfileDAO customerProfileDao = new CustomerProfileDAO();
		try {
			customerProfileDao.validateBillHeaderParties(objBillHeader);
			System.out.println(objBillHeader.toString());
			validateVesselVoyage(objBillHeader);
			processBill(objBillHeader);
			
//			customerProfileDao.commit();
		} finally {
			customerProfileDao.closeAll();
		}
	}

	private void validateVesselVoyage(BillHeader objBillHeader) {
		int vesselID, voyageID;
		VesselVoyageDAO objDao = new VesselVoyageDAO();
		// Get vesselID
		vesselID = objDao.validateLloydsCode(objBillHeader.getVesselSchedule().getVesselName(),
				objBillHeader.getLoginScac());
		if (vesselID != 0) {
			objBillHeader.getVesselSchedule().setVesselId(vesselID);
			// Get voyageID
			voyageID = objDao.validateVoyage(objBillHeader.getVesselSchedule().getVoyageNumber(), vesselID,
					objBillHeader.getLoginScac());
			if (voyageID != 0) {
				objBillHeader.getVesselSchedule().setVoyageId(voyageID);
				// validateDischargePort
				if (!objDao.validateDischargePort(objBillHeader)) {
					errorMessage.append("portOfDischarge does not exists.");
				}
				// validateLoadPort
				if (!objDao.validateLoadPort(objBillHeader)) {
					errorMessage.append("portOfLoading does not exists.");
				}
			} else {
				errorMessage.append("voyageNumber does not exists.");
			}
		} else {
			errorMessage.append("vesselName does not exists.");

		}
	}
	
	private void processBill(BillHeader objBillHeader) {
		BillsDAO objDao = new BillsDAO();
		int billLadingId = objDao.insertIntoBillHeader(objBillHeader);
		// Adding insertIntoConsigneeShipperDetails
		objDao.insertIntoConsigneeShipperDetails(objBillHeader.getShipper(), "shipper", billLadingId);
		objDao.insertIntoConsigneeShipperDetails(objBillHeader.getBookingParty(), "booking", billLadingId);
		objDao.insertIntoConsigneeShipperDetails(objBillHeader.getSeller(), "seller", billLadingId);
		objDao.insertIntoConsigneeShipperDetails(objBillHeader.getStuffer(), "stuffer", billLadingId);
		objDao.insertIntoConsigneeShipperDetails(objBillHeader.getConsignee(), "consignee", billLadingId);
		objDao.insertIntoConsigneeShipperDetails(objBillHeader.getNotify(), "notify", billLadingId);
		objDao.insertIntoConsigneeShipperDetails(objBillHeader.getConsolidator(), "consolidator", billLadingId);
		objDao.insertIntoConsigneeShipperDetails(objBillHeader.getImporter(), "importer", billLadingId);
		objDao.insertIntoConsigneeShipperDetails(objBillHeader.getBuyer(), "buyer", billLadingId);
		objDao.insertIntoConsigneeShipperDetails(objBillHeader.getShipTo(), "shipTo", billLadingId);
		//Adding insertIntoNotifyPartyDetails
		objDao.insertIntoNotifyPartyDetails(objBillHeader.getNotifyParties(),billLadingId);
		//Adding Equipments
		addEquipments(objBillHeader ,billLadingId,objDao);
		// Adding into billDetailStatus if all Adding Equipments is succeeds
		objDao.insertIntoBillDetailStatus(objBillHeader,billLadingId);
		// Adding into voyagePortDetails
		objDao.insertIntoVoyagePortDetails(objBillHeader,"");
		// Checking isFROBBill
		if(objDao.isFROBBill(objBillHeader.getVesselSchedule().getPortOfDischarge())) {
			String firstUsDischargePort=objDao.getDistrictPortForFROB(objBillHeader.getVesselSchedule().getVoyageId(),objBillHeader.getLoginScac());
			objDao.insertIntoVoyagePortDetails(objBillHeader,firstUsDischargePort);
		}
		
		
	}

	private void addEquipments(BillHeader objBillHeader , int billLadingId,BillsDAO objBillsDao) {
		for (Equipment objEquipment : objBillHeader.getEquipments()) {
			objBillsDao.insertIntoEquipments(objEquipment, billLadingId);
			objBillsDao.insertIntoSeals(objEquipment,billLadingId);
			objBillsDao.addPackages(objEquipment,billLadingId);
			objBillsDao.addCargos(objEquipment,billLadingId);
		}
	}
	

}
