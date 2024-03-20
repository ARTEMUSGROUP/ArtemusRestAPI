package com.artemus.app.service;

import com.artemus.app.model.request.BillStatus;
import com.artemus.app.model.response.BillStatusResponse;

public interface BillStatusService {
	
	public void getBillStatus(String billNumber);

	public BillStatusResponse getBillStatus(BillStatus objBillStatus, String scacCode);
}
