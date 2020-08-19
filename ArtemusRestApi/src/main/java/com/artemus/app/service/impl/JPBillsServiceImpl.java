package com.artemus.app.service.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.artemus.app.awsmail.SendMail;
import com.artemus.app.dao.JPBillsDAO;
import com.artemus.app.dao.JPCustomerProfileDAO;
import com.artemus.app.dao.JPVesselVoyageDAO;
import com.artemus.app.exceptions.ErrorResponseException;
import com.artemus.app.exceptions.MissingRequiredFieldException;
import com.artemus.app.model.request.JPBillHeader;
import com.artemus.app.model.request.JPEquipment;
import com.artemus.app.model.request.Party;
import com.artemus.app.service.JPBillsService;
import com.artemus.app.utils.JPBillHeaderUtils;
import com.artemus.app.utils.ValidateBeanUtil;

public class JPBillsServiceImpl implements JPBillsService {

	static Logger logger = LogManager.getLogger();
	JPBillHeaderUtils objUtils = new JPBillHeaderUtils();
	StringBuffer errorMessage = new StringBuffer("");
	boolean isError;
	SendMail mailResponse = new SendMail();
	
	@Override
	public void createBill(JPBillHeader objBillHeader) {
		// Validate JSON
		logger.info(objBillHeader.toString());
		ValidateBeanUtil.buildDefaultValidatorFactory();
		StringBuffer invalidJsonMsg = ValidateBeanUtil.getConstraintViolationMsgForVoyage(objBillHeader);
		if (invalidJsonMsg.length() > 0) {
			throw new MissingRequiredFieldException(invalidJsonMsg.toString());
		}
		objUtils.validateRequiredFields(objBillHeader);
		// Call for DAO
		JPCustomerProfileDAO jpcustomerProfileDao = new JPCustomerProfileDAO();
		try {
			validateBillHeaderParties(objBillHeader, jpcustomerProfileDao);
			System.out.println(objBillHeader.toString());
			validateVesselVoyage(objBillHeader);
			System.out.println(errorMessage);
			if (errorMessage.length() > 0) {
				try {
					mailResponse.sendMail(objBillHeader.getLoginScac(), "Bill", 1,objBillHeader.getBillOfLading(),errorMessage.toString());
				}catch (Exception e) {
					// TODO: handle exception
				}
				
				throw new ErrorResponseException(errorMessage.toString());
			} else {
				try {
					processBill(objBillHeader, jpcustomerProfileDao.getConnection());
					if (errorMessage.length() > 0) {
						try {
							mailResponse.sendMail(objBillHeader.getLoginScac(), "Japan Bill", 1,objBillHeader.getBillOfLading(),errorMessage.toString());
						}catch (Exception e) {
							// TODO: handle exception
						}
						
						throw new ErrorResponseException(errorMessage.toString().replaceAll("<br>", ""));

					}else {
						try {
							mailResponse.sendMail(objBillHeader.getLoginScac(), "Japan Bill", 0,objBillHeader.getBillOfLading(),errorMessage.toString());	
						}catch (Exception e) {
							// TODO: handle exception
						}
						
					}
				} catch (ErrorResponseException e) {
					throw e;
				} catch (Exception e) {
					throw new ErrorResponseException("Internal Bill Processing Error");
				}
			}
		} finally {
			jpcustomerProfileDao.closeAll();
		}
	}

