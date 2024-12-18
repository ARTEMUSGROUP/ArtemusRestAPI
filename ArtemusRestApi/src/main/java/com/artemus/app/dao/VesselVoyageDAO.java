package com.artemus.app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.artemus.app.connection.DBConnectionFactory;
import com.artemus.app.model.request.BillHeader;
import com.artemus.app.model.request.Voyage;

public class VesselVoyageDAO {
	private Connection con;
	private java.sql.PreparedStatement stmt = null;
	private ResultSet rs = null;

	public VesselVoyageDAO() {
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

	public int validateLloydsCode(String vesselName, String loginScac) {
		try {
			stmt = con.prepareStatement("Select vessel_id,usa_scac_code from vessel " + " where vessel_name=? and login_scac=?");
			stmt.setString(1, vesselName);
			stmt.setString(2, loginScac);
			rs = stmt.executeQuery();
			System.out.println(stmt);
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int validateLloydsCode(Voyage objVoyage, String loginScac) {
		try {
			stmt = con.prepareStatement("Select vessel_id,usa_scac_code from vessel " + " where vessel_name=? and login_scac=?");
			stmt.setString(1, objVoyage.getVesselName());
			stmt.setString(2, loginScac);
			rs = stmt.executeQuery();
			System.out.println(stmt);
			if (rs.next()) {
				objVoyage.setVesselScacCode(rs.getString("usa_scac_code"));
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int validateVoyage(BillHeader objBillHeader, int VesselId) {
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("Select voyage_id,vessel_scac from voyage where login_scac=? and vessel_id=? and voyage_number=?");
			stmt.setString(1, objBillHeader.getLoginScac());
			stmt.setInt(2, VesselId);
			stmt.setString(3, objBillHeader.getVesselSchedule().getVoyageNumber());
			rs = stmt.executeQuery();
			System.out.println(stmt);
			if (rs.next()) {
				objBillHeader.getVesselSchedule().setVesselScac(rs.getString("vessel_scac"));
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
					"(Select a.location_code from location a left outer join alt_location c on a.location_id=c.location_id,"
							+ " voyage_details b "
							+ " where (c.alt_name like ? or b.location_name like ?) and a.login_scac= ? and b.location_id=a.location_id and b.voyage_id = ? and b.is_discharge_port=true )"
							+ " union"
							+ " (Select a.location_code from location a, voyage_details b   where (a.location_name like ? "
							+ " or b.location_name like ?) and a.login_scac= ?  and b.location_id=a.location_id and b.voyage_id = ? and b.is_discharge_port=true)");
			stmt.setString(1, objBillHeader.getVesselSchedule().getPortOfDischarge() + "%");
			stmt.setString(2, objBillHeader.getVesselSchedule().getPortOfDischarge() + "%");
			stmt.setString(3, objBillHeader.getLoginScac());
			stmt.setInt(4, objBillHeader.getVesselSchedule().getVoyageId());
			stmt.setString(5, objBillHeader.getVesselSchedule().getPortOfDischarge() + "%");
			stmt.setString(6, objBillHeader.getVesselSchedule().getPortOfDischarge() + "%");
			stmt.setString(7, objBillHeader.getLoginScac());
			stmt.setInt(8, objBillHeader.getVesselSchedule().getVoyageId());
			rs = stmt.executeQuery();
			System.out.println(stmt.toString());
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
							+ " where (c.alt_name like ? or b.location_name like ?) and a.login_scac= ? and b.location_id=a.location_id and b.voyage_id = ? and b.is_load_port=true)"
							+ " union"
							+ " (Select a.location_code,a.location_name from location a, voyage_details b  where (a.location_name like ? "
							+ " or b.location_name like ?) and a.login_scac= ?  and b.location_id=a.location_id and b.voyage_id = ? and b.is_load_port=true )");
			stmt.setString(1, objBillHeader.getVesselSchedule().getPortOfLoading() + "%");
			stmt.setString(2, objBillHeader.getVesselSchedule().getPortOfLoading() + "%");
			stmt.setString(3, objBillHeader.getLoginScac());
			stmt.setInt(4, objBillHeader.getVesselSchedule().getVoyageId());
			stmt.setString(5, objBillHeader.getVesselSchedule().getPortOfLoading() + "%");
			stmt.setString(6, objBillHeader.getVesselSchedule().getPortOfLoading() + "%");
			stmt.setString(7, objBillHeader.getLoginScac());
			stmt.setInt(8, objBillHeader.getVesselSchedule().getVoyageId());
			rs = stmt.executeQuery();
			System.out.println(stmt.toString());
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
