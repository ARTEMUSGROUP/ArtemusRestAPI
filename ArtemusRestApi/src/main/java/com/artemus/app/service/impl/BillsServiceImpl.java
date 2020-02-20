package com.artemus.app.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

import com.artemus.app.dao.CustomerProfileDAO;
import com.artemus.app.model.request.BillHeader;

public class BillsServiceImpl {
	
	@Context
    private HttpServletRequest request;

	public void createBill(BillHeader objBillHeader)
	{
		
		
		
		
		System.out.println(objBillHeader.getLoginScac());
		
		//Call for DAO
		CustomerProfileDAO customerProfileDao=new CustomerProfileDAO();
		try {
			customerProfileDao.validateBillHeaderParties(objBillHeader);
			System.out.println(objBillHeader.toString());
			customerProfileDao.commit();
		}finally {
			customerProfileDao.closeAll();
		}
		
	}
	
}
