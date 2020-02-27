package com.artemus.app.service.impl;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.artemus.app.dao.LocationDAO;
import com.artemus.app.dao.VesselVoyageDAO;
import com.artemus.app.dao.VoyageDAO;
import com.artemus.app.exceptions.ErrorResponseException;
import com.artemus.app.exceptions.MissingRequiredFieldException;
import com.artemus.app.model.request.Location;
import com.artemus.app.model.request.PortCall;
import com.artemus.app.model.request.PortDetails;
import com.artemus.app.model.request.Voyage;
import com.artemus.app.model.response.ErrorMessages;
import com.artemus.app.service.VoyageScheduleService;
import com.artemus.app.utils.ValidateBeanUtil;

public class VoyageScheduleServiceImpl implements VoyageScheduleService {
	static Logger logger = LogManager.getLogger();
	StringBuffer errorMessage = new StringBuffer("");

	@Override
	public void createVoyage(Voyage objVoyage) {
		logger.debug(objVoyage.toString());
		// validate json
		ValidateBeanUtil.buildDefaultValidatorFactory();
		StringBuffer invalidJsonMsg = ValidateBeanUtil.getConstraintViolationMsgForVoyage(objVoyage);
		if (invalidJsonMsg.length() > 0) {
			throw new MissingRequiredFieldException(invalidJsonMsg.toString());
		}
		// validate Country
		validateCountry(objVoyage);
		// Validate UnCode
		ValidateUnCode(objVoyage, objVoyage.getScacCode());
		// VAlidate Vessel,Voyage
		validateVesselVoyage(objVoyage);
		if (errorMessage.length() > 0) {
			throw new ErrorResponseException(errorMessage.toString());
		} else {
			// Validate Location
			if (validateLocation(objVoyage, objVoyage.getScacCode())) {
				populateVoyage(objVoyage);
				validatePort(objVoyage);
				if (errorMessage.length() > 0) {
					logger.debug(errorMessage);
					throw new ErrorResponseException(errorMessage.toString());
				}
				if (!validateVoyage(objVoyage)) {
					throw new ErrorResponseException(errorMessage.toString());
				}
			}
		}

	}

	private void validateVesselVoyage(Voyage objVoyage) {
		int vesselID, voyageID;
		VesselVoyageDAO objDao = new VesselVoyageDAO();
		try {
			// Get vesselID
			vesselID = objDao.validateLloydsCode(objVoyage.getVesselName(), objVoyage.getScacCode());
			if (vesselID != 0) {
				objVoyage.setVesselId(vesselID);
				// Get voyageID
//				voyageID = objDao.validateVoyage(objVoyage.getVoyageNumber(), vesselID, objVoyage.getScacCode());
//				if (voyageID != 0) {
//					objVoyage.setVoyageId(voyageID);
//
//				}
//				else {
//					if(errorMessage.length()>0) {
//						errorMessage.append(" , ");
//					}
//					errorMessage.append("voyageNumber does not exists.");
//				}
			} else {
				if (errorMessage.length() > 0) {
					errorMessage.append(" , ");
				}
				errorMessage.append("vesselName:" + objVoyage.getVesselName() + " does not exists for scac "
						+ objVoyage.getScacCode());
			}
		} finally {
			objDao.closeAll();
		}

	}

