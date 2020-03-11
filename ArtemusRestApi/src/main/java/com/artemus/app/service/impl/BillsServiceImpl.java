package com.artemus.app.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.artemus.app.dao.BillsDAO;
import com.artemus.app.dao.CustomerProfileDAO;
import com.artemus.app.dao.VesselVoyageDAO;
import com.artemus.app.exceptions.ErrorResponseException;
import com.artemus.app.model.request.BillHeader;
import com.artemus.app.model.request.Equipment;
import com.artemus.app.service.BillsService;
import com.artemus.app.utils.BillHeaderUtils;

public class BillsServiceImpl implements BillsService {
	static Logger logger = LogManager.getLogger();
	BillHeaderUtils objUtils = new BillHeaderUtils();
	StringBuffer errorMessage = new StringBuffer("");
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
			validateScacUser(objBillHeader,customerProfileDao);
			System.out.println(errorMessage);
			if (errorMessage.length() > 0) {
				throw new ErrorResponseException(errorMessage.toString());
			} else {
				try {
					processBill(objBillHeader, customerProfileDao.getConnection());
				}catch (ErrorResponseException e) {
					throw e;
				} catch (Exception e) {
					throw new ErrorResponseException("Internal Bill Processing Error");
				}
			}
		} finally {
			customerProfileDao.closeAll();
		}
	}
	
	private void validateScacUser(BillHeader objBillHeader,CustomerProfileDAO customerProfileDao) {
		String scacUserType = customerProfileDao.getScacUserType(objBillHeader.getLoginScac());
		if (scacUserType.equalsIgnoreCase("master")) {
		}else {
			if(objBillHeader.getNvoType().equalsIgnoreCase("automated NVO")){
			}else {
				errorMessage.append("Only nvoType:automated NVO is acceptable for scac "+objBillHeader.getLoginScac());
			}
		}
	}

	public void updateBill(BillHeader objBillHeader) {
		// Validate JSON
		objUtils.validateRequiredFields(objBillHeader);
		// Call for DAO
				CustomerProfileDAO customerProfileDao = new CustomerProfileDAO();
				try {
					customerProfileDao.validateBillHeaderParties(objBillHeader);
					System.out.println(objBillHeader.toString());			
					validateVesselVoyage(objBillHeader);
					validateScacUser(objBillHeader,customerProfileDao);
					System.out.println(errorMessage);
					if (errorMessage.length() > 0) {
						throw new ErrorResponseException(errorMessage.toString());
					} else {
						try {
							processBillForUpdate(objBillHeader, customerProfileDao.getConnection());
						}catch (ErrorResponseException e) {
							e.printStackTrace();
							throw e;
						} catch (Exception e) {
							e.printStackTrace();
							throw new ErrorResponseException("Internal Bill Processing Error");
						}
					}
				} finally {
					customerProfileDao.closeAll();
				}
	}

	private void validateVesselVoyage(BillHeader objBillHeader) {
		System.out.println("validateVesselVoyage ::");
		int vesselID, voyageID;
		VesselVoyageDAO objDao = new VesselVoyageDAO();
		try {
			// Get vesselID
			vesselID = objDao.validateLloydsCode(objBillHeader.getVesselSchedule().getVesselName(),
					objBillHeader.getLoginScac());
			if (vesselID != 0) {
				objBillHeader.getVesselSchedule().setVesselId(vesselID);
				// Get voyageID
				voyageID = objDao.validateVoyage(objBillHeader, vesselID);
				if (voyageID != 0) {
					objBillHeader.getVesselSchedule().setVoyageId(voyageID);
					// validateDischargePort
					if (objBillHeader.getVesselSchedule().getPlaceOfDelivery() == null
							|| objBillHeader.getVesselSchedule().getPlaceOfDelivery().isEmpty()) {
						objBillHeader.getVesselSchedule()
								.setPlaceOfDelivery(objBillHeader.getVesselSchedule().getPortOfDischarge());
					}
					if (objBillHeader.getVesselSchedule().getPlaceOfReceipt() == null
							|| objBillHeader.getVesselSchedule().getPlaceOfReceipt().isEmpty()) {
						objBillHeader.getVesselSchedule()
								.setPlaceOfReceipt(objBillHeader.getVesselSchedule().getPortOfLoading());
					}
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
		} finally {
			objDao.closeAll();
		}
	}

	private void processBill(BillHeader objBillHeader, Connection conn) throws SQLException,ErrorResponseException {
		System.out.println("processBill :: ");
		BillsDAO objDao = new BillsDAO(conn);
		try {
			if(objDao.validateBillExist(objBillHeader)) {
				throw new ErrorResponseException("Bill Number Already Exist");
			};
			int billLadingId = objDao.insertIntoBillHeader(objBillHeader);
			if (billLadingId == 0) {
				throw new SQLException();
			}
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
			// Adding insertIntoNotifyPartyDetails
			objDao.insertIntoNotifyPartyDetails(objBillHeader.getNotifyParties(), billLadingId);
			// Adding Equipments
			addEquipments(objBillHeader, billLadingId, objDao);
			// Adding into billDetailStatus if all Adding Equipments is succeeds
			objDao.insertIntoBillDetailStatus(objBillHeader, billLadingId);
			// Adding into voyagePortDetails
			objDao.insertIntoVoyagePortDetails(objBillHeader, "");
			// Checking isFROBBill
			if (objDao.isFROBBill(objBillHeader.getVesselSchedule().getPortOfDischarge())) {
				String firstUsDischargePort = objDao.getDistrictPortForFROB(
						objBillHeader.getVesselSchedule().getVoyageId(), objBillHeader.getLoginScac());
				objDao.insertIntoVoyagePortDetails(objBillHeader, firstUsDischargePort);
			}
			objDao.commit();
		} finally {
			objDao.closeAll();
		}

	}
	
	private void processBillForUpdate(BillHeader objBillHeader, Connection conn) throws SQLException,ErrorResponseException {
		logger.info("processBillForUpdate :: ");
		BillsDAO objDao = new BillsDAO(conn);
		try {
			if(objDao.validateBillExist(objBillHeader)) {
				int billLadingId= objBillHeader.getBillLadingId();
				// updating BillHeader
				objDao.updateBillHeader(objBillHeader);
				// Deleting
				objDao.deleteFromConsigneeShipperDetails(billLadingId);
				objDao.deleteFromEquipment(billLadingId);
				objDao.deleteFromNotifyPartyDetails(billLadingId);
				objDao.deleteFromSeal(billLadingId);
				objDao.deleteFromPackages(billLadingId);
				objDao.deleteFromCargo(billLadingId);
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
				// Adding insertIntoNotifyPartyDetails
				objDao.insertIntoNotifyPartyDetails(objBillHeader.getNotifyParties(), billLadingId);
				// Adding Equipments
				addEquipments(objBillHeader, billLadingId, objDao);
				// Updating into billDetailStatus if all Adding Equipments is succeeds
				objDao.updateBillDetailStatus(objBillHeader, billLadingId);
				// Adding into voyagePortDetails
				objDao.insertIntoVoyagePortDetails(objBillHeader, "");
				// Checking isFROBBill
				if (objDao.isFROBBill(objBillHeader.getVesselSchedule().getPortOfDischarge())) {
					String firstUsDischargePort = objDao.getDistrictPortForFROB(
							objBillHeader.getVesselSchedule().getVoyageId(), objBillHeader.getLoginScac());
					objDao.insertIntoVoyagePortDetails(objBillHeader, firstUsDischargePort);
				}
				
			}else{
				throw new ErrorResponseException("Bill number does not exist");
			};
			objDao.commit();
		} finally {
			objDao.closeAll();
		}

	}

	private void addEquipments(BillHeader objBillHeader, int billLadingId, BillsDAO objBillsDao) throws SQLException {
		boolean returnedVal = true;
		int packageIndex=0;
		int cargoIndex=0;
		
		for (Equipment objEquipment : objBillHeader.getEquipments()) {
			if (!objBillsDao.insertIntoEquipments(objEquipment, billLadingId)) {
				returnedVal = false;
				break;
			}
			if (!objBillsDao.insertIntoSeals(objEquipment, billLadingId)) {
				returnedVal = false;
				break;
			}
			packageIndex =objBillsDao.addPackages(objEquipment, billLadingId,packageIndex);
			if (packageIndex == -1) {
				returnedVal = false;
				break;
			}
			cargoIndex = objBillsDao.addCargos(objEquipment, billLadingId,cargoIndex);
			if (cargoIndex==-1) {
				returnedVal = false;
				break;
			}
		}
		if (!returnedVal) {
			throw new SQLException();
		}

	}

}
