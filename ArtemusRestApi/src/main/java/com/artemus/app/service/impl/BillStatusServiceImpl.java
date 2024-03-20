package com.artemus.app.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.artemus.app.dao.BillStatusDAO;
import com.artemus.app.exceptions.ErrorResponseException;
import com.artemus.app.exceptions.MissingRequiredFieldException;
import com.artemus.app.model.request.BillStatus;
import com.artemus.app.model.response.BillStatusResponse;
import com.artemus.app.service.BillStatusService;
import com.artemus.app.servlet.AuthorizationHeaderHolder;
import com.artemus.app.utils.ValidateBeanUtil;

public class BillStatusServiceImpl implements BillStatusService{
	static Logger logger = LogManager.getLogger();
	StringBuffer errorMessage = new StringBuffer("");
	StringBuffer updateErrorMessage = new StringBuffer("");
	int billId;
	//String authorizationHeader = AuthorizationHeaderHolder.getInstance().getAuthorizationHeader();
	//String loginScac = authorizationHeader.substring(0,4);


	@Override
	public BillStatusResponse getBillStatus(BillStatus objBillStatus, String scacCode) {
		// TODO Auto-generated method stub
		ValidateBeanUtil.buildDefaultValidatorFactory();
		StringBuffer invalidJsonMsg = ValidateBeanUtil.getConstraintViolationMsgForBillStatus(objBillStatus);
		if (invalidJsonMsg.length() > 0) {
			throw new MissingRequiredFieldException(invalidJsonMsg.toString());
		}
		BillStatusResponse objBillStatusResponse = new BillStatusResponse();
		try {
			ValidateBillNumber(objBillStatus);
			ValidateBillNumberExist(objBillStatus, scacCode);
			
			if (errorMessage.length() > 0) {
				throw new ErrorResponseException(errorMessage.toString());
			} else {
				BillStatusDAO objBillStatusDAO = new BillStatusDAO();
				try {
					// get bill details
					objBillStatusResponse = objBillStatusDAO.getbillStatus(billId,scacCode);
				} catch (Exception e) {
					e.printStackTrace();
					throw new ErrorResponseException("Internal Bill status Error");
				}finally {
					objBillStatusDAO.closeAll();
				}
			}
			
		} catch (ErrorResponseException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ErrorResponseException("Internal Bill Status Error");
		}
		return objBillStatusResponse;
	}
	private void ValidateBillNumberExist(BillStatus objBillStatus, String scacCode) {
		BillStatusDAO objBillStatusDAO = new BillStatusDAO();
		
		try {
			int billLadingId = objBillStatusDAO.validateBillExist(objBillStatus.getBillOfLading(), scacCode);
			if(billLadingId!=0) {
				billId = billLadingId;
			}else {
				errorMessage.append("Bill Number does NOT exist in Artemus System :");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			objBillStatusDAO.closeAll();
		}

	}
	private void ValidateBillNumber(BillStatus objBillStatus) {
		Pattern p = null;
		Matcher m = null;
		
		p = Pattern.compile("^[a-zA-Z0-9]+$"); // the pattern to search for
		m = p.matcher(objBillStatus.getBillOfLading());
		if (!m.matches()) {
			errorMessage.append("Bill Number must contain Alphabets and Numbers only.");
		}

	}
	@Override
	public void getBillStatus(String billNumber) {
		// TODO Auto-generated method stub
		
	}
	
}
