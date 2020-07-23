package com.artemus.app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.artemus.app.connection.DBConnectionFactory;
import com.artemus.app.model.request.AddressInfo;
import com.artemus.app.model.request.BillHeader;
import com.artemus.app.model.request.Cargo;
import com.artemus.app.model.request.Equipment;
import com.artemus.app.model.request.Package;
import com.artemus.app.model.request.Party;

public class BillsDAO {
	private Connection con;
	private java.sql.PreparedStatement stmt = null;
	private java.sql.PreparedStatement stmt1 = null, MIstmt = null;
	private ResultSet rs = null, MIrs = null;
	static Logger logger = LogManager.getLogger();
	StringBuffer errorMessage = new StringBuffer("");
	StringBuffer hazardErrorMessage = new StringBuffer("");

	public BillsDAO(Connection connection) {
		try {
			if (connection != null) {
				con = connection;
			} else {
				con = DBConnectionFactory.getConnection();
				con.setAutoCommit(false);
			}
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

	public int insertIntoBillHeader(BillHeader objBillHeader) {
		int billLadingId = 0;
		System.out.println("Inside Insert BillHeader");
		try {
			stmt = con.prepareStatement("Insert into bill_header "
					+ "(login_scac, bill_lading_number, bill_status, bill_type, hbl_scac,"
					+ " nvo_type, nvo_bl, scac_bill, master_bill, master_carrier_scac, voyage_number, voyage_id ,"
					+ " load_port, discharge_port, country_of_origin, place_of_receipt, "
					+ " place_of_delivery, move_type, created_user, created_date,split_bill_number,"
					+ "shipment_type,transmission_type,carnet_number,carnet_country,shipment_sub_type, "
					+ "estimated_value, estimated_quantity, unit_of_measure, estimated_weight, weight_qualifier)"
					+ "  values (?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, now(),?,?,?,?,?,?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, objBillHeader.getLoginScac());
			stmt.setString(2, objBillHeader.getBillOfLading());
			stmt.setString(3, "COMPLETE");
			stmt.setString(4, objBillHeader.getBillType());
			stmt.setString(5, objBillHeader.getHblScac());
			if (objBillHeader.getNvoType().length() > 16) {
				stmt.setString(6, objBillHeader.getNvoType().substring(0, 16));
			} else {
				stmt.setString(6, objBillHeader.getNvoType());
			}
			stmt.setString(7, objBillHeader.getNvoBill());
			stmt.setString(8, objBillHeader.getMasterBillScac());
			stmt.setString(9, objBillHeader.getMasterBill());
			stmt.setString(10, objBillHeader.getVesselSchedule().getVesselScac());

			String VoyageNumber = objBillHeader.getVesselSchedule().getVoyageNumber();
			stmt.setString(11, VoyageNumber);
			stmt.setInt(12, objBillHeader.getVesselSchedule().getVoyageId());
			stmt.setString(13, objBillHeader.getVesselSchedule().getPortOfLoading());
			stmt.setString(14, objBillHeader.getVesselSchedule().getPortOfDischarge());
			stmt.setString(15, objBillHeader.getVesselSchedule().getCountryOfOrigin());
			stmt.setString(16, objBillHeader.getVesselSchedule().getPlaceOfReceipt());
			stmt.setString(17, objBillHeader.getVesselSchedule().getPlaceOfDelivery());
			stmt.setString(18, objBillHeader.getVesselSchedule().getMoveType());
			stmt.setString(19, "admin");
			stmt.setString(20, "");
			if (objBillHeader.getShipmentType() == null || objBillHeader.getShipmentType().isEmpty()) {
				stmt.setString(21, "01");
			} else {
				stmt.setString(21, objBillHeader.getShipmentType());
			}
			if (objBillHeader.getTransmissionType() == null || objBillHeader.getTransmissionType().isEmpty()) {
				stmt.setString(22, "CT");
			} else {
				stmt.setString(22, objBillHeader.getTransmissionType());
			}

			stmt.setString(23, objBillHeader.getCarnet().getCarnetNumber());
			stmt.setString(24, objBillHeader.getCarnet().getCarnetCountry());
			stmt.setString(25, objBillHeader.getInformal().getShipmentSubType());
			stmt.setInt(26, objBillHeader.getInformal().getEstimatedValue());
			stmt.setInt(27, objBillHeader.getInformal().getEstimatedQuantity());
			stmt.setString(28, objBillHeader.getInformal().getUnitOfMeasure());
			stmt.setInt(29, objBillHeader.getInformal().getEstimatedWeight());
			stmt.setString(30, "K");
			System.out.println(stmt);
			if (stmt.executeUpdate() != 1) {
				billLadingId = 0;
			} else {
				rs = stmt.getGeneratedKeys();
				rs.next();
				billLadingId = rs.getInt(1);
			}
			System.out.println(stmt);
			System.out.println(billLadingId);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return billLadingId; // billLadingId
	}

	public boolean updateBillHeader(BillHeader objBillHeader) throws SQLException {
		boolean isUpdated = false;
		stmt = con.prepareStatement("Update bill_header set bill_status=?, bill_type=?,"
				+ " hbl_scac=?, nvo_type=?, nvo_bl=?, scac_bill=?, master_bill=?, "
				+ " master_carrier_scac=?,voyage_number=?,voyage_id=?, load_port=?, discharge_port=?,"
				+ " country_of_origin= ?, place_of_receipt= ?, place_of_delivery= ?,"
				+ " move_type= ?, split_bill_number=?, shipment_type=?,transmission_type=?, "
				+ " carnet_number=?, carnet_country=?, shipment_sub_type=?, estimated_value=?, estimated_quantity=?, "
				+ " unit_of_measure=?, estimated_weight=?, weight_qualifier=?,canada_carrier_office=? "
				+ " where bill_lading_id=? and login_scac=?");

		stmt.setString(1, "COMPLETE");
		stmt.setString(2, objBillHeader.getBillType());
		stmt.setString(3, objBillHeader.getHblScac());
		if (objBillHeader.getNvoType().length() > 16) {
			stmt.setString(4, objBillHeader.getNvoType().substring(0, 16));
		} else {
			stmt.setString(4, objBillHeader.getNvoType());
		}
		stmt.setString(5, objBillHeader.getNvoBill());
		stmt.setString(6, objBillHeader.getMasterBillScac());
		stmt.setString(7, objBillHeader.getMasterBill());
		stmt.setString(8, objBillHeader.getVesselSchedule().getVesselScac());

		stmt.setString(9, objBillHeader.getVesselSchedule().getVoyageNumber());
		stmt.setInt(10, objBillHeader.getVesselSchedule().getVoyageId());
		stmt.setString(11, objBillHeader.getVesselSchedule().getPortOfLoading());
		stmt.setString(12, objBillHeader.getVesselSchedule().getPortOfDischarge());
		stmt.setString(13, objBillHeader.getVesselSchedule().getCountryOfOrigin());
		stmt.setString(14, objBillHeader.getVesselSchedule().getPlaceOfReceipt());
		stmt.setString(15, objBillHeader.getVesselSchedule().getPlaceOfDelivery());
		stmt.setString(16, objBillHeader.getVesselSchedule().getMoveType());

		stmt.setString(17, "");
		stmt.setString(18, objBillHeader.getShipmentType());
		stmt.setString(19, objBillHeader.getTransmissionType());
		stmt.setString(20, objBillHeader.getCarnet().getCarnetNumber());
		stmt.setString(21, objBillHeader.getCarnet().getCarnetCountry());
		stmt.setString(22, objBillHeader.getInformal().getShipmentSubType());
		stmt.setInt(23, objBillHeader.getInformal().getEstimatedValue());
		stmt.setInt(24, objBillHeader.getInformal().getEstimatedQuantity());
		stmt.setString(25, objBillHeader.getInformal().getUnitOfMeasure());
		stmt.setInt(26, objBillHeader.getInformal().getEstimatedWeight());
		stmt.setString(27, objBillHeader.getInformal().getUnit());
		stmt.setString(28, "");

		stmt.setInt(29, objBillHeader.getBillLadingId());
		stmt.setString(30, objBillHeader.getLoginScac());
		System.out.println(stmt);
		if (stmt.executeUpdate() != 1) {
			isUpdated = false;
		} else {
			isUpdated = true;
		}

		return isUpdated;
	}

	public boolean insertIntoConsigneeShipperDetails(Party objParty, String tag, int billLadingId) throws SQLException {
		boolean isDone = false;
		if (objParty != null) {
			if (objParty.getName().trim().equalsIgnoreCase("To Order") && objParty.getAddressInfo() == null) {
				objParty.setAddressInfo(new AddressInfo());
			}
			stmt = con.prepareStatement("Insert into consignee_shipper_details values (?, ?, ?, ?, ?, ?, false,?)");
			stmt.setInt(1, billLadingId);
			stmt.setString(2, objParty.getName());
			stmt.setString(3, tag);
			stmt.setString(4, objParty.getAddressInfo().getAddressLine1());
			stmt.setString(5, objParty.getAddressInfo().getAddressLine2());
			stmt.setString(6, objParty.getAddressInfo().getCity() + "," + objParty.getAddressInfo().getState() + ","
					+ objParty.getAddressInfo().getZipCode() + " " + objParty.getAddressInfo().getCountry());
			stmt.setInt(7, objParty.getCustomerId());
			logger.info(stmt);
			if (stmt.executeUpdate() != 1) {
				throw new SQLException();
			}
			isDone = true;
		} else {
			stmt = con.prepareStatement("Insert into consignee_shipper_details values (?, ?, ?, ?, ?, ?, false,?)");
			stmt.setInt(1, billLadingId);
			stmt.setString(2, "");
			stmt.setString(3, tag);
			stmt.setString(4, "");
			stmt.setString(5, "");
			stmt.setString(6, "");
			stmt.setInt(7, 0);
			logger.info(stmt);
			if (stmt.executeUpdate() != 1) {
				throw new SQLException();
			}
			isDone = true;
		}
		return isDone;
	}

	public boolean deleteFromConsigneeShipperDetails(int billLadingId) throws SQLException {
		boolean isDone = false;
		stmt = con.prepareStatement("Delete from consignee_shipper_details where bill_lading_id=?");
		stmt.setInt(1, billLadingId);
		logger.info(stmt);
		stmt.executeUpdate();
		isDone = true;
		return isDone;
	}

	public void insertIntoNotifyPartyDetails(ArrayList<String> notifyParties, int billLadingId) throws SQLException {
		stmt = con.prepareStatement("Insert into notify_party_details values (?,?)");
		for (String notifyParty : notifyParties) {
			stmt.setInt(1, billLadingId);
			stmt.setString(2, notifyParty);
			logger.info(stmt);
			if (stmt.executeUpdate() != 1) {
				throw new SQLException();
			}
		}
	}

	public boolean deleteFromNotifyPartyDetails(int billLadingId) throws SQLException {
		boolean isDone = false;
		stmt = con.prepareStatement("Delete from notify_party_details where bill_lading_id=?");
		stmt.setInt(1, billLadingId);
		logger.info(stmt);
		stmt.executeUpdate();
		isDone = true;
		return isDone;
	}

	public boolean insertIntoEquipments(Equipment objEquipment, int billLadingId) {
		try {
			stmt = con.prepareStatement("Insert into equipment values (?,?,?)");
			stmt.setInt(1, billLadingId);
			stmt.setString(2, objEquipment.getEquipmentNo());
			stmt.setString(3, objEquipment.getEquipmentType());
			stmt.executeUpdate();
			logger.info(stmt);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteFromEquipment(int billLadingId) throws SQLException {
		boolean isDone = false;
		stmt = con.prepareStatement("Delete from equipment where bill_lading_id=?");
		stmt.setInt(1, billLadingId);
		stmt.executeUpdate();
		logger.info(stmt);
		isDone = true;
		return isDone;
	}

	public boolean insertIntoSeals(Equipment objEquipment, int billLadingId) {
		try {
			if (objEquipment.getSeals() != null) {
				stmt = con.prepareStatement("Insert into seal values (?, ?, ?)");
				stmt.setInt(1, billLadingId);
				for (String seal : objEquipment.getSeals()) {
					stmt.setString(2, objEquipment.getEquipmentNo());
					stmt.setString(3, seal);
					logger.info(stmt);
					stmt.executeUpdate();
				}
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteFromSeal(int billLadingId) throws SQLException {
		boolean isDone = false;
		stmt = con.prepareStatement("Delete from seal where bill_lading_id=?");
		stmt.setInt(1, billLadingId);
		logger.info(stmt);
		stmt.executeUpdate();
		isDone = true;
		return isDone;
	}

	public int addPackages(Equipment objEquipment, int billLadingId, int packageIndex) {
		try {
			stmt = con.prepareStatement(
					"Insert into packages " + " (bill_lading_id, package_id,equipment_number, marks, pieces, packages)"
							+ " values (?, ?, ?, ?, ?, ?)");
			stmt1 = con.prepareStatement("Insert into packages_details "
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			for (Package objPackage : objEquipment.getPackages()) {
				stmt.setInt(1, billLadingId);
				stmt.setInt(2, packageIndex);
				stmt.setString(3, objEquipment.getEquipmentNo());
				stmt.setString(4, objPackage.getMarks());
				stmt.setString(5, objPackage.getPieces());
				stmt.setString(6, objPackage.getPackageType());
				System.out.println("Package" + stmt);
				if (stmt.executeUpdate() != 1) {
					return -1;
				}

				// following query till end of the list PackagesBean to insert notify party
				// records in packages table

				stmt1.setInt(1, billLadingId);
				stmt1.setInt(2, packageIndex);
				stmt1.setString(3, objEquipment.getEquipmentNo());

				if (objPackage.getWeight().getUnit().equalsIgnoreCase("LBS")) {
					stmt1.setDouble(4, objPackage.getWeight().getValue());
					stmt1.setString(16, objPackage.getWeight().getUnit());
				} else {
					stmt1.setDouble(4, 0);
					stmt1.setString(16, "");
				}

				if (objPackage.getWeight().getUnit().equalsIgnoreCase("KGS")
						|| objPackage.getWeight().getUnit().equalsIgnoreCase("MT")) {
					stmt1.setDouble(5, objPackage.getWeight().getValue());
					stmt1.setString(17, objPackage.getWeight().getUnit());
				} else {
					stmt1.setDouble(5, 0);
					stmt1.setString(17, "");
				}

				if (objPackage.getVolume().getUnit().equalsIgnoreCase("CF")) {
					stmt1.setDouble(6, objPackage.getVolume().getValue());
					stmt1.setString(18, objPackage.getVolume().getUnit());
				} else {
					stmt1.setDouble(6, 0);
					stmt1.setString(18, "");
				}

				if (objPackage.getVolume().getUnit().equalsIgnoreCase("CM")) {
					stmt1.setDouble(7, objPackage.getVolume().getValue());
					stmt1.setString(19, objPackage.getVolume().getUnit());
				} else {
					stmt1.setDouble(7, 0);
					stmt1.setString(19, "");
				}

				stmt1.setDouble(8, objPackage.getLength().getValue());
				stmt1.setDouble(9, objPackage.getWidth().getValue());
				stmt1.setDouble(10, objPackage.getHeight().getValue());
				stmt1.setDouble(11, objPackage.getSet().getValue());
				stmt1.setDouble(12, objPackage.getMin().getValue());
				stmt1.setDouble(13, objPackage.getMax().getValue());
				stmt1.setDouble(14, objPackage.getVents().getValue());
				stmt1.setDouble(15, objPackage.getDrainage().getValue());

				stmt1.setString(20, objPackage.getLength().getUnit());
				stmt1.setString(21, objPackage.getWidth().getUnit());
				stmt1.setString(22, objPackage.getHeight().getUnit());
				stmt1.setString(23, objPackage.getSet().getUnit());
				stmt1.setString(24, objPackage.getMin().getUnit());
				stmt1.setString(25, objPackage.getMax().getUnit());
				stmt1.setString(26, objPackage.getVents().getUnit());
				stmt1.setString(27, objPackage.getDrainage().getUnit());

				if (stmt1.executeUpdate() != 1) {
					return -1;
				}

				++packageIndex;
			}
			return packageIndex;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public boolean deleteFromPackages(int billLadingId) throws SQLException {
		boolean isDone = false;
		stmt = con.prepareStatement("Delete from packages where bill_lading_id=?");
		stmt.setInt(1, billLadingId);
		logger.info(stmt);
		stmt.executeUpdate();
		stmt = con.prepareStatement("Delete from packages_details where bill_lading_id=?");
		stmt.setInt(1, billLadingId);
		logger.info(stmt);
		stmt.executeUpdate();
		isDone = true;
		return isDone;
	}

	public int addCargos(Equipment objEquipment, int billLadingId, int cargoIndex) {
		// TODO Auto-generated method stub
		try {
			if (objEquipment.getCargos() != null) {

				stmt1 = con.prepareStatement("SELECT system_code FROM artemus.new_hazard_code where un_code=?");

				stmt = con.prepareStatement("Insert into cargo "
						+ " (bill_lading_id, cargo_id, equipment_number, description, harmonize_code, "
						+ " hazard_code, manufacturer, country,customer_id,flash_point,flash_unit) values "
						+ " (?,?,?,?,?,?,?,?,?,?,?)");

				for (Cargo objCargo : objEquipment.getCargos()) {
					if (objCargo != null) {

						// get hazard code from hazard un_code
						stmt1.setString(1, objCargo.getHazardCode());
						rs = stmt1.executeQuery();
						if (rs.next()) {
							objCargo.setHazardCode(rs.getString(1));
							logger.info(rs.getString(1));
						} else if (objCargo.getHazardCode() != null && !objCargo.getHazardCode().isEmpty()) {
							hazardErrorMessage.append("Entered hazard_code " + objCargo.getHazardCode()
									+ " is invalid .Insert Valid one.");
						}

						// Insert into Cargo
						stmt.setInt(1, billLadingId);
						stmt.setInt(2, cargoIndex);
						stmt.setString(3, objEquipment.getEquipmentNo());
						stmt.setString(4, objCargo.getDescriptionsOfGoods());
						stmt.setString(5, objCargo.getHarmonizeCode());
						stmt.setString(6, objCargo.getHazardCode());
						if (objCargo.getManufacturer() != null) {
							stmt.setInt(9, objCargo.getManufacturer().getCustomerId());// Field cannot get
							if (objCargo.getManufacturer().getName() == null
									|| objCargo.getManufacturer().getName().isEmpty()) {
								stmt.setString(7, "");
							} else {
								stmt.setString(7, objCargo.getManufacturer().getName());
							}
						} else {
							stmt.setString(7, "");
							stmt.setInt(9, 0);// Field cannot get
							errorMessage.append("<br>Manufacturer entry is missing.");
						}

						stmt.setString(8, objCargo.getCountry());
						if (objCargo.getFlashPointDetails() != null) {
							stmt.setDouble(10, objCargo.getFlashPointDetails().getFlashPoint());
							stmt.setString(11, objCargo.getFlashPointDetails().getFlashUnit());
						} else {
							stmt.setDouble(10, 0);
							stmt.setString(11, "");
						}

						if (stmt.executeUpdate() != 1) {
							return -1;
						}
						// logger.info(stmt);
						System.out.println("Inside CArgos" + objCargo);
						if (objCargo.getCountry().isEmpty()
								|| objCargo.getCountry() == null && objCargo.getHarmonizeCode().isEmpty()
								|| objCargo.getHarmonizeCode() == null) {
							errorMessage.append("<br>Country is missing for Manufacturer.")
									.append("<br>Harmonized Code entry is missing.");
						} else if (objCargo.getHarmonizeCode().isEmpty() || objCargo.getHarmonizeCode() == null) {
							errorMessage.append("<br>Harmonized Code entry is missing.");
						}

					}
					++cargoIndex;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return cargoIndex;
	}

	public boolean deleteFromCargo(int billLadingId) throws SQLException {
		boolean isDone = false;
		stmt = con.prepareStatement("Delete from cargo where bill_lading_id=?");
		stmt.setInt(1, billLadingId);
		logger.info(stmt);
		stmt.executeUpdate();
		isDone = true;
		return isDone;
	}

	public void insertIntoBillDetailStatus(BillHeader objBillHeader, int billLadingId) throws SQLException {
		stmt = con.prepareStatement(
				"Insert into bill_detail_status (login_scac, bill_lading_id, is_ams_sent, is_isf_sent, is_readonly, "
						+ "error_code, isf_error_code, error_description, isf_error_description, is_manifest_error, is_isf_error, is_ams_error, "
						+ "updated_date)" + "values(?,?,?,?,?,?,?,?,?,?,?,?,now())");

		stmt.setString(1, objBillHeader.getLoginScac());
		stmt.setInt(2, billLadingId);
		stmt.setBoolean(3, false);
		stmt.setBoolean(4, false);
		stmt.setBoolean(5, false);
		stmt.setString(6, "");
		stmt.setString(7, "");
		stmt.setString(8, "");
		stmt.setString(9, objBillHeader.getIsfErrorDescription());
		stmt.setBoolean(10, false);
		if (objBillHeader.getIsfErrorDescription().length() > 7) {
			stmt.setBoolean(11, true);
		} else {
			stmt.setBoolean(11, false);
		}
		stmt.setBoolean(12, false);
		System.out.println(stmt);

		if (stmt.executeUpdate() != 1) {
			throw new SQLException();
		}
	}

	public void updateBillDetailStatus(BillHeader objBillHeader, int billLadingId) throws SQLException {
		stmt = con.prepareStatement("Update bill_detail_status "
				+ " set is_readonly=?, error_description=?, isf_error_description=?, is_manifest_error=?, is_isf_error=?, is_ams_sent=? "
				+ " where bill_lading_id=? and login_scac=?");
		stmt.setBoolean(1, false);
		stmt.setString(2, "");
		stmt.setString(3, objBillHeader.getIsfErrorDescription());
		stmt.setBoolean(4, false);
		if (objBillHeader.getIsfErrorDescription().length() > 7) {
			stmt.setBoolean(5, true);
		} else {
			stmt.setBoolean(5, false);
		}
		stmt.setBoolean(5, false);
		stmt.setBoolean(6, false);
		stmt.setInt(7, billLadingId);
		stmt.setString(8, objBillHeader.getLoginScac());
		stmt.executeUpdate();
		logger.info(stmt);
		stmt.executeUpdate();
	}

	public void insertIntoVoyagePortDetails(BillHeader objBillHeader, String dischargePort) {
		if (dischargePort.equals(""))
			dischargePort = objBillHeader.getVesselSchedule().getPortOfDischarge();
		try {
			stmt = con.prepareStatement("select arrival_date from voyage_details a, location b "
					+ "where a.login_scac=? and a.voyage_id=? and a.is_discharge_port=? "
					+ "and a.location_id=b.location_id and b.location_code=? ");

			stmt.setString(1, objBillHeader.getLoginScac());
			stmt.setInt(2, objBillHeader.getVesselSchedule().getVoyageId());
			stmt.setBoolean(3, true);
			stmt.setString(4, dischargePort);

			rs = stmt.executeQuery();
			System.out.println(stmt);
			if (rs.next()) {
				stmt = con.prepareStatement("replace into voyage_port_details "
						+ "(login_scac, voyage_id, load_port, discharge_port, is_ams_sent, eta) values(?,?,?,?,?,?)");
				stmt.setString(1, objBillHeader.getLoginScac());
				stmt.setInt(2, objBillHeader.getVesselSchedule().getVoyageId());
				stmt.setString(3, objBillHeader.getVesselSchedule().getPortOfLoading());
				stmt.setString(4, dischargePort);
				stmt.setBoolean(5,
						isMISent(objBillHeader.getVesselSchedule().getVoyageId(),
								objBillHeader.getVesselSchedule().getPortOfLoading(),
								objBillHeader.getVesselSchedule().getPortOfDischarge(), objBillHeader.getLoginScac()));
				stmt.setString(6, rs.getString(1));
				stmt.executeUpdate();
				System.out.println(stmt);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean isMISent(int voyageId, String loadPortCode, String dischargePortCode, String loginScac)
			throws SQLException {
		boolean result = false;

		MIstmt = con.prepareStatement(
				"Select * from voyage_port_details where login_scac=? and voyage_id=? and load_port=? and discharge_port=? and is_ams_sent=true");
		MIstmt.setString(1, loginScac);
		MIstmt.setInt(2, voyageId);
		MIstmt.setString(3, loadPortCode);
		MIstmt.setString(4, dischargePortCode);
		MIrs = MIstmt.executeQuery();
		if (MIrs.next())
			result = true;
		else
			result = false;

		return result;
	}

	public boolean isFROBBill(BillHeader objBillHeader) {
		// TODO Auto-generated method stub
		boolean isFROB = false;
		try {
			stmt = con.prepareStatement("select port_code from foreign_port where port_code=?");
			stmt.setString(1, objBillHeader.getVesselSchedule().getPortOfDischarge());
			if (stmt.executeQuery().next()) {
				isFROB = true;
				objBillHeader.setIsfType("ISF-5");
				System.out.println(stmt);
			} else {
				objBillHeader.setIsfType("ISF-10");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isFROB;
	}

	public String getDistrictPortForFROB(int voyageId, String loginScac) {
		// TODO Auto-generated method stub
		String districtPort = "";
		try {
			stmt = con.prepareStatement("select b.location_code,a.arrival_date from "
					+ "voyage_details a,location b where a.login_scac=? and a.voyage_id=? and "
					+ "a.location_id=b.location_id and is_discharge_port=true order by 2 asc");
			stmt.setString(1, loginScac);
			stmt.setInt(2, voyageId);
			rs = stmt.executeQuery();
			System.out.println(stmt);
			if (rs.next()) {
				districtPort = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return districtPort;
	}

	public boolean validateBillExist(BillHeader objBillHeader) {
		boolean isExist = false;
		try {
			stmt = con.prepareStatement(
					"select bill_lading_id from bill_header where login_scac=? and bill_lading_number = ?");
			stmt.setString(1, objBillHeader.getLoginScac());
			stmt.setString(2, objBillHeader.getBillOfLading());
			rs = stmt.executeQuery();
			if (rs.next()) {
				objBillHeader.setBillLadingId(rs.getInt("bill_lading_id"));
				isExist = true;
			}
			System.out.println(stmt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isExist;
	}

	public StringBuffer getErrorMessage() {
		return errorMessage;

	}

	public StringBuffer getHazardErrorMessage() {
		return hazardErrorMessage;
	}

	public int addEmptyPackages(Equipment objEquipment, int billLadingId, int packageIndex) {
		try {
			stmt = con.prepareStatement(
					"Insert into packages " + " (bill_lading_id, package_id,equipment_number, marks, pieces, packages)"
							+ " values (?, ?, ?, ?, ?, ?)");
			stmt1 = con.prepareStatement("Insert into packages_details "
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			logger.info("Inside Empty Packages");

			Package emptyPackage = new Package();
			emptyPackage.setPackageType("CTN");
			emptyPackage.setPieces("1");
			objEquipment.getPackages().add(emptyPackage);

			for (Package objPackage : objEquipment.getPackages()) {

				stmt.setInt(1, billLadingId);
				stmt.setInt(2, packageIndex);
				stmt.setString(3, objEquipment.getEquipmentNo());
				stmt.setString(4, objPackage.getMarks());
				stmt.setString(5, "1");
				stmt.setString(6, "CTN");
				logger.info(stmt);
				if (stmt.executeUpdate() != 1) {
					return -1;
				}

				// following query till end of the list PackagesBean to insert notify party
				// records in packages table

				stmt1.setInt(1, billLadingId);
				stmt1.setInt(2, packageIndex);
				stmt1.setString(3, objEquipment.getEquipmentNo());

				if (objPackage.getWeight().getUnit().equalsIgnoreCase("LBS")) {
					stmt1.setDouble(4, objPackage.getWeight().getValue());
					stmt1.setString(16, objPackage.getWeight().getUnit());
				} else {
					stmt1.setDouble(4, 0);
					stmt1.setString(16, "");
				}

				// set Weight by default
				stmt1.setDouble(5, 1);
				stmt1.setString(17, "KGS");

				if (objPackage.getVolume().getUnit().equalsIgnoreCase("CF")) {
					stmt1.setDouble(6, objPackage.getVolume().getValue());
					stmt1.setString(18, objPackage.getVolume().getUnit());
				} else {
					stmt1.setDouble(6, 0);
					stmt1.setString(18, "");
				}

				if (objPackage.getVolume().getUnit().equalsIgnoreCase("CM")) {
					stmt1.setDouble(7, objPackage.getVolume().getValue());
					stmt1.setString(19, objPackage.getVolume().getUnit());
				} else {
					stmt1.setDouble(7, 0);
					stmt1.setString(19, "");
				}

				stmt1.setDouble(8, objPackage.getLength().getValue());
				stmt1.setDouble(9, objPackage.getWidth().getValue());
				stmt1.setDouble(10, objPackage.getHeight().getValue());
				stmt1.setDouble(11, objPackage.getSet().getValue());
				stmt1.setDouble(12, objPackage.getMin().getValue());
				stmt1.setDouble(13, objPackage.getMax().getValue());
				stmt1.setDouble(14, objPackage.getVents().getValue());
				stmt1.setDouble(15, objPackage.getDrainage().getValue());

				stmt1.setString(20, objPackage.getLength().getUnit());
				stmt1.setString(21, objPackage.getWidth().getUnit());
				stmt1.setString(22, objPackage.getHeight().getUnit());
				stmt1.setString(23, objPackage.getSet().getUnit());
				stmt1.setString(24, objPackage.getMin().getUnit());
				stmt1.setString(25, objPackage.getMax().getUnit());
				stmt1.setString(26, objPackage.getVents().getUnit());
				stmt1.setString(27, objPackage.getDrainage().getUnit());
				logger.info(stmt1);
				if (stmt1.executeUpdate() != 1) {
					return -1;
				}

				++packageIndex;
			}
			return packageIndex;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int addEmptyCargos(Equipment objEquipment, int billLadingId, int cargoIndex) {
		try {

			objEquipment.setCargos(new ArrayList<Cargo>());

			if (objEquipment.getCargos() != null) {

				stmt1 = con.prepareStatement("SELECT system_code FROM artemus.new_hazard_code where un_code=?");

				stmt = con.prepareStatement("Insert into cargo "
						+ " (bill_lading_id, cargo_id, equipment_number, description, harmonize_code, "
						+ " hazard_code, manufacturer, country,customer_id,flash_point,flash_unit) values "
						+ " (?,?,?,?,?,?,?,?,?,?,?)");

				Cargo emptyCargo = new Cargo();
				emptyCargo.setHarmonizeCode("869000");
				objEquipment.getCargos().add(emptyCargo);

				for (Cargo objCargo : objEquipment.getCargos()) {
					if (objCargo != null) {

						// get hazard code from hazard un_code
						stmt1.setString(1, objCargo.getHazardCode());
						rs = stmt1.executeQuery();
						if (rs.next()) {
							objCargo.setHazardCode(rs.getString(1));
							logger.info(rs.getString(1));
						} else if (objCargo.getHazardCode() != null && !objCargo.getHazardCode().isEmpty()) {
							hazardErrorMessage.append("Entered hazard_code " + objCargo.getHazardCode()
									+ " is invalid .Insert Valid one.");
						}

						// Insert into Cargo
						stmt.setInt(1, billLadingId);
						stmt.setInt(2, cargoIndex);
						stmt.setString(3, objEquipment.getEquipmentNo());
						stmt.setString(4, objCargo.getDescriptionsOfGoods());
						stmt.setString(5, "869000");
						stmt.setString(6, objCargo.getHazardCode());
						if (objCargo.getManufacturer() != null) {
							stmt.setInt(9, objCargo.getManufacturer().getCustomerId());// Field cannot get
							if (objCargo.getManufacturer().getName() == null
									|| objCargo.getManufacturer().getName().isEmpty()) {
								stmt.setString(7, "");
							} else {
								stmt.setString(7, objCargo.getManufacturer().getName());
							}
						} else {
							stmt.setString(7, "");
							stmt.setInt(9, 0);// Field cannot get
							errorMessage.append("<br>Manufacturer entry is missing.");
						}

						stmt.setString(8, objCargo.getCountry());
						if (objCargo.getFlashPointDetails() != null) {
							stmt.setDouble(10, objCargo.getFlashPointDetails().getFlashPoint());
							stmt.setString(11, objCargo.getFlashPointDetails().getFlashUnit());
						} else {
							stmt.setDouble(10, 0);
							stmt.setString(11, "");
						}

						if (stmt.executeUpdate() != 1) {
							return -1;
						}
						// logger.info(stmt);
						System.out.println("Inside CArgos" + objCargo);
						if (objCargo.getCountry().isEmpty()
								|| objCargo.getCountry() == null && objCargo.getHarmonizeCode().isEmpty()
								|| objCargo.getHarmonizeCode() == null) {
							errorMessage.append("<br>Country is missing for Manufacturer.")
									.append("<br>Harmonized Code entry is missing.");
						} else if (objCargo.getHarmonizeCode().isEmpty() || objCargo.getHarmonizeCode() == null) {
							errorMessage.append("<br>Harmonized Code entry is missing.");
						}

					}
					++cargoIndex;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return cargoIndex;
	}

	public boolean insertIntoEmptySeals(Equipment objEquipment, int billLadingId) {
		try {
			objEquipment.setSeals(new ArrayList<String>());
			String emptySeal = new String();
			emptySeal = "";
			objEquipment.getSeals().add(emptySeal);
			if (objEquipment.getSeals() != null) {
				stmt = con.prepareStatement("Insert into seal values (?, ?, ?)");
				stmt.setInt(1, billLadingId);
				for (String seal : objEquipment.getSeals()) {
					stmt.setString(2, objEquipment.getEquipmentNo());
					stmt.setString(3, seal);
					logger.info(stmt);
					stmt.executeUpdate();
				}
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
