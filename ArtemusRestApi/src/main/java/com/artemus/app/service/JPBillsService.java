package com.artemus.app.service;

import com.artemus.app.model.request.BillHeader;

public interface JPBillsService {
	public void createBill(BillHeader objBillHeader);
	public void updateBill(BillHeader objBillHeader);
}
