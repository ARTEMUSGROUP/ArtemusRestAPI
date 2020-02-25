package com.artemus.app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.artemus.app.connection.DBConnectionFactory;
import com.artemus.app.model.request.Voyage;

public class VoyageDAO {

	private Connection con;
	private java.sql.PreparedStatement stmt = null;
	private ResultSet rs = null;

	public VoyageDAO() {
		con = DBConnectionFactory.getConnection();
		try {
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

	public boolean isExist(String LoginScac, int vesselID, String voyageNumber) {
		ResultSet rs = null;
		Boolean result = true;
		try {
			stmt = con.prepareStatement("Select " + " voyage_id, login_scac, voyage_number, vessel_id, vessel_scac,"
					+ " crew_members, passengers, report_number, created_user, created_date" + " from voyage "
					+ " where login_scac=? and vessel_id=? and voyage_number=?");
			stmt.setString(1, LoginScac);
			stmt.setInt(2, vesselID);
			stmt.setString(3, voyageNumber);
			rs = stmt.executeQuery();
			if (rs.next()) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	void insertVoyageDatails(Voyage voyage) {

	}

	public boolean insert(Voyage objmVoyageBean) {

		String result = "";
		int voyageId = 0;
		try {
			stmt = con.prepareStatement("Insert into voyage "
					+ "(login_scac, voyage_number, vessel_id, vessel_scac, crew_members,"
					+ " passengers, report_number, created_user, created_date)" + " VALUES (?,?,?,?,?,?,?,?,now()) ",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, objmVoyageBean.getScacCode());
			stmt.setString(2, objmVoyageBean.getVoyageNumber());
			stmt.setInt(3, objmVoyageBean.getVesselId());
			stmt.setString(4, objmVoyageBean.getScacCode());
			stmt.setString(5, objmVoyageBean.getCrewMembers());
			stmt.setString(6, objmVoyageBean.getPassengers());
			stmt.setString(7, objmVoyageBean.getReportNumber());
			stmt.setString(8, "admin");

			stmt.execute();
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				voyageId = rs.getInt(1);
			}
			stmt = con.prepareStatement(
					"Insert into voyage_details " + "(login_scac, voyage_id, location_id, is_last_load_port, "
							+ " terminal, sailing_date, arrival_date, is_load_port, "
							+ " is_discharge_port, location_name)" + " values(?,?,?,?,?,?,?,?,?,?)");

			stmt.setString(1, objmVoyageBean.getScacCode());
			stmt.setInt(2, voyageId);
			for (int i = 0; i < objmVoyageBean.getPortDetails().size(); i++) {
				stmt.setInt(3, objmVoyageBean.getPortDetails().get(i).getLocationIndex());
				stmt.setBoolean(4, objmVoyageBean.getPortDetails().get(i).getLastLoadPort());
				stmt.setString(5, objmVoyageBean.getPortDetails().get(i).getTerminal());
				stmt.setString(6, objmVoyageBean.getPortDetails().get(i).getSailingDate());
				stmt.setString(7, objmVoyageBean.getPortDetails().get(i).getArrivalDate());
				stmt.setBoolean(8, objmVoyageBean.getPortDetails().get(i).getLoad());
				stmt.setBoolean(9, objmVoyageBean.getPortDetails().get(i).getDischarge());
				stmt.setString(10, objmVoyageBean.getLocations().get(i).getLocation());
				stmt.execute();

//				stmt1= con.prepareStatement("update location " +
//						"set is_voyage_created=true " +
//						" where location_id=? and login_scac=?");
//				
//				stmt1.setInt(1, objmVoyageBean.getObjmPortDetailsBeans().get(i).getLocationId());
//				stmt1.setString(2, objmVoyageBean.getLoginScac());
//				stmt1.executeUpdate();
			}

			stmt = con.prepareStatement(
					"update vessel " + "set is_voyage_created=true " + " where vessel_id=? and login_scac=?");

			stmt.setInt(1, objmVoyageBean.getVesselId());
			stmt.setString(2, objmVoyageBean.getScacCode());
			stmt.executeUpdate();

			result = "Success";
		} catch (SQLException e) {
			e.printStackTrace();
			result = "Exception";
		} catch (Exception e) {
			e.printStackTrace();
			result = "Exception";
		} finally {
			try {
				if (!result.equals("Success")) {
					con.rollback();
					result = "Fail";
				} else {
					con.commit();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}

			if (result.equals("Success")) {
				return true;
			}
		}return false;
	}
}
