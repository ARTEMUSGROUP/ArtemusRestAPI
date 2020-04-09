package com.artemus.app.service;

import com.artemus.app.model.request.JPBillHeader;

public interface JPBillsService {
	public void createBill(JPBillHeader objBillHeader);
	public void updateBill(JPBillHeader objBillHeader);
}
