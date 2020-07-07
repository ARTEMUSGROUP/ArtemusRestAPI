package com.artemus.app.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.artemus.app.dao.BillsDAO;
import com.artemus.app.dao.CustomerProfileDAO;
import com.artemus.app.dao.VesselVoyageDAO;
import com.artemus.app.exceptions.ErrorResponseException;
import com.artemus.app.exceptions.MissingRequiredFieldException;
import com.artemus.app.model.request.BillHeader;
import com.artemus.app.model.request.Carnet;
import com.artemus.app.model.request.Equipment;
import com.artemus.app.model.request.Informal;
import com.artemus.app.service.BillsService;
import com.artemus.app.utils.BillHeaderUtils;
import com.artemus.app.utils.ValidateBeanUtil;

public class BillsServiceImpl implements BillsService {
	static Logger logger = LogManager.getLogger();
	BillHeaderUtils objUtils = new BillHeaderUtils();
	StringBuffer errorMessage = new StringBuffer("");
	StringBuilder entityErrorMessage = new StringBuilder("");
	boolean isError;

	public void createBill(BillHeader objBillHeader) {
		// Validate JSON
		logger.debug(objBillHeader.toString());
				ValidateBeanUtil.buildDefaultValidatorFactory();
				StringBuffer invalidJsonMsg = ValidateBeanUtil.getConstraintViolationMsgForVoyage(objBillHeader);
				if (invalidJsonMsg.length() > 0) {
					throw new MissingRequiredFieldException(invalidJsonMsg.toString());
				}
		objUtils.validateRequiredFields(objBillHeader);
		// Call for DAO
		CustomerProfileDAO customerProfileDao = new CustomerProfileDAO();
		try {
			customerProfileDao.validateBillHeaderParties(objBillHeader);
			// getting error messages for entity number and type
			String entityerrormsg = new String("");
			entityerrormsg = "" + customerProfileDao.getErrorMessage().toString();
			System.out.println("Entity message" + entityerrormsg);
			entityErrorMessage.append(entityerrormsg);
			if (entityErrorMessage.length() > 0) {
				throw new ErrorResponseException(entityErrorMessage.toString());
			}
			System.out.println(objBillHeader.toString());
			validateShipmentType(objBillHeader);
			validateVesselVoyage(objBillHeader);
			validateScacUser(objBillHeader, customerProfileDao);
			System.out.println(errorMessage);

			if (errorMessage.length() > 0) {
				throw new ErrorResponseException(errorMessage.toString());
			} else {
				try {
					processBill(objBillHeader, customerProfileDao);
					if (errorMessage.length() > 0) {
						throw new ErrorResponseException(errorMessage.toString().replaceAll("<br>", ""));
					}
				} catch (ErrorResponseException e) {
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

	private void validateShipmentType(BillHeader objBillHeader) {
		Carnet objcarnet = new Carnet();
		Informal objinformal = new Informal();
		if (objBillHeader.getShipmentType() == null || objBillHeader.getShipmentType().isEmpty()) {
			objBillHeader.setShipmentType("01");
		}
		if (objBillHeader.getTransmissionType() == null || objBillHeader.getTransmissionType().isEmpty()) {
			objBillHeader.setTransmissionType("CT");
		}
		if (objBillHeader.getShipmentType().equalsIgnoreCase("11")) {
			System.out.println("setting Informal fields");
			if (objBillHeader.getInformal() == null) {
				errorMessage.append("Informal is required :");
			} else {
				objcarnet.setCarnetCountry("");
				objcarnet.setCarnetNumber("");
				objBillHeader.setCarnet(objcarnet);
			}
		} else if (objBillHeader.getShipmentType().equalsIgnoreCase("06")) {
			if (objBillHeader.getCarnet() == null) {
				errorMessage.append("Carnet is required :");
			} else {
				System.out.println("setting Carnet fields");
				if (objBillHeader.getInformal() == null) {

					objinformal.setShipmentSubType("");
					objinformal.setEstimatedValue(0);
					objinformal.setEstimatedQuantity(0);
					objinformal.setUnitOfMeasure("");
					objinformal.setEstimatedWeight(0);
					objBillHeader.setInformal(objinformal);
				}
			}
		} else {
			System.out.println("Setting non CI fields");

			objcarnet.setCarnetNumber("");
			objcarnet.setCarnetCountry("");
			objinformal.setShipmentSubType("");
			objinformal.setEstimatedValue(0);
			objinformal.setEstimatedQuantity(0);
			objinformal.setUnitOfMeasure("");
			objinformal.setEstimatedWeight(0);
			objBillHeader.setCarnet(objcarnet);
			objBillHeader.setInformal(objinformal);
		}
	}

	private void validateScacUser(BillHeader objBillHeader, CustomerProfileDAO customerProfileDao) {
		String scacUserType = customerProfileDao.getScacUserType(objBillHeader.getLoginScac());
		if (scacUserType.equalsIgnoreCase("master")) {
		} else {
			if (objBillHeader.getNvoType().equalsIgnoreCase("automated NVO")) {
			} else {
				errorMessage
						.append("Only nvoType:automated NVO is acceptable for scac " + objBillHeader.getLoginScac());
			}
		}
	}

	public void updateBill(BillHeader objBillHeader) {
		logger.debug(objBillHeader.toString());
		// Validate JSON
		ValidateBeanUtil.buildDefaultValidatorFactory();
		StringBuffer invalidJsonMsg = ValidateBeanUtil.getConstraintViolationMsgForVoyage(objBillHeader);
		if (invalidJsonMsg.length() > 0) {
			throw new MissingRequiredFieldException(invalidJsonMsg.toString());
		}
		objUtils.validateRequiredFields(objBillHeader);
		// Call for DAO
		CustomerProfileDAO customerProfileDao = new CustomerProfileDAO();
		try {
			customerProfileDao.validateBillHeaderParties(objBillHeader);
			// getting error messages for entity number and type
			String entityerrormsg = new String("");
			entityerrormsg = "" + customerProfileDao.getErrorMessage().toString();
			System.out.println("Entity message" + entityerrormsg);
			entityErrorMessage.append(entityerrormsg);
			if (entityErrorMessage.length() > 0) {
				throw new ErrorResponseException(entityErrorMessage.toString());
			}
			System.out.println(objBillHeader.toString());
			validateShipmentType(objBillHeader);
			validateVesselVoyage(objBillHeader);
			validateScacUser(objBillHeader, customerProfileDao);
			System.out.println(errorMessage);
			if (errorMessage.length() > 0) {
				throw new ErrorResponseException(errorMessage.toString());
			} else {
				try {
					processBillForUpdate(objBillHeader, customerProfileDao);
				} catch (ErrorResponseException e) {
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
			if (objBillHeader.getShipmentType().equalsIgnoreCase("06")
					|| objBillHeader.getShipmentType().equalsIgnoreCase("6")) {
				if (objBillHeader.getCarnet() != null) {
					if (objBillHeader.getCarnet().getCarnetNumber() == "") {
						errorMessage.append("Carnet Number is Mandatory for shipment type Carnet.");
					} else if (objBillHeader.getCarnet().getCarnetCountry() == "") {
						errorMessage.append("Carnet Country is Mandatory for shipment type Carnet.");
					}
				} else {
					errorMessage.append("Carnet is Mandatory for shipment type Carnet.");
				}
			} else if (objBillHeader.getShipmentType().equalsIgnoreCase("11")) {
				if (objBillHeader.getInformal() != null) {
					if (objBillHeader.getInformal().getShipmentSubType() == "") {
						errorMessage.append("ShipmentSubType is Mandatory for shipment type Informal.");
					} else if (objBillHeader.getInformal().getUnitOfMeasure() == "") {
						errorMessage.append("UnitOfMeasure is Mandatory for shipment type Informal.");
					}
					if (objBillHeader.getInformal().getEstimatedWeight() == 0) {
						errorMessage.append("EstimatedWeight is Mandatory for shipment type Informal.");
					}
				} else {
					errorMessage.append("Informal is Mandatory for shipment type Informal.");
				}
			}
		} finally {
			objDao.closeAll();
		}
	}

	private void processBill(BillHeader objBillHeader, CustomerProfileDAO objCustomerProfiledao)
			throws SQLException, ErrorResponseException {
		System.out.println("processBill :: ");
		String isferrormsg = new String("");
		BillsDAO objDao = new BillsDAO(objCustomerProfiledao.getConnection());
		try {
			if (objDao.validateBillExist(objBillHeader)) {
				throw new ErrorResponseException("Bill Number Already Exist");
			}
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
			// Setting ISF Type
			objDao.isFROBBill(objBillHeader);
			// Adding Equipments
			addEquipments(objBillHeader, billLadingId, objDao, objCustomerProfiledao);

			//validate Hazard Code
			errorMessage=objDao.getHazardErrorMessage();
			if(errorMessage.length()>5) {
				throw new ErrorResponseException(errorMessage.toString());
			}
			
			// Setting ISF Error
			isferrormsg = objBillHeader.getIsfType() + ":" + objDao.getErrorMessage().toString();
			System.out.println(isferrormsg);
			entityErrorMessage.append(isferrormsg);
			System.out.println(entityErrorMessage);

			if (objBillHeader.getIsfType() == "ISF-5") {
				if (objBillHeader.getShipTo() == null) {
					entityErrorMessage.append("<br>Ship To information is not entered.");
				}
				if (objBillHeader.getBookingParty() == null) {
					entityErrorMessage.append("<br>Booking Party information is not entered.");
				}
			} else if (objBillHeader.getIsfType() == "ISF-10") {
				if (objBillHeader.getShipTo() == null) {
					entityErrorMessage.append("<br>Ship To information is not entered.");
				} else if (objBillHeader.getSeller() == null) {
					entityErrorMessage.append("<br>Seller information is not entered.");
				} else if (objBillHeader.getBuyer() == null) {
					entityErrorMessage.append("<br>Buyer information is not entered.");
				} else if (objBillHeader.getStuffer() == null) {
					entityErrorMessage.append("<br>Stuffer information is not entered.");
				} else if (objBillHeader.getConsolidator() == null) {
					entityErrorMessage.append("<br>Consolidator information is not entered.");
				} else if (objBillHeader.getImporter() == null) {
					entityErrorMessage.append("<br>Importer information is not entered.");
				}

			}
			//Setting Entity Error
			entityErrorMessage.append(objCustomerProfiledao.getIsfErrorMessage());
			// Setting ISF errorDescription
			objBillHeader.setIsfErrorDescription(entityErrorMessage.toString());
			// Adding into billDetailStatus if all Adding Equipments is succeeds
			objDao.insertIntoBillDetailStatus(objBillHeader, billLadingId);
			// Adding into voyagePortDetails
			objDao.insertIntoVoyagePortDetails(objBillHeader, "");
			// Checking isFROBBill
			if (objDao.isFROBBill(objBillHeader)) {
				String firstUsDischargePort = objDao.getDistrictPortForFROB(
						objBillHeader.getVesselSchedule().getVoyageId(), objBillHeader.getLoginScac());
				objDao.insertIntoVoyagePortDetails(objBillHeader, firstUsDischargePort);
			}
			objDao.commit();
		} finally {
			objDao.closeAll();
		}

	}

	private void processBillForUpdate(BillHeader objBillHeader, CustomerProfileDAO objCustomerProfiledao)
			throws SQLException, ErrorResponseException {
		logger.info("processBillForUpdate :: ");
		String isferrormsg = new String("");
		BillsDAO objDao = new BillsDAO(objCustomerProfiledao.getConnection());
		try {
			if (objDao.validateBillExist(objBillHeader)) {
				int billLadingId = objBillHeader.getBillLadingId();
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
				// Setting ISF Type
				objDao.isFROBBill(objBillHeader);
				// Adding Equipments
				addEquipments(objBillHeader, billLadingId, objDao, objCustomerProfiledao);
				
				
				//validate Hazard Code
				errorMessage=objDao.getHazardErrorMessage();
				if(errorMessage.length()>5) {
					throw new ErrorResponseException(errorMessage.toString());
				}
				
				// Updating into billDetailStatus if all Adding Equipments is succeeds
				// Setting ISF Error

				isferrormsg = objBillHeader.getIsfType() + ":" + objDao.getErrorMessage().toString();
				System.out.println(isferrormsg);
				entityErrorMessage.append(isferrormsg);
				System.out.println(entityErrorMessage);

				if (objBillHeader.getIsfType() == "ISF-5") {
					if (objBillHeader.getShipTo() == null) {
						entityErrorMessage.append("<br>Ship To information is not entered.");
					}
					if (objBillHeader.getBookingParty() == null) {
						entityErrorMessage.append("<br>Booking Party information is not entered.");
					}
				} else if (objBillHeader.getIsfType() == "ISF-10") {
					if (objBillHeader.getShipTo() == null) {
						entityErrorMessage.append("<br>Ship To information is not entered.");
					} else if (objBillHeader.getSeller() == null) {
						entityErrorMessage.append("<br>Seller information is not entered.");
					} else if (objBillHeader.getBuyer() == null) {
						entityErrorMessage.append("<br>Buyer information is not entered.");
					} else if (objBillHeader.getStuffer() == null) {
						entityErrorMessage.append("<br>Stuffer information is not entered.");
					} else if (objBillHeader.getConsolidator() == null) {
						entityErrorMessage.append("<br>Consolidator information is not entered.");
					} else if (objBillHeader.getImporter() == null) {
						entityErrorMessage.append("<br>Importer information is not entered.");
					}

				}
				//Setting Entity Error
				entityErrorMessage.append(objCustomerProfiledao.getIsfErrorMessage());
				// Setting ISF errorDescription
				objBillHeader.setIsfErrorDescription(entityErrorMessage.toString());
				objDao.updateBillDetailStatus(objBillHeader, billLadingId);
				// Adding into voyagePortDetails
				objDao.insertIntoVoyagePortDetails(objBillHeader, "");
				// Checking isFROBBill
				if (objDao.isFROBBill(objBillHeader)) {
					String firstUsDischargePort = objDao.getDistrictPortForFROB(
							objBillHeader.getVesselSchedule().getVoyageId(), objBillHeader.getLoginScac());
					objDao.insertIntoVoyagePortDetails(objBillHeader, firstUsDischargePort);
				}

			} else {
				throw new ErrorResponseException("Bill number does not exist");
			}
			;
			objDao.commit();
		} finally {
			objDao.closeAll();
		}

	}

	private void addEquipments(BillHeader objBillHeader, int billLadingId, BillsDAO objBillsDao,
			CustomerProfileDAO customerProfileDao) throws SQLException {
		boolean returnedVal = true;
		int packageIndex = 0;
		int cargoIndex = 0;

		for (Equipment objEquipment : objBillHeader.getEquipments()) {
			if (!objBillsDao.insertIntoEquipments(objEquipment, billLadingId)) {
				returnedVal = false;
				break;
			}
			if (!objBillsDao.insertIntoSeals(objEquipment, billLadingId)) {
				returnedVal = false;
				break;
			}
			packageIndex = objBillsDao.addPackages(objEquipment, billLadingId, packageIndex);
			if (packageIndex == -1) {
				returnedVal = false;
				break;
			}
			if (cargoIndex < objEquipment.getCargos().size()) {
				customerProfileDao.validateCustomer(objEquipment.getCargos().get(cargoIndex).getManufacturer(),
						objBillHeader.getLoginScac());
			}
			cargoIndex = objBillsDao.addCargos(objEquipment, billLadingId, cargoIndex);
			if (cargoIndex == -1) {
				returnedVal = false;
				break;
			}
		}
		if (!returnedVal) {
			throw new SQLException();
		}

	}

}
