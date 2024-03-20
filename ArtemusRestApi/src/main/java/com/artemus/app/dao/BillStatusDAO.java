package com.artemus.app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.artemus.app.connection.DBConnectionFactory;
import com.artemus.app.model.response.BillStatusResponse;


public class BillStatusDAO {
	private Connection con;
	private java.sql.PreparedStatement stmt = null;
	private java.sql.PreparedStatement stmt1 = null, MIstmt = null, stmt2 = null;
	private ResultSet rs = null, MIrs = null;
	static Logger logger = LogManager.getLogger();

	public BillStatusDAO() {
		try {
			con = DBConnectionFactory.getConnection();
			con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
			if (MIrs != null)
				MIrs.close();
			if (stmt != null)
				stmt.close();
			if (stmt1 != null)
				stmt1.close();
			if (MIstmt != null)
				MIstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int validateBillExist(String billNumber, String loginScac) {
		int billLadingId = 0;
		try {
			stmt = con.prepareStatement(
					"select bill_lading_id from bill_header where login_scac=? and bill_lading_number = ?");
			stmt.setString(1, loginScac);
			stmt.setString(2, billNumber);
			rs = stmt.executeQuery();
			if (rs.next()) {
				billLadingId = rs.getInt("bill_lading_id");
			}
			System.out.println(stmt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return billLadingId;
	}

	public BillStatusResponse getbillStatus(int billId, String loginScac) {
		BillStatusResponse objBillStatusResponse = new BillStatusResponse();
		try{

			stmt=con.prepareStatement("select b.bill_lading_number, a.entry_description, a.processed_date, a.disposition_code from bill_detail_information a, bill_header b where a.bill_lading_id=? and a.login_scac=?" + 
					" and a.processed_date=(select MAX(processed_date) from bill_detail_information where bill_lading_id=? and login_scac=?) " + 
					" and b.bill_lading_id=?");
			stmt.setInt(1, billId);
			stmt.setString(2, loginScac);
			stmt.setInt(3, billId);
			stmt.setString(4, loginScac);
			stmt.setInt(5, billId);
			rs=stmt.executeQuery();
			if(rs.next()){
				objBillStatusResponse.setBillOfLading(rs.getString(1));
				objBillStatusResponse.setDispositionDescription(rs.getString(2));
				objBillStatusResponse.setSentDate(rs.getString(3));
			}else {
				objBillStatusResponse.setBillOfLading(rs.getString(1));
				objBillStatusResponse.setDispositionDescription("NO record found for this bill");
				objBillStatusResponse.setSentDate("");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return objBillStatusResponse;
	}
	
	
}
