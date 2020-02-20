package com.artemus.app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.artemus.app.connection.DBConnectionFactory;
import com.artemus.app.model.request.BillHeader;

public class VesselVoyageDAO {
	private Connection con;
	private java.sql.PreparedStatement stmt = null;
	private ResultSet rs = null;

	public VesselVoyageDAO() {
		try {
			con = DBConnectionFactory.getConnection();
			con.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
			if (stmt != null)
				stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int validateLloydsCode(String lloydsCode, String loginScac) {
		try {
			stmt = con.prepareStatement("Select vessel_id from vessel " + " where vessel_name=? and login_scac=?");
			stmt.setString(1, lloydsCode);
			stmt.setString(2, loginScac);
			rs = stmt.executeQuery();
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int validateVoyage(String VoyageNumber, int VesselId, String loginScac) {
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(
					"Select " + " voyage_id from voyage " + " where login_scac=? and vessel_id=? and voyage_number=?");
			stmt.setString(1, loginScac);
			stmt.setInt(2, VesselId);
			stmt.setString(3, loginScac);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}

	public boolean validateDischargePort(BillHeader objBillHeader) {
		boolean isValid = false;
		try {
			stmt = con.prepareStatement(
					"(Select a.location_code,a.location_name from location a left outer join alt_location c on a.location_id=c.location_id,"
							+ " voyage_details b "
							+ " where c.alt_name like ? and a.login_scac= ? and b.location_id=a.location_id and b.voyage_id = ? )"
							+ " union"
							+ " (Select a.location_code,a.location_name from location a, voyage_details b  where a.location_name like ? "
							+ " and a.login_scac= ?  and b.location_id=a.location_id and b.voyage_id = ?)");
			stmt.setString(1, objBillHeader.getVesselSchedule().getPortOfDischarge() + "%");
			stmt.setString(2, objBillHeader.getLoginScac());
			stmt.setInt(3, objBillHeader.getVesselSchedule().getVoyageId());
			stmt.setString(4, objBillHeader.getVesselSchedule().getPortOfDischarge() + "%");
			stmt.setString(5, objBillHeader.getLoginScac());
			stmt.setInt(6, objBillHeader.getVesselSchedule().getVoyageId());
			System.out.println(stmt.toString());
			rs = stmt.executeQuery();
			if (rs.next()) {
				objBillHeader.getVesselSchedule().setPortOfDischarge(rs.getString("location_code"));
				isValid = true;
			} else {
				isValid = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isValid;
	}

	public boolean validateLoadPort(BillHeader objBillHeader) {
		boolean isValid = false;
		try {
			stmt = con.prepareStatement(
					"(Select a.location_code,a.location_name from location a left outer join alt_location c on a.location_id=c.location_id,"
							+ " voyage_details b "
							+ " where c.alt_name like ? and a.login_scac= ? and b.location_id=a.location_id and b.voyage_id = ? )"
							+ " union"
							+ " (Select a.location_code,a.location_name from location a, voyage_details b  where a.location_name like ? "
							+ " and a.login_scac= ?  and b.location_id=a.location_id and b.voyage_id = ?)");
			stmt.setString(1, objBillHeader.getVesselSchedule().getPortOfLoading() + "%");
			stmt.setString(2, objBillHeader.getLoginScac());
			stmt.setInt(3, objBillHeader.getVesselSchedule().getVoyageId());
			stmt.setString(4, objBillHeader.getVesselSchedule().getPortOfLoading() + "%");
			stmt.setString(5, objBillHeader.getLoginScac());
			stmt.setInt(6, objBillHeader.getVesselSchedule().getVoyageId());
			System.out.println(stmt.toString());
			rs = stmt.executeQuery();
			if (rs.next()) {
				objBillHeader.getVesselSchedule().setPortOfLoading(rs.getString("location_code"));
				isValid = true;
			} else {
				isValid = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isValid;
	}

}
