package com.artemus.app.dao;

import java.sql.Connection;
import java.sql.ResultSet;

import com.artemus.app.model.request.Location;
import com.artemus.app.model.request.Voyage;

public class LocationDAO {

	private Connection con;
	private java.sql.PreparedStatement stmt = null;
	private ResultSet rs = null;
	
	public String getLocationCode(String Unlocode,String loginScacs)
	{
		return loginScacs;
		// set Location Code/Custom Code
		
	}
	
	public void validatePort(Voyage objvoyage)
	{
		
	}
	
	public void insertLocation(Voyage objvoyage)
	{
		
	}
	
	public boolean isExistsCountry(String objlocationName)
	{
		ResultSet rs=null;
		Boolean result=true;
		try{
			stmt = con.prepareStatement("Select iso_code from country where iso_code=? ");
			stmt.setString(1, objlocationName);
			rs=stmt.executeQuery();
			if (rs.next()){
				result=true;
		}else{
			result=false;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
		
	}
	
	public boolean isExistsLocation(Location location) 
	{
		return false;
		
	}
	
}
