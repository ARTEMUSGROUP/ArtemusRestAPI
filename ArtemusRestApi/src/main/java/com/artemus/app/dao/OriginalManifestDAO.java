package com.artemus.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.artemus.app.connection.DBConnectionFactory;
import com.artemus.app.model.request.AddOriginalManifest;

public class OriginalManifestDAO {

	private Connection con;
	private java.sql.PreparedStatement stmt = null, pstmt = null, pstmt1 = null, pstmt2 = null, pstmt3 = null,
			pstmt4 = null, pstmt5 = null;
	private java.sql.PreparedStatement stmt1 = null;
	private ResultSet rs = null, rs1 = null;
	static Logger logger = LogManager.getLogger();
	StringBuffer errorMessage = new StringBuffer("");

	public OriginalManifestDAO() {
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
			if (stmt != null)
				stmt.close();
			if (stmt1 != null)
				stmt1.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return con;
	}

	public int validateVessel(String vesselName, String loginScac) {
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement("Select vessel_id, vessel_name,usa_scac_code  "
					+ " from vessel where vessel_name like ? and login_scac=? and is_voyage_created = true");
			stmt.setString(1, vesselName + "%");
			stmt.setString(2, loginScac);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int validateVoyage(AddOriginalManifest objManifest, int vesselID) {
		ResultSet rs = null;
		try {

			stmt = con.prepareStatement(
					"SELECT voyage_id FROM artemus.voyage where vessel_id=? and login_scac=? and voyage_number=?");
			stmt.setInt(1, vesselID);
			stmt.setString(2, objManifest.getLoginScac());
			stmt.setString(3, objManifest.getVoyageNumber());
			logger.info(stmt.toString());
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean validateDischargePort(AddOriginalManifest objManifest) {
		boolean isValid = false;
		try {
			stmt = con.prepareStatement(
					"(Select a.location_code from location a left outer join alt_location c on a.location_id=c.location_id,"
							+ " voyage_details b "
							+ " where c.alt_name like ? and a.login_scac= ? and b.location_id=a.location_id and b.voyage_id = ? and b.is_discharge_port=true )"
							+ " union"
							+ " (Select a.location_code from location a, voyage_details b  where a.location_name like ? "
							+ " and a.login_scac= ?  and b.location_id=a.location_id and b.voyage_id = ? and b.is_discharge_port=true)");
			stmt.setString(1, objManifest.getDischargePortLocation() + "%");
			stmt.setString(2, objManifest.getLoginScac());
			stmt.setInt(3, objManifest.getVoyageId());
			stmt.setString(4, objManifest.getDischargePortLocation() + "%");
			stmt.setString(5, objManifest.getLoginScac());
			stmt.setInt(6, objManifest.getVoyageId());
			rs = stmt.executeQuery();
			System.out.println(stmt.toString());
			if (rs.next()) {
				objManifest.setDischargePortCustomCode(rs.getString("location_code"));
				isValid = true;
			} else {
				isValid = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isValid;
	}

	public boolean validateLoadPort(AddOriginalManifest objManifest) {
		boolean isValid = false;
		try {
			stmt = con.prepareStatement(
					"(Select a.location_code,a.location_name from location a left outer join alt_location c on a.location_id=c.location_id,"
							+ " voyage_details b "
							+ " where c.alt_name like ? and a.login_scac= ? and b.location_id=a.location_id and b.voyage_id = ? and b.is_load_port=true)"
							+ " union"
							+ " (Select a.location_code,a.location_name from location a, voyage_details b  where a.location_name like ? "
							+ " and a.login_scac= ?  and b.location_id=a.location_id and b.voyage_id = ? and b.is_load_port=true )");
			stmt.setString(1, objManifest.getLoadPortLocation() + "%");
			stmt.setString(2, objManifest.getLoginScac());
			stmt.setInt(3, objManifest.getVoyageId());
			stmt.setString(4, objManifest.getLoadPortLocation() + "%");
			stmt.setString(5, objManifest.getLoginScac());
			stmt.setInt(6, objManifest.getVoyageId());
			rs = stmt.executeQuery();
			System.out.println(stmt.toString());
			if (rs.next()) {
				objManifest.setLoadPortCustomCode(rs.getString("location_code"));
				isValid = true;
			} else {
				isValid = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isValid;
	}

	public String getLocationIdfromUnlocode(String loadPortCustomCode, String loginScac) {
		// setting Location Code/Custom Code
		ResultSet rs = null;
		String result = "";
		try {
			stmt1 = con.prepareStatement(
					"SELECT location_name FROM artemus.location where location_code=? and login_scac=?");
			stmt1.setString(1, loadPortCustomCode);
			stmt1.setString(2, loginScac);
			rs = stmt1.executeQuery();
			logger.info(stmt1);
			if (rs.next()) {
				result = rs.getString(1);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	public String update(AddOriginalManifest objManifest, String loginScac, OriginalManifestDAO objManifestDao)
			throws SQLException {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowDate = formatter.format(date);

		String result = "";

		try {

			pstmt3 = con.prepareStatement(
					"Insert into ams_to_be_sent" + " (login_scac, voyage_id, load_port, discharge_port, "
							+ " bill_lading_id, equipment_number, control_identifier, "
							+ " action_code, amendment_code, sent_date, sent_user) "
							+ " values (?, ?, ?, ?, ?, '', 'MI','','', ?, ?)");

			pstmt4 = con.prepareStatement(
					"Update bill_detail_status " + " set is_readonly=true where bill_lading_id=? and login_scac=?");

			pstmt5 = con.prepareStatement("Select * from ams_to_be_sent " + " where bill_lading_id=? "
					+ " and voyage_id=? " + " and load_port=? " + " and discharge_port=? " + " and login_scac=? "
					+ " and control_identifier='MI'");
			if (!isFROBPort(objManifest.getDischargePortCustomCode())) {

				pstmt = con.prepareStatement("insert ignore into voyage_port_details "
						+ " select a.login_scac, a.voyage_id," + objManifest.getDischargePortCustomCode()
						+ ", b.location_code, false, a.arrival_date from voyage_details a, "
						+ " location b where a.login_scac = b.login_scac and b.location_id = a.location_id "
						+ " and a.login_scac = ? and a.voyage_id = ? and b.location_code in ("
						+ objManifest.getDischargePortCustomCode() + ")");
				pstmt.setString(1, loginScac);
				pstmt.setInt(2, objManifest.getVoyageId());
				pstmt.executeUpdate();

				pstmt1 = con.prepareStatement("Update voyage_port_details "
						+ " set is_ams_sent=true where load_port=? and discharge_port in ("
						+ objManifest.getDischargePortCustomCode() + ") "
						+ " and voyage_id=? and login_scac=? and is_ams_sent=false");
				// if (objmOriginalManifestBeans.getSendManifest()) {
				pstmt1.setString(1, objManifest.getLoadPortCustomCode());
				pstmt1.setInt(2, objManifest.getVoyageId());
				pstmt1.setString(3, loginScac);
				logger.info("1 : " + pstmt1.toString());
				pstmt1.executeUpdate();
				// }
				pstmt2 = con.prepareStatement("Select distinct a.bill_lading_id " + " from bill_header a "
						+ " where a.voyage_id=? " + " and a.load_port=? " + " and a.bill_status!='DELETED'"
						+ " and a.discharge_port in (" + objManifest.getDischargePortCustomCode() + ") ");
				pstmt2.setInt(1, objManifest.getVoyageId());
				pstmt2.setString(2, objManifest.getLoadPortCustomCode());
				logger.info("2 : " + pstmt2.toString());
				rs = pstmt2.executeQuery();
				while (rs.next()) {
					// code for avoid multiple entry of voyageid,load_por,Discharge_por,bill_lading
					// _id,Controlidentifier="MI",login_scac
					pstmt5.setInt(1, rs.getInt(1));
					pstmt5.setInt(2, objManifest.getVoyageId());
					pstmt5.setString(3, objManifest.getLoadPortCustomCode());
					pstmt5.setString(4, objManifest.getDischargePortCustomCode());
					pstmt5.setString(5, loginScac);
					logger.info("3 : " + pstmt5.toString());
					rs1 = pstmt5.executeQuery();
					// end of avoid multiple entry of voyageid,load_por,Discharge_por,bill_lading
					// _id,Controlidentifier="MI",login_scac
					if (!rs1.next()) {

						pstmt3.setString(1, loginScac);
						pstmt3.setInt(2, objManifest.getVoyageId());
						pstmt3.setString(3, objManifest.getLoadPortCustomCode());
						pstmt3.setString(4, objManifest.getDischargePortCustomCode());
						pstmt3.setInt(5, rs.getInt(1));
						// pstmt3.setString(6, rs.getString(2));
						pstmt3.setString(6, nowDate);
						pstmt3.setString(7, "api");
						logger.info("3 : " + pstmt3.toString());
						pstmt3.executeUpdate();

						pstmt4.setInt(1, rs.getInt(1));
						pstmt4.setString(2, loginScac);
						logger.info("4 : " + pstmt4.toString());
						pstmt4.executeUpdate();

					}
				}
			}
			return "Success";
		} catch (Exception e) {
			e.printStackTrace();
			result = "Error";
		}

		return null;
	}

	public Boolean isFROBPort(String dischargePort) {
		Boolean result = false;
		PreparedStatement objmPreparedStatement = null;
		try {
			System.out.println(dischargePort);
			objmPreparedStatement = con.prepareStatement("select * from foreign_port where port_code=?");
			objmPreparedStatement.setString(1, dischargePort);
			rs = objmPreparedStatement.executeQuery();
			logger.info(objmPreparedStatement);
			if (rs.next()) {
				result = true;
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getLocationCode(String locationName, String loginScac) {
		try {
			stmt = con.prepareStatement("Select location_code from location where location_name=? and login_scac=? ");
			stmt.setString(1, locationName);
			stmt.setString(2, loginScac);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString("location_code");
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private int getReferenceNumber(String loginScac) {
		int referenceNumber = 0;
		try {
			stmt = con.prepareStatement("SELECT (ifnull(max(orm_number),0)+1) FROM orm where login_scac=?");
			stmt.setString(1, loginScac);
			rs = stmt.executeQuery();
			if (rs.next()) {
				referenceNumber = rs.getInt(1);
				stmt = con.prepareStatement("update orm set orm_number=? where login_scac=?");
				stmt.setInt(1, referenceNumber);
				stmt.setString(2, loginScac);
				if (stmt.executeUpdate() != 1) {
					referenceNumber = 0;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return referenceNumber;
	}

	public String updateFOBE(AddOriginalManifest objManifest, String loginScac, OriginalManifestDAO objManifestDao)
			throws SQLException {
		String result = "";

		try {
			pstmt3 = con.prepareStatement("Insert into ams_sent"
					+ " (login_scac, voyage_id, load_port, discharge_port, "
					+ " bill_lading_id, equipment_number, control_identifier, "
					+ " action_code, amendment_code, sent_date, sent_user,reference_number,status,message,batch_number,transmitted_date) "
					+ " values (?, ?, ?, ?, ?, '', 'MI','','', now(), ? , ? ,'','','',now())");

			pstmt4 = con.prepareStatement(
					"Update bill_detail_status " + " set is_readonly=true where bill_lading_id=? and login_scac=?");

			pstmt5 = con.prepareStatement(
					"Select * from ams_sent " + " where bill_lading_id=? " + " and voyage_id=? " + " and load_port=? "
							+ " and discharge_port=? " + " and login_scac=? " + " and control_identifier='MI'");

			if (isFROBPort(objManifest.getDischargePortCustomCode())) {
				String refNumber = "ORM" + objManifestDao.getReferenceNumber(loginScac);
				pstmt1 = con.prepareStatement(
						"Update voyage_port_details " + " set is_ams_sent=true where load_port=? and discharge_port='"
								+ objManifest.getDischargePortLocation() + "' "
								+ " and voyage_id=? and login_scac=? and is_ams_sent=false");
				pstmt1.setString(1, objManifest.getLoadPortCustomCode());
				pstmt1.setInt(2, objManifest.getVoyageId());
				pstmt1.setString(3, loginScac);
				logger.info("1 : " + pstmt1.toString());
				pstmt1.executeUpdate();

				pstmt2 = con.prepareStatement("Select distinct a.bill_lading_id " + " from bill_header a "
						+ " where a.voyage_id=? " + " and a.load_port=? " + " and a.bill_status!='DELETED'"
						+ " and a.discharge_port='" + objManifest.getDischargePortCustomCode() + "' ");
				pstmt2.setInt(1, objManifest.getVoyageId());
				pstmt2.setString(2, objManifest.getLoadPortCustomCode());
				logger.info("2 : " + pstmt2.toString());
				rs = pstmt2.executeQuery();

				while (rs.next()) {
					// code for avoid multiple entry of voyageid,load_por,Discharge_por,bill_lading
					// _id,Controlidentifier="MI",login_scac
					pstmt5.setInt(1, rs.getInt(1));
					pstmt5.setInt(2, objManifest.getVoyageId());
					pstmt5.setString(3, objManifest.getLoadPortCustomCode());
					pstmt5.setString(4, objManifest.getDischargePortCustomCode());
					pstmt5.setString(5, loginScac);
					logger.info("3 : " + pstmt5.toString());
					rs1 = pstmt5.executeQuery();
					// end of avoid multiple entry of voyageid,load_por,Discharge_por,bill_lading
					// _id,Controlidentifier="MI",login_scac
					if (!rs1.next()) {
						pstmt3.setString(1, loginScac);
						pstmt3.setInt(2, objManifest.getVoyageId());
						pstmt3.setString(3, objManifest.getLoadPortCustomCode());
						pstmt3.setString(4, objManifest.getDischargePortCustomCode());
						pstmt3.setInt(5, rs.getInt(1));
						pstmt3.setString(6, "api");
						pstmt3.setString(7, refNumber);
						logger.info("3 : " + pstmt3.toString());
						pstmt3.executeUpdate();

						pstmt4.setInt(1, rs.getInt(1));
						pstmt4.setString(2, loginScac);
						logger.info("4 : " + pstmt4.toString());
						pstmt4.executeUpdate();
					}
				}
			}

			result = "Success";
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			result = "Error";
		}
		return result;
	}

	public int getErrorBill(int voyageId, String loadPortLocation, String dischargePortLocation, String loginScac) {
		int iserror = 0;
		try {

			pstmt = con.prepareStatement("SELECT a.bill_lading_number,a.bill_lading_id FROM bill_header a,"
					+ " bill_detail_status b, voyage c Where a.login_scac=? and a.bill_lading_id=b.bill_lading_id "
					+ " and a.load_port=? and a.discharge_port=? and a.voyage_number=c.voyage_number "
					+ " and c.voyage_id=? and a.voyage_id=c.voyage_id and b.is_manifest_error=true");
			pstmt.setString(1, loginScac);
			pstmt.setString(2, loadPortLocation);
			pstmt.setString(3, dischargePortLocation);
			pstmt.setInt(4, voyageId);
			logger.info(pstmt.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				iserror = 1;
				errorMessage.append("Bill Lading number " + rs.getString(1) + " has AMS Error");
			}
			if (iserror == 1) {
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}

	public String getErrorMessage() {
		return errorMessage.toString();
	}

	public boolean validateBillExists(AddOriginalManifest objManifest) throws SQLException {
		try {
			pstmt2 = con.prepareStatement("Select distinct a.bill_lading_id " + " from bill_header a "
					+ " where a.voyage_id=? " + " and a.load_port=? " + " and a.bill_status!='DELETED'"
					+ " and a.discharge_port in (" + objManifest.getDischargePortCustomCode() + ") ");
			pstmt2.setInt(1, objManifest.getVoyageId());
			pstmt2.setString(2, objManifest.getLoadPortCustomCode());
			logger.info("2 : " + pstmt2.toString());
			rs = pstmt2.executeQuery();
			if (!rs.next()) {
				return false;
			}

		} catch (Exception e) {

		}
		return true;
	}

}
