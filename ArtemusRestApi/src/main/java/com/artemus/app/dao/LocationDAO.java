package com.artemus.app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.artemus.app.connection.DBConnectionFactory;
import com.artemus.app.model.request.BillHeader;
import com.artemus.app.model.request.Location;
import com.artemus.app.model.request.Voyage;

public class LocationDAO {
	static Logger logger = LogManager.getLogger();
	private Connection con;
	private java.sql.PreparedStatement stmt = null, stmt2 = null, stmt3 = null, stmt4 = null;;
	private ResultSet rs = null;

	public LocationDAO(Connection connection) {
		if (connection != null) {
			con = connection;
		} else {
			con = DBConnectionFactory.getConnection();
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

	public String getLocationCode(String Unlocode, String loginScac) {
		// setting Location Code/Custom Code
		ResultSet rs = null;
		String result = "";
		try {
			stmt = con.prepareStatement("Select location_code from portcode_unlocode where unlocode=?");
			stmt.setString(1, Unlocode);
			rs = stmt.executeQuery();
			logger.info(stmt);
			if (rs.next()) {
				result = rs.getString(1);
				return result;
			} else {
				stmt2 = con.prepareStatement("SELECT location_code FROM artemus.location where unlocode=?");
				stmt2.setString(1, Unlocode);
				rs = stmt2.executeQuery();
				logger.info(stmt2);
				if (rs.next()) {
					result = rs.getString(1);
					return result;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	public void validatePort(Voyage objvoyage) {

	}

	public void insertLocation(Voyage objvoyage) {

	}

	public int getLocationId(String locationName, String loginScac) {
		try {
			stmt = con.prepareStatement("Select location_id from location where location_name=?"
					+ " and login_scac=? union Select location_id from alt_location where alt_name=?"
					+ " and login_scac=?");
			stmt.setString(1, locationName);
			stmt.setString(2, loginScac);
			stmt.setString(3, locationName);
			stmt.setString(4, loginScac);
			logger.debug(stmt);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean isExistsCountry(String objLocationName) {
		ResultSet rs = null;
		Boolean result = true;
		try {
			stmt = con.prepareStatement("Select iso_code from country where iso_code=? ");
			stmt.setString(1, objLocationName);
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

	public Boolean isForeignPort(String unCode) {
		ResultSet rs = null;
		Boolean result = true;
		try {
			stmt = con.prepareStatement("select port_name, port_code from foreign_port where port_code=? ");
			stmt.setString(1, unCode);
			rs = stmt.executeQuery();
			logger.info(stmt);
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

	public Boolean isDisctrictPort(String unlocode) {
		ResultSet rs = null;
		Boolean result = true;
		try {
			stmt = con.prepareStatement("select port_name, port_code from district_port where port_code=? ");
			stmt.setString(1, unlocode);
			rs = stmt.executeQuery();
			logger.info(stmt);
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

	public boolean isExistsLocation(Location location) {
		return false;

	}

	public int checkLocationForCustomCode(String customCode, String loginScac) {
		try {
			stmt = con.prepareStatement("Select location_id from location where location_code=? and login_scac=? ");
			stmt.setString(1, customCode);
			stmt.setString(2, loginScac);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("location_id");
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

//	public boolean checkForLocationName(String locationName, String loginScac) {
//		// TODO Auto-generated method stub
//		boolean result = true;
//		try {
//			stmt = con.prepareStatement("Select location_name from location where location_name=? and login_scac=? "
//					+ "union Select alt_name from alt_location where alt_name=? and login_scac=?");
//			stmt.setString(1, locationName);
//			stmt.setString(2, loginScac);
//			stmt.setString(3, locationName);
//			stmt.setString(4, loginScac);
//			rs = stmt.executeQuery();
//			if (rs.next()) {
//				result = true;
//			} else {
//				result = false;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}

	/*
	 * public boolean getLocationCode(Location objlocationBean,String scacCode) {
	 * boolean isValid = false; try { stmt = con.prepareStatement(
	 * "(Select a.location_code from location a left outer join alt_location c on a.location_id=c.location_id,"
	 * + " voyage_details b " +
	 * " where c.alt_name like ? and a.login_scac= ? and b.location_id=a.location_id and b.voyage_id = ? and b.is_discharge_port=true )"
	 * + " union" +
	 * " (Select a.location_code from location a, voyage_details b  where a.location_name like ? "
	 * +
	 * " and a.login_scac= ?  and b.location_id=a.location_id and b.voyage_id = ? and b.is_discharge_port=true)"
	 * ); stmt.setString(1, objlocationBean.getLocation() + "%"); stmt.setString(2,
	 * scacCode); stmt.setInt(3, objlocationBean.getVoyageId()); stmt.setString(4,
	 * objlocationBean.getLocation() + "%"); stmt.setString(5, scacCode);
	 * stmt.setInt(6, objlocationBean.getVoyageId()); rs = stmt.executeQuery();
	 * System.out.println(stmt.toString()); if (rs.next()) {
	 * objlocationBean.setLocationCode(rs.getString("location_code")); isValid =
	 * true; } else { isValid = false; } } catch (SQLException e) {
	 * e.printStackTrace(); } return isValid; }
	 */

	public boolean insert(Location locationbean, String loginScac) {
		logger.info("inside insert location...");
		boolean result = false;
		Boolean flag = true;
		try {
			stmt = con.prepareStatement(
					"select location_id from location where location_code=? and login_scac=? and location_code!=''");
			stmt.setString(1, locationbean.getCustomCode());
			stmt.setString(2, loginScac);
			rs = stmt.executeQuery();
			if (rs.next()) {
				locationbean.setLocationId(rs.getInt(1));

				stmt = con.prepareStatement(
						"select ifnull(max(sequence_number),0)+1 from alt_location where location_id=? and login_scac=?");
				stmt.setInt(1, locationbean.getLocationId());
				stmt.setString(2, loginScac);
				rs = stmt.executeQuery();
				int sequenceNumber = 1;
				if (rs.next())
					sequenceNumber = rs.getInt(1);

				stmt = con.prepareStatement("insert into alt_location values(?,?,?,?)");
				stmt.setInt(1, locationbean.getLocationId());
				stmt.setInt(2, sequenceNumber);
				stmt.setString(3, loginScac);
				stmt.setString(4, locationbean.getLocation());
				stmt.executeUpdate();
				logger.info(stmt);
				stmt2 = con.prepareStatement(
						"update location set hold_at_lp=?,location_type=? where location_id=? and login_scac=?");
				stmt2.setString(1, locationbean.getHoldAtLp());
				stmt2.setString(2, locationbean.getLocationType());
				stmt2.setInt(3, locationbean.getLocationId());
				stmt2.setString(4, loginScac);
				logger.info(stmt2);
				if (stmt2.executeUpdate() < 0)
					flag = false;

			} else {
				stmt = con.prepareStatement("INSERT INTO location (location_code,"
						+ "  login_scac, location_name,country, state, location_type, "
						+ " hold_at_lp,is_voyage_created, created_user,created_date,is_custom_foreign,unlocode ) "
						+ " Values (?,?,?,?,?,?,?,?,?,now(),?,?)", Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, locationbean.getCustomCode());
				stmt.setString(2, loginScac);
				stmt.setString(3, locationbean.getLocation());
				stmt.setString(4, locationbean.getCountry());
				stmt.setString(5, locationbean.getProvidence());
				stmt.setString(6, locationbean.getLocationType());
				stmt.setString(7, locationbean.getHoldAtLp());
				stmt.setBoolean(8, locationbean.isVoyageCreated());
				stmt.setString(9, locationbean.getCreatedUser());
				stmt.setBoolean(10, locationbean.isCustomForeign());
				stmt.setString(11, locationbean.getUnlocode());
				logger.info(stmt);
				if (stmt.executeUpdate() < 0)
					flag = false;
				else {
					rs = stmt.getGeneratedKeys();
					rs.next();
					locationbean.setLocationId(rs.getInt(1));
					logger.info(locationbean.getLocationId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			if (flag) {
				result = true;
			} else {
				result = false;
			}
		}
		return result;
	}

	public Location setLocationBean(Location locationbean) {

		if (locationbean.getCustomCode() == null)
			locationbean.setCustomCode("");
		else
			locationbean.setCustomCode(locationbean.getCustomCode());
		if (locationbean.getLocation() == null)
			locationbean.setLocation("");
		else
			locationbean.setLocation(locationbean.getLocation().trim());
		if (locationbean.getCountry() == null)
			locationbean.setCountry("");
		else
			locationbean.setCountry(locationbean.getCountry().trim());

		if (locationbean.getUnlocode() == null)
			locationbean.setUnlocode("");
		else
			locationbean.setUnlocode(locationbean.getUnlocode());

		if (locationbean.getLocationType() == null)
			locationbean.setLocationType("");
		else {
			if (locationbean.getLocationType().equalsIgnoreCase("marine"))
				locationbean.setLocationType("M");
			if (locationbean.getLocationType().equalsIgnoreCase("inland"))
				locationbean.setLocationType("M");
		}
		if (locationbean.getProvidence() == null)
			locationbean.setProvidence("");
		else
			locationbean.setProvidence(locationbean.getProvidence());
		locationbean.setVoyageCreated(false);
		locationbean.setHoldAtLp("");
		locationbean.setCreatedUser("admin");
		// locationbean.setCreatedDate("");
		// objmLocationBean.setIsCustomForeign(false);
		if (locationbean.isCustomForeign() == true)
			locationbean.setCustomForeign(true);
		else if (locationbean.isCustomForeign() == false)
			locationbean.setCustomForeign(false);
		else
			System.out.println("Error");

		return locationbean;

	}

}
