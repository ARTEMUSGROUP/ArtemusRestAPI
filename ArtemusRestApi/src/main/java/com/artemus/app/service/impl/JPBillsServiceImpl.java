package com.artemus.app.service.impl;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.artemus.app.dao.JPBillsDAO;
import com.artemus.app.dao.JPCustomerProfileDAO;
import com.artemus.app.dao.JPVesselVoyageDAO;
import com.artemus.app.exceptions.ErrorResponseException;
import com.artemus.app.model.request.BillHeader;
import com.artemus.app.model.request.JPEquipment;
import com.artemus.app.model.request.Party;
import com.artemus.app.service.JPBillsService;
import com.artemus.app.utils.JPBillHeaderUtils;

public class JPBillsServiceImpl implements JPBillsService {

	JPBillHeaderUtils objUtils = new JPBillHeaderUtils();
	StringBuffer errorMessage = new StringBuffer("");
	boolean isError;

	@Override
	public void createBill(BillHeader objBillHeader) {
		// Validate JSON
		objUtils.validateRequiredFields(objBillHeader);
		// Call for DAO
		JPCustomerProfileDAO jpcustomerProfileDao = new JPCustomerProfileDAO();
		try {
			validateBillHeaderParties(objBillHeader,jpcustomerProfileDao);
			System.out.println(objBillHeader.toString());			
			validateVesselVoyage(objBillHeader);
			System.out.println(errorMessage);
			if (errorMessage.length() > 0) {
				throw new ErrorResponseException(errorMessage.toString());
			} else {
				try {
					processBill(objBillHeader, jpcustomerProfileDao.getConnection());
				}catch (ErrorResponseException e) {
					throw e;
				} catch (Exception e) {
					throw new ErrorResponseException("Internal Bill Processing Error");
				}
			}
		} finally {
			jpcustomerProfileDao.closeAll();
		}
		
	}

	private void validateVesselVoyage(BillHeader objBillHeader) {
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
					errorMessage.append("voyageNumber does not exists :"+objBillHeader.getVesselSchedule().getVoyageNumber());
				}
			} else {
				errorMessage.append("vesselName does not exists :"+objBillHeader.getVesselSchedule().getVesselName());

			}
		} finally {
			objDao.closeAll();
		}
	}
	
	private void processBill(BillHeader objBillHeader, Connection conn) throws SQLException,ErrorResponseException {
		System.out.println("processBill :: ");
		JPBillsDAO objDao = new JPBillsDAO(conn);
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
			objDao.insertIntoConsigneeShipperDetails(objBillHeader.getConsignee(), "consignee", billLadingId);
			objDao.insertIntoConsigneeShipperDetails(objBillHeader.getNotify(), "notify", billLadingId);
			
			// Adding insertIntoNotifyPartyDetails
			//objDao.insertIntoNotifyPartyDetails(objBillHeader.getNotifyParties(), billLadingId);
			
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

	public boolean validateBillHeaderParties(BillHeader objBillHeader,JPCustomerProfileDAO objCustomerProfiledao)
	{
		System.out.println("validateBillHeaderParties ::");
		validateCustomer(objBillHeader.getShipper(), objBillHeader.getLoginScac(),objCustomerProfiledao);
		validateCustomer(objBillHeader.getConsignee(), objBillHeader.getLoginScac(),objCustomerProfiledao);
		validateCustomer(objBillHeader.getNotify(), objBillHeader.getLoginScac(),objCustomerProfiledao);
		return true;
		
	}
	
	public void validateCustomer(Party objParty,String loginScac,JPCustomerProfileDAO objCustomerProfiledao)
	{
		boolean customerGen=false;
		
		if(objParty !=null) {
			if(objCustomerProfiledao.isCustomerExists(objParty,loginScac)) {
				customerGen=true;
			}else {
				
				if(objParty.getAddressInfo().getPhoneNo() !=null || !objParty.getAddressInfo().getPhoneNo().isEmpty())
				{
				customerGen=objCustomerProfiledao.addCustomer(objParty,loginScac);
				System.out.println("Customer created"+customerGen);
				}else {
					
					errorMessage.append("phone number is mandatory for "+objParty.getName());
				}
			}
		}
		
	}
	
	private void addEquipments(BillHeader objBillHeader, int billLadingId, JPBillsDAO objBillsDao) throws SQLException {
		boolean returnedVal = true;
		int packageIndex=0;
		int cargoIndex=0;
		
		for (JPEquipment objEquipment : objBillHeader.getJpequipments()) {
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
