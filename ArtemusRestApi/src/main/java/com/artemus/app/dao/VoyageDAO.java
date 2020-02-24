package com.artemus.app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.artemus.app.connection.DBConnectionFactory;
import com.artemus.app.model.request.Voyage;

public class VoyageDAO {
	
	private Connection con;
	private java.sql.PreparedStatement stmt = null;
	private ResultSet rs = null;
	
	VoyageDAO()
	{
		con = DBConnectionFactory.getConnection();
	}
	
	public void commit() {
		try {
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeAll() {
		try {
			if (con != null)
				con.close();
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	boolean isExistsVesselVoyage(Voyage voyage)
	{
		return false;
		
	}
	
	void insertVoyageDatails(Voyage voyage)
	{
		
	}
	
}
