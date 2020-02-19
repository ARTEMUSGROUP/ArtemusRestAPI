package com.artemus.app.service.impl;

import com.artemus.app.model.request.BillHeader;
import com.artemus.app.service.BillsService;
import com.artemus.app.utils.BillHeaderUtils;

public class BillsServiceImpl implements BillsService{
	BillHeaderUtils objUtils = new BillHeaderUtils();

	public void createBill(BillHeader objBillHeader) {
		// Validate JSON
		objUtils.validateRequiredFields(objBillHeader);
		// Call for DAO
	}
	
}
