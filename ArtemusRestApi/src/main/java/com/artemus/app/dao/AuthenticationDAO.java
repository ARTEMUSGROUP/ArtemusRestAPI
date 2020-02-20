package com.artemus.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.artemus.app.connection.DBConnectionFactory;

public class AuthenticationDAO {

	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	
	public AuthenticationDAO() {
		conn = DBConnectionFactory.getConnection();;
	}

	public void closeAll() {
		try {
			if (conn != null)
				conn.close();
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean checkUserAuthentication(String scacCode, String extractedToken) throws SQLException {
		boolean isChecked = false;
		String query = "SELECT login_scac, token FROM login_scac_token where login_scac = ? and token = ?";
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, scacCode);
		pstmt.setString(2, extractedToken);
		System.out.println("Query: "+ pstmt.toString());
		rs = pstmt.executeQuery();
		if (rs.next()) {
			isChecked = true;
		}
		return isChecked;
	}
	


}
