package com.artemus.app.service;

import com.artemus.app.model.request.Vessel;

public interface VesselScheduleService {
	public void createVessel(Vessel objVessel);

	public void updateVessel(Vessel objRequest);
}