	boolean validateVoyage(Voyage voyage) {
		logger.trace("inside validateVoyage");
		VoyageDAO objVoyageDao = new VoyageDAO();
		boolean result = false;
		try {
			if (objVoyageDao.isExist(voyage.getScacCode(), voyage.getVesselId(), voyage.getVoyageNumber())) {
				if (errorMessage.length() > 0) {
					errorMessage.append(" , ");
				}
				errorMessage.append("voyageNumber : " + voyage.getVoyageNumber() + " already exists");
				result = false;
			} else {
				if (objVoyageDao.insert(voyage)) {
					result = true;
				} else {
					errorMessage.append(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
				}
			}
		} finally {
			objVoyageDao.closeAll();
		}

		return result;
	}

	boolean validateLocation(Voyage objVoyage, String loginScac) {
		logger.info("inside validateLocation");
		boolean result = false;
		LocationDAO objLocationdao = new LocationDAO(null);
		try {
			for (PortDetails objPortDetail : objVoyage.getPortDetails()) {
				Location locationbean = objPortDetail.getLocation();
				// Setting locationBean
				locationbean = objLocationdao.setLocationBean(locationbean);
				int locationId = objLocationdao.checkLocationForCustomCode(locationbean.getCustomCode(), loginScac);
				logger.info("locationId :"+locationId);
				if (locationId == 0) {
					if (locationbean.getLocation() == null || locationbean.getLocation().isEmpty()) {
						result = false;
						break;
					} else {
						if (objLocationdao.insert(locationbean, loginScac)) {
							result = true;
						} else {
							result = false;
							break;
						}
					}
				} else {
					result = true;
					locationbean.setLocationIndex(locationId);
					locationbean.setLocationId(locationId);
					
				}
			}
		} finally {
			objLocationdao.closeAll();
		}
		return result;
	}

	boolean validateCountry(Voyage objVoyage) {
		LocationDAO objLocationDao = new LocationDAO(null);
		try {
			for (PortDetails objPortDetail : objVoyage.getPortDetails()) {
				Location locationbean = objPortDetail.getLocation();
				if (objLocationDao.isExistsCountry(locationbean.getCountry())) {
					System.out.println("Country exist");
				} else {
					if (errorMessage.length() > 0) {
						errorMessage.append(" , ");
					}
					errorMessage.append("country : " + locationbean.getCountry() + " does not exists");
					return false;
				}
			}
		} finally {
			objLocationDao.closeAll();
		}
		return true;
	}

	private void populateVoyage(Voyage objVoyage) {
		logger.info("Inside populate Voyage");

		if (objVoyage.getCrewMembers() == null)
			objVoyage.setCrewMembers("");
		else
			objVoyage.setCrewMembers(objVoyage.getCrewMembers());
		if (objVoyage.getPassengers() == null)
			objVoyage.setPassengers("");
		else
			objVoyage.setPassengers(objVoyage.getPassengers());
		if (objVoyage.getReportNumber() == null)
			objVoyage.setReportNumber("");
		else
			objVoyage.setReportNumber(objVoyage.getReportNumber());
		if (objVoyage.getScacCode() == null)
			objVoyage.setScacCode("");

	}

	private void validatePort(Voyage objVoyage) {
		logger.trace("inside validatePort");
		LocationDAO objLocationDAO = new LocationDAO(null);
		try {
			boolean result;
			ArrayList<Location> objLocationbean = new ArrayList<Location>();
			for (PortDetails portCall : objVoyage.getPortDetails()) {
				objLocationbean.add(portCall.getLocation());
			}
			ArrayList<PortDetails> objmPortDetailsBeans = new ArrayList<PortDetails>();
			ArrayList<PortDetails> objPortCallbean = objVoyage.getPortDetails();
			boolean portValidation = false;
			for (PortDetails portCall : objVoyage.getPortDetails()) {
				if (portCall.getDischarge() == true) {
					portValidation = true;
					break;
				}
			}

			if (!portValidation) {
				errorMessage.append("discharge : Voyage should have atleast one discharge");
				result = false;
			}
			portValidation = false;
			for (PortDetails portCall : objVoyage.getPortDetails()) {
				if (portCall.getLoad() == true) {
					portValidation = true;
					break;
				}
			}
			if (!portValidation) {
				result = false;
				errorMessage.append("load: Voyage should have atleast one load");
			}

			portValidation = false;
			for (PortDetails portCall : objVoyage.getPortDetails()) {
				if (portCall.getLoad() == true && portCall.getDischarge() == true) {
					portValidation = true;
					break;
				}
			}
			if (portValidation) {
				result = false;
				errorMessage.append("load: Voyage should not have a location with same discharge Port and load Port ");
			}

			portValidation = false;
			for (PortDetails portCall : objVoyage.getPortDetails()) {
				if (portCall.getLastLoadPort() == true && portCall.getDischarge() == true) {
					portValidation = true;
					break;
				}
			}

			if (portValidation) {
				errorMessage.append(
						"lastLoadPort ,discharge: Voyage should not have a location with same discharge port and lastLoadPort ");
				result = false;
			}

			portValidation = false;
			int lastLoadPortCount = 0;
			for (PortDetails portCall : objVoyage.getPortDetails()) {
				if (portCall.getLastLoadPort() == true) {
					portValidation = true;
					lastLoadPortCount++;
					break;
				}
			}
			if (lastLoadPortCount > 1) {
				errorMessage.append("lastLoadPort : only one lastLoadPort must be true");
			}
			if (!portValidation) {
				errorMessage.append("lastLoadPort : Voyage should have atleast one lastLoadPort");
				result = false;
			} else {

				String lastloaddate;
				String dischargeDate;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date lastloaddate1 = null;
				java.util.Date dichargedate1 = null;
				for (PortDetails portCall : objVoyage.getPortDetails()) {
					if (portCall.getLastLoadPort() == true) {
						lastloaddate = portCall.getArrivalDate();
						try {
							lastloaddate1 = sdf.parse(lastloaddate);
						} catch (Exception e) {
							System.out.println("Date is not in correct format");
							errorMessage.append(
									"arrivalDate of lastLoadPort : is not in correct format, correct format is YYYY-MM-DD");
							e.printStackTrace();
							result = false;
						}

						break;
					}

				}

				for (PortDetails portCall : objVoyage.getPortDetails()) {
					if (portCall.getDischarge() == true) {
						dischargeDate = portCall.getArrivalDate();
						try {
							dichargedate1 = sdf.parse(dischargeDate);
						} catch (Exception e) {
							System.out.println("Date is not in correct format");
							errorMessage.append("arrivalDate : is not in correct format, correct format is YYYY-MM-DD");
							e.printStackTrace();
							result = false;
							break;
						}
						if (dichargedate1.compareTo(lastloaddate1) >= 0
								|| dichargedate1.compareTo(lastloaddate1) == 0) {
							result = true;
						} else {
							if (errorMessage.length() > 0) {
								errorMessage.append(" , ");
							}
							// Error message
							errorMessage.append(" 'arrivalDate' should not be less than 'sailingDate' please check 'portDetails' ");

//							if ((objVoyage.getLocations().size()) < portCall.getLocationIndex()) {
//								if (errorMessage.length() > 0) {
//									errorMessage.append(" , ");
//								}
//								// Error message
//								errorMessage.append(
//										"locationIndex : " + portCall.getLocationIndex() + "in a PortCall is invalid");
//								result = false;
//								break;
//							} else {
//								if (errorMessage.length() > 0) {
//									errorMessage.append(" , ");
//								}
//								// Error message
//								errorMessage.append("arrivalDate : "
//										+ objVoyage.getLocations().get(portCall.getLocationIndex() - 1).getLocation()
//										+ " should not be less than sailingDate ");
//
//								result = false;
//								break;
//							}
						}
					}
				}

				for (PortDetails portCallbean : objPortCallbean) {
					logger.info(objPortCallbean.toString());
					PortDetails objPortDetailsBean = new PortDetails();
					objPortDetailsBean.setArrivalDate(portCallbean.getArrivalDate());
					objPortDetailsBean.setSailingDate(portCallbean.getSailingDate());
					objPortDetailsBean.setLoad(portCallbean.getLoad());
					objPortDetailsBean.setDischarge(portCallbean.getDischarge());
					objPortDetailsBean.setLastLoadPort(portCallbean.getLastLoadPort());
					objPortDetailsBean.setLocationIndex(portCallbean.getLocation().getLocationId());
					Location objLocation = new Location();
					objLocation.setLocationId(portCallbean.getLocation().getLocationId());
					objLocation.setLocation(portCallbean.getLocation().getLocation());
					objPortDetailsBean.setLocation(objLocation);
					objPortDetailsBean.setTerminal("");
					objmPortDetailsBeans.add(objPortDetailsBean);
				}

				objVoyage.setPortDetails(objmPortDetailsBeans);
			}
		} finally {
			objLocationDAO.closeAll();
		}

	}

	boolean ValidateUnCode(Voyage objVoyage, String loginScac) {
		LocationDAO objLocationdao = new LocationDAO(null);
		boolean result = true;
		try {
			for (PortDetails objPortDetail : objVoyage.getPortDetails()) {
				Location locationbean = objPortDetail.getLocation();
				objLocationdao.setLocationBean(locationbean);

				if (locationbean.getCustomCode() == null) {
					// setting customCode from locationCode
					String customCodefromUNCode = objLocationdao.getLocationCode(locationbean.getUnlocode(), loginScac);
					locationbean.setCustomCode(customCodefromUNCode);
				}

				if (objLocationdao.isForeignPort(locationbean.getCustomCode())) {
					locationbean.setCustomForeign(true);
				} else if (objLocationdao.isDisctrictPort(locationbean.getCustomCode())) {
					locationbean.setCustomForeign(false);
				} else {
					// Error Message Handle
					if (errorMessage.length() > 0) {
						errorMessage.append(" , ");
					}
					if (locationbean.getUnlocode() != null && (!locationbean.getUnlocode().isEmpty())) {
						errorMessage
								.append("customCode for unlocode : " + locationbean.getUnlocode() + " does not exists");
					} else {
						errorMessage.append("customCode : does not exists");
					}

					result = false;
				}
			}
		} finally {
			objLocationdao.closeAll();
		}
		return result;
	}

}