	@Override
	public void updateBill(JPBillHeader objBillHeader) {
		// Validate JSON
		logger.info(objBillHeader.toString());
		ValidateBeanUtil.buildDefaultValidatorFactory();
		StringBuffer invalidJsonMsg = ValidateBeanUtil.getConstraintViolationMsgForVoyage(objBillHeader);
		if (invalidJsonMsg.length() > 0) {
			throw new MissingRequiredFieldException(invalidJsonMsg.toString());
		}
		objUtils.validateRequiredFields(objBillHeader);
		// Call for DAO
		JPCustomerProfileDAO jpcustomerProfileDao = new JPCustomerProfileDAO();
		try {
			validateBillHeaderParties(objBillHeader, jpcustomerProfileDao);
			System.out.println(objBillHeader.toString());
			validateVesselVoyage(objBillHeader);
			System.out.println(errorMessage);
			if (errorMessage.length() > 0) {
				mailResponse.sendMail(objBillHeader.getLoginScac(), "Bill", 1,objBillHeader.getBillOfLading(),errorMessage.toString());
				throw new ErrorResponseException(errorMessage.toString());
			} else {
				try {
					processBillForUpdate(objBillHeader, jpcustomerProfileDao.getConnection());
					if (errorMessage.length() > 0) {
						mailResponse.sendMail(objBillHeader.getLoginScac(), "Bill", 1,objBillHeader.getBillOfLading(),errorMessage.toString());
						throw new ErrorResponseException(errorMessage.toString().replaceAll("<br>", ""));

					}else {
						mailResponse.sendMail(objBillHeader.getLoginScac(), "Bill", 0,objBillHeader.getBillOfLading(),errorMessage.toString());
					}
				} catch (ErrorResponseException e) {
					e.printStackTrace();
					throw e;
				} catch (Exception e) {
					e.printStackTrace();
					throw new ErrorResponseException("Internal Bill Processing Error");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			jpcustomerProfileDao.closeAll();
		}

	}

	private void validateVesselVoyage(JPBillHeader objBillHeader) {
		System.out.println("validateVesselVoyage ::");
		int vesselID, voyageID;
		JPVesselVoyageDAO objDao = new JPVesselVoyageDAO();
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
					errorMessage.append(
							"voyageNumber does not exists :" + objBillHeader.getVesselSchedule().getVoyageNumber());
				}
			} else {
				errorMessage.append("vesselName does not exists :" + objBillHeader.getVesselSchedule().getVesselName());

			}
		} finally {
			objDao.closeAll();
		}
	}

	private void processBill(JPBillHeader objBillHeader, Connection conn) throws SQLException, ErrorResponseException {
		System.out.println("processBill :: ");
		JPBillsDAO objDao = new JPBillsDAO(conn);
		try {
			if (objDao.validateBillExist(objBillHeader)) {
				mailResponse.sendMail(objBillHeader.getLoginScac(), "Bill", 1,objBillHeader.getBillOfLading(),"Bill Number Already Exist");
				throw new ErrorResponseException("Bill Number Already Exist");
			}
			;
			int billLadingId = objDao.insertIntoBillHeader(objBillHeader);
			if (billLadingId == 0) {
				throw new SQLException();
			}
			// Adding insertIntoConsigneeShipperDetails
			objDao.insertIntoConsigneeShipperDetails(objBillHeader.getShipper(), "shipper", billLadingId);
			objDao.insertIntoConsigneeShipperDetails(objBillHeader.getConsignee(), "consignee", billLadingId);
			objDao.insertIntoConsigneeShipperDetails(objBillHeader.getNotify(), "notify", billLadingId);

			// Adding insertIntoNotifyPartyDetails
			// objDao.insertIntoNotifyPartyDetails(objBillHeader.getNotifyParties(),
			// billLadingId);

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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			objDao.closeAll();
		}

	}

	private void processBillForUpdate(JPBillHeader objBillHeader, Connection conn)
			throws SQLException, ErrorResponseException {
		System.out.println("processBillForUpdate :: ");
		JPBillsDAO objDao = new JPBillsDAO(conn);
		try {
			if (objDao.validateBillExist(objBillHeader)) {
				int billLadingId = objBillHeader.getBillLadingId();
				// Updating BillHeader
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
				objDao.insertIntoConsigneeShipperDetails(objBillHeader.getConsignee(), "consignee", billLadingId);
				objDao.insertIntoConsigneeShipperDetails(objBillHeader.getNotify(), "notify", billLadingId);

				// Adding insertIntoNotifyPartyDetails
				// objDao.insertIntoNotifyPartyDetails(objBillHeader.getNotifyParties(),
				// billLadingId);

				// Adding Equipments
				addEquipments(objBillHeader, billLadingId, objDao);
				// Update billDetailStatus if all Adding Equipments is succeeds
				objDao.updateBillDetailStatus(objBillHeader, billLadingId);
				// Adding into voyagePortDetails
				objDao.insertIntoVoyagePortDetails(objBillHeader, "");
				// Checking isFROBBill
				if (objDao.isFROBBill(objBillHeader.getVesselSchedule().getPortOfDischarge())) {
					String firstUsDischargePort = objDao.getDistrictPortForFROB(
							objBillHeader.getVesselSchedule().getVoyageId(), objBillHeader.getLoginScac());
					objDao.insertIntoVoyagePortDetails(objBillHeader, firstUsDischargePort);
				}
				objDao.commit();
			} else {
				mailResponse.sendMail(objBillHeader.getLoginScac(), "Bill", 1,objBillHeader.getBillOfLading(),"Bill Number Already Exist");
				throw new ErrorResponseException("Bill number does not exist");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			objDao.closeAll();
		}
	}

	public boolean validateBillHeaderParties(JPBillHeader objBillHeader, JPCustomerProfileDAO objCustomerProfiledao) {
		System.out.println("validateBillHeaderParties ::");
		validateCustomer(objBillHeader.getShipper(), objBillHeader.getLoginScac(), objCustomerProfiledao);
		validateCustomer(objBillHeader.getConsignee(), objBillHeader.getLoginScac(), objCustomerProfiledao);
		validateCustomer(objBillHeader.getNotify(), objBillHeader.getLoginScac(), objCustomerProfiledao);
		return true;

	}

	public void validateCustomer(Party objParty, String loginScac, JPCustomerProfileDAO objCustomerProfiledao) {
		boolean customerGen = false;
		if (objParty != null) {
			if (objCustomerProfiledao.isCustomerExists(objParty, loginScac)) {
				if (objParty.getAddressInfo().getPhoneNo() == null
						|| objParty.getAddressInfo().getPhoneNo().isEmpty()) {
					errorMessage.append("phone number is mandatory for " + objParty.getName());
				} else {
					objCustomerProfiledao.updateCustomerPhone(objParty, loginScac);
					customerGen = true;
					System.out.println("Customer phone updated");
				}
			} else {
				if (objParty.getAddressInfo().getPhoneNo() == null
						|| objParty.getAddressInfo().getPhoneNo().isEmpty()) {
					errorMessage.append("phone number is mandatory for " + objParty.getName());
				} else {
					customerGen = objCustomerProfiledao.addCustomer(objParty, loginScac);
					System.out.println("Customer created" + customerGen);
				}
			}
		} else {
			errorMessage.append("phone number is mandatory for " + objParty.getName());
		}

	}

	private void addEquipments(JPBillHeader objBillHeader, int billLadingId, JPBillsDAO objBillsDao) throws SQLException {
		boolean returnedVal = true;
		int packageIndex = 0;
		int cargoIndex = 0;

		for (JPEquipment objEquipment : objBillHeader.getJpequipments()) {
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
