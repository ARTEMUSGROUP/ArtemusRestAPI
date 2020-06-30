package com.artemus.app.service.impl;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.artemus.app.dao.VesselDAO;
import com.artemus.app.exceptions.ErrorResponseException;
import com.artemus.app.exceptions.MissingRequiredFieldException;
import com.artemus.app.model.request.Vessel;
import com.artemus.app.service.VesselScheduleService;
import com.artemus.app.utils.ValidateBeanUtil;

public class VesselScheduleServiceImpl implements VesselScheduleService {

	static Logger logger = LogManager.getLogger();
	StringBuffer errorMessage = new StringBuffer("");
	StringBuffer updateErrorMessage = new StringBuffer("");

	@Override
	public void createVessel(Vessel objVessel) {
		// validate json
		ValidateBeanUtil.buildDefaultValidatorFactory();
		StringBuffer invalidJsonMsg = ValidateBeanUtil.getConstraintViolationMsgForVessel(objVessel);
		if (invalidJsonMsg.length() > 0) {
			throw new MissingRequiredFieldException(invalidJsonMsg.toString());
		}
		try {
			// Validate Required fields
			ValidateFields(objVessel);
			// Validate Vessel
			validateVessel(objVessel);
			// Validate LloydsCode
			vlidateLloydsCode(objVessel);
			// validate Japan Carrier Code
			if (objVessel.getJpnCarrierCode() != "") {
				validateJpnCarrierCode(objVessel);
			}
			if (errorMessage.length() > 0) {
				throw new ErrorResponseException(errorMessage.toString());
			} else {
				// Create Vessel
				try {
					processVessel(objVessel);
				} catch (Exception e) {
					e.printStackTrace();
					throw new ErrorResponseException("Internal Bill Processing Error");

				}

			}
		} catch (ErrorResponseException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ErrorResponseException("Internal Bill Processing Error");

		}

	}

	@Override
	public void updateVessel(Vessel objVessel) {

		// validate json
		ValidateBeanUtil.buildDefaultValidatorFactory();
		StringBuffer invalidJsonMsg = ValidateBeanUtil.getConstraintViolationMsgForVessel(objVessel);
		if (invalidJsonMsg.length() > 0) {
			throw new MissingRequiredFieldException(invalidJsonMsg.toString());
		}
// Validate Vessel
		validateVessel(objVessel);
// Validate LloydsCode
//		vlidateLloydsCode(objVessel);
		// validate Japan Carrier Code
		if (objVessel.getJpnCarrierCode() != "") {
			validateJpnCarrierCode(objVessel);
		}
		if (updateErrorMessage.length() > 0) {
			throw new ErrorResponseException(updateErrorMessage.toString());
		} else {
			// Create Vessel
			try {
				processVesselUpdate(objVessel);
			}catch (Exception e) {
				e.printStackTrace();
				throw new ErrorResponseException("Internal Bill Processing Error");

			}

		}

	}

	private void validateJpnCarrierCode(Vessel objVessel) {
		boolean isExists;
		VesselDAO objDao = new VesselDAO();
		try {
			// Get vesselID
			isExists = objDao.getJapanCarrierCode(objVessel.getJpnCarrierCode());
			if (isExists) {
				System.out.println("japan Carrier Code exists.");
			} else {
				System.out.println("japan Carrier Code does not exists.");
				if (errorMessage.length() > 0) {
					errorMessage.append(" , ");
				}
				errorMessage.append("jpnCarrierCode: " + objVessel.getJpnCarrierCode() + " does not exists for scac "
						+ objVessel.getLoginScac());
				if (updateErrorMessage.length() > 0) {
					updateErrorMessage.append(" , ");
				}
				updateErrorMessage.append("jpnCarrierCode: " + objVessel.getJpnCarrierCode() + " does not exists for scac "
						+ objVessel.getLoginScac());
			}
		} finally {
			objDao.closeAll();
		}

	}

	private void vlidateLloydsCode(Vessel objVessel) {
		boolean isExists;
		VesselDAO objDao = new VesselDAO();
		try {
			// Get vesselID
			isExists = objDao.isExistLloydsCode(objVessel.getLloydsCode(), objVessel.getLoginScac());
			if (isExists) {
				System.out.println("lloydsCode exists.");
				if (errorMessage.length() > 0) {
					errorMessage.append(" , ");
				}
				errorMessage.append(
						"lloydsCode: " + objVessel.getLloydsCode() + " exists for scac " + objVessel.getLoginScac());
			} else {
				System.out.println("lloydsCode does not exists.");
				if (updateErrorMessage.length() > 0) {
					updateErrorMessage.append(" , ");
				}
				updateErrorMessage.append("lloydsCode: " + objVessel.getLloydsCode() + " does not exists for scac "
						+ objVessel.getLoginScac());
			}
		} finally {
			objDao.closeAll();
		}

	}

	private void processVessel(Vessel objVessel) throws SQLException, ErrorResponseException {
		VesselDAO objDao = new VesselDAO();
		try {
			if (objDao.insert(objVessel).equalsIgnoreCase("Success")) {
				objDao.commit();
			}
		} finally {
			objDao.closeAll();
		}
	}

	private void validateVessel(Vessel objVessel) {
		int vesselID;
		VesselDAO objDao = new VesselDAO();
		try {
			// Get vesselID
			vesselID = objDao.validateVessel(objVessel.getVesselName(), objVessel.getLoginScac());
			if (vesselID != 0) {
				objVessel.setVesselId(vesselID);
				System.out.println("Vessel Exists.");
				if (errorMessage.length() > 0) {
					errorMessage.append(" , ");
				}
				errorMessage.append("vesselName: " + objVessel.getVesselName() + " exists for scac "
						+ objVessel.getLoginScac());
			} else {
				System.out.println("Vessel does not Exists.");
				if (updateErrorMessage.length() > 0) {
					updateErrorMessage.append(" , ");
				}
				updateErrorMessage.append("vesselName: " + objVessel.getVesselName() + " does not exists for scac "
						+ objVessel.getLoginScac());
			}
		} finally {
			objDao.closeAll();
		}

	}

	private void processVesselUpdate(Vessel objVessel) throws SQLException, ErrorResponseException {
		VesselDAO objDao = new VesselDAO();
		try {
			if (objDao.update(objVessel).equalsIgnoreCase("Success"))
			{
				objDao.commit();
			}

		} finally {
			objDao.closeAll();
		}
	}

	private void ValidateFields(Vessel objVessel) {
		Pattern p = null;
		Matcher m = null;

		p = Pattern.compile("^[\\w\\d_.-]+$"); // the pattern to search for
		m = p.matcher(objVessel.getVesselName());
		if (!m.matches()) {
			errorMessage.append("Vessel Name must contain Alphabets only.");
		}

		p = Pattern.compile("^[0-9]{1,7}$"); // the pattern to search for
		m = p.matcher(objVessel.getLloydsCode());
		if (!m.matches()) {
			errorMessage.append("lloydsCode must contain numbers only.");
		}
	}

}
