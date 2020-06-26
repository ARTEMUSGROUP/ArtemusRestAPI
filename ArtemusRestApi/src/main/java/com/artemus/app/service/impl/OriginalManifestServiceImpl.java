package com.artemus.app.service.impl;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.javascript.ObjToIntMap;

import com.artemus.app.dao.OriginalManifestDAO;
import com.artemus.app.exceptions.ErrorResponseException;
import com.artemus.app.exceptions.MissingRequiredFieldException;
import com.artemus.app.model.request.AddOriginalManifest;
import com.artemus.app.model.response.ErrorMessages;
import com.artemus.app.service.OriginalManifestService;
import com.artemus.app.utils.ValidateBeanUtil;

public class OriginalManifestServiceImpl implements OriginalManifestService {

	StringBuilder errorMessage = new StringBuilder("");
	StringBuilder entityErrorMessage = new StringBuilder("");
	static Logger logger = LogManager.getLogger();
	boolean isError;

	@Override
	public void sentBillToCustoms(AddOriginalManifest objManifest) {
		// Validate JSON
		logger.debug(objManifest.toString());
		ValidateBeanUtil.buildDefaultValidatorFactory();
		StringBuffer invalidJsonMsg = ValidateBeanUtil.getConstraintViolationMsgForAddingManifest(objManifest);
		if (invalidJsonMsg.length() > 0) {
			throw new MissingRequiredFieldException(invalidJsonMsg.toString());
		}
		// Validation of Port Locations
		validate(objManifest);
		// Validation of Vessel and Voyage
		validateVesselVoyage(objManifest);
		// Validate LocationCode
		ValidateLocationCode(objManifest, objManifest.getLoginScac());
		// Validate Location Names
		validateLocation(objManifest, objManifest.getLoginScac());

		validateErrorBills(objManifest);
		if (errorMessage.length() > 0) {
			throw new ErrorResponseException(errorMessage.toString());
		}
		// Insert into AMS Sent
		try {
			processBill(objManifest);
		} catch (ErrorResponseException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (errorMessage.length() > 0) {
			throw new ErrorResponseException(errorMessage.toString());
		}
	}

	private void processBill(AddOriginalManifest objManifest) throws ErrorResponseException, SQLException {
		boolean isExists = true;
		OriginalManifestDAO objManifestdao = new OriginalManifestDAO();
		try {
			isExists = objManifestdao.validateBillExists(objManifest);
			if (!isExists) {
				errorMessage.append("No Bills exists for the entered Vessel and Voyage");
			} else {
				update(objManifest);
			}
		} finally {
			objManifestdao.closeAll();
		}

	}

	private void validateLocation(AddOriginalManifest objManifest, String loginScac) {
		String loadPortCode = "", dischargePortCode = "";
		OriginalManifestDAO objManifestdao = new OriginalManifestDAO();
		try {
			if (!objManifest.getLoadPortLocation().isEmpty()) {
				loadPortCode = objManifestdao.getLocationCode(objManifest.getLoadPortLocation(), loginScac);
				logger.info(objManifest.getLoadPortLocation());
				if (loadPortCode == "") {
					errorMessage.append("Location Code for Load Port Location " + objManifest.getLoadPortLocation()
							+ " does not exists.");
				} else {
					objManifest.setLoadPortCustomCode(loadPortCode);
				}

			}
			if (!objManifest.getDischargePortLocation().isEmpty()) {
				dischargePortCode = objManifestdao.getLocationCode(objManifest.getDischargePortLocation(), loginScac);
				logger.info(objManifest.getDischargePortLocation());
				if (dischargePortCode == "") {
					errorMessage.append("Location Code for Discharge Port Location " + objManifest.getLoadPortLocation()
							+ " does not exists.");
				} else {
					objManifest.setDischargePortCustomCode(dischargePortCode);
				}

			}
		} finally {
			objManifestdao.closeAll();
		}

	}

	private void validate(AddOriginalManifest objManifest) {
		if (objManifest.getLoadPortLocation().isEmpty() && objManifest.getLoadPortCustomCode().isEmpty()) {
			errorMessage.append("Either Load Port Location name or Load Port Custom code must be entered.");
		}
		if (objManifest.getDischargePortLocation().isEmpty() && objManifest.getDischargePortCustomCode().isEmpty()) {
			errorMessage.append("Either Discharge Port Location name or Discharge Port Custom code must be entered.");
		}

	}

	private void update(AddOriginalManifest objManifest) throws SQLException, ErrorResponseException {
		String result = "", amsBillError = "";
		OriginalManifestDAO objManifestDao = new OriginalManifestDAO();
		try {
			result = objManifestDao.update(objManifest, objManifest.getLoginScac(), objManifestDao);
			if (result == "Success") {
				result = objManifestDao.updateFOBE(objManifest, objManifest.getLoginScac(), objManifestDao);
			} else {
				errorMessage.append(objManifestDao.getErrorMessage());
			}
			if (result == "Success") {

				objManifestDao.commit();
			} else {
				errorMessage.append(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
			}
		} finally {
			objManifestDao.closeAll();
		}

	}

	private void validateErrorBills(AddOriginalManifest objManifest) {
		OriginalManifestDAO objManifestDao = new OriginalManifestDAO();
		int billError = 0;
		try {

			billError = objManifestDao.getErrorBill(objManifest.getVoyageId(), objManifest.getLoadPortCustomCode(),
					objManifest.getDischargePortCustomCode(), objManifest.getLoginScac());
			if (billError == 1) {
				if (errorMessage.length() > 0) {
					errorMessage.append(" , ");
				}
				errorMessage.append(" Bills with " + objManifestDao.getErrorMessage());
			}

		} finally {
			objManifestDao.closeAll();
		}

	}

	private void ValidateLocationCode(AddOriginalManifest objManifest, String loginScac) {
		OriginalManifestDAO objManifestDao = new OriginalManifestDAO();
		String locationLoadPort = "";
		String locationDischargePort = "";
		try {
			if (objManifest.getLoadPortCustomCode() != "") {

				locationLoadPort = objManifestDao.getLocationIdfromUnlocode(objManifest.getLoadPortCustomCode(),
						loginScac);
				if (locationLoadPort != "") {
					objManifest.setLoadPortLocation(locationLoadPort);
				} else {
					errorMessage.append("customCode : " + objManifest.getLoadPortCustomCode() + " does not exists");
				}
			}
			if (objManifest.getDischargePortCustomCode() != "") {

				locationDischargePort = objManifestDao
						.getLocationIdfromUnlocode(objManifest.getDischargePortCustomCode(), loginScac);
				if (locationDischargePort != "") {
					objManifest.setDischargePortLocation(locationDischargePort);
				} else {
					errorMessage
							.append("customCode : " + objManifest.getDischargePortCustomCode() + " does not exists");
				}

			}
		} finally {
			objManifestDao.closeAll();
		}

	}

	private void validateVesselVoyage(AddOriginalManifest objManifest) {
		System.out.println("validateVesselVoyage ::");
		OriginalManifestDAO objDao = new OriginalManifestDAO();
		int vesselID, voyageID;
		try {
			// Get vesselID
			vesselID = objDao.validateVessel(objManifest.getVesselName(), objManifest.getLoginScac());
			if (vesselID != 0) {
				objManifest.setVesselId(vesselID);
				// Get voyageID
				voyageID = objDao.validateVoyage(objManifest, vesselID);
				if (voyageID != 0) {
					objManifest.setVoyageId(voyageID);
					if (!objManifest.getDischargePortLocation().isEmpty()
							&& !objManifest.getLoadPortLocation().isEmpty()) {
						// validateDischargePort
						if (!objDao.validateDischargePort(objManifest)) {
							errorMessage.append("portOfDischarge does not exists.");
						}
						// validateLoadPort
						if (!objDao.validateLoadPort(objManifest)) {
							errorMessage.append("portOfLoading does not exists.");
						}
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

}
