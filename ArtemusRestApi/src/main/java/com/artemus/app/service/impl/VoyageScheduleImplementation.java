package com.artemus.app.service.impl;

import java.util.ArrayList;

import com.artemus.app.dao.LocationDAO;
import com.artemus.app.model.request.Location;
import com.artemus.app.model.request.Voyage;

public class VoyageScheduleImplementation {

	StringBuffer errorMessage = new StringBuffer("");
	
	void CreateVoyage(Voyage objVoyage) {
		
		//validate Country
		if(validateCountry(objVoyage.getLocation()))
		{
			ValidateUnCode(objVoyage.getLocation(),objVoyage.getScacCode());
		}
		else {
			// Error Message Handling
		}
		
	}
	
	void ValidateVoyage(Voyage voyage)
	{
		
	}
	
	void ValidateLocation(Voyage voyage)
	{
		
	}
	
	boolean validateCountry(ArrayList<Location> objLocation)
	{
		
	    LocationDAO objLocationdao=new LocationDAO();
		for(Location locationbean:objLocation){
			if(objLocationdao.isExistsCountry(locationbean.getCountry())){
				System.out.println("Country exist");
			}
		   else{
			   return false;
				
			}
		}
		return true;
	
		
		
	}
	
	boolean ValidateUnCode(ArrayList<Location> objLocation,String loginScac)
	{
		LocationDAO objLocationdao=new LocationDAO();
		boolean result=false;
		for(Location locationbean:objLocation){
			if(locationbean.getCustomCode()==null){
				//customCodefromUNCode=locationCode
				String customCodefromUNCode = objLocationdao.getLocationCode(locationbean.getUnlocode(),loginScac);
				locationbean.setCustomCode(customCodefromUNCode);
				//locationbean.setName();
			}
			/*
			 * if(objLocationdao.isForeignPort(locationbean.getCustomCode())){
			 * 
			 * //locationbean.setIsCustomForeign(true); } else
			 * if(objLocationdao.isDisctrictPort(locationbean.getCustomCode())){
			 * //locationbean.setIsCustomForeign(false); } else{ //Error Meaggage Handle
			 * Part result = false; }
			 */
		}
		return result;
		
	}
	
}
