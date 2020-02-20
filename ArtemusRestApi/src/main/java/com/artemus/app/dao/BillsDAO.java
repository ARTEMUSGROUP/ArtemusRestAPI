package com.artemus.app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.artemus.app.connection.DBConnectionFactory;
import com.artemus.app.model.request.BillHeader;
import com.artemus.app.model.request.Cargo;
import com.artemus.app.model.request.Equipment;
import com.artemus.app.model.request.Package;
import com.artemus.app.model.request.Party;
import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;

import javassist.bytecode.stackmap.BasicBlock.Catch;

public class BillsDAO {
	private Connection con;
	private java.sql.PreparedStatement stmt = null;
	private java.sql.PreparedStatement stmt1 = null, MIstmt = null;
	private ResultSet rs = null;

	public BillsDAO() {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int insertIntoBillHeader(BillHeader objBillHeader) {
		int billLadingId = 0;
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
			stmt.setString(3, "completed");
			stmt.setString(4, objBillHeader.getBillType());
			stmt.setString(5, objBillHeader.getHblScac());
			stmt.setString(6, objBillHeader.getNvoType());
			stmt.setString(7, objBillHeader.getNvoBill());
			stmt.setString(8, objBillHeader.getScacBill());
			stmt.setString(9, objBillHeader.getMasterBill());
			stmt.setString(10, objBillHeader.getLoginScac());
			// here we splite the vesselName bcoz it contain vessel + voyageNumber we take
			// voyage no.only .
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
			stmt.setString(21, "");
			stmt.setString(22, "");
			stmt.setString(23, "");
			stmt.setString(24, "");

			stmt.setString(25, "");
			stmt.setInt(26, 0);
			stmt.setInt(27, 0);
			stmt.setString(28, "");
			stmt.setInt(29, 0);
			stmt.setString(30, "");

			if (stmt.executeUpdate() != 1) {
				billLadingId = 0;
			} else {
				rs = stmt.getGeneratedKeys();
				rs.next();
				billLadingId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return billLadingId; // billLadingId
	}

	public boolean insertIntoConsigneeShipperDetails(Party objParty, String tag, int billLadingId) {
		boolean isDone = false;
		if (objParty != null) {
			try {
				stmt = con.prepareStatement("Insert into consignee_shipper_details values (?, ?, ?, ?, ?, ?, false,?)");
				stmt.setInt(1, billLadingId);
				stmt.setString(2, objParty.getName());
				stmt.setString(3, tag);
				stmt.setString(4, objParty.getAddressInfo().getAddressLine1());
				stmt.setString(5, objParty.getAddressInfo().getAddressLine2());
				stmt.setString(6, objParty.getAddressInfo().getCity() + "," + objParty.getAddressInfo().getZipCode());
				stmt.setInt(7, objParty.getCustomerId());
				stmt.executeUpdate();
				isDone = true;
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return isDone;
	}

	public boolean insertIntoNotifyPartyDetails(ArrayList<String> notifyParties, int billLadingId) {
		try {
			stmt = con.prepareStatement("Insert into notify_party_details values (?,?)");
			for (String notifyParty : notifyParties) {
				stmt.setInt(1, billLadingId);
				stmt.setString(2, notifyParty);
				stmt.executeUpdate();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean insertIntoEquipments(Equipment objEquipment, int billLadingId) {
		try {
			stmt = con.prepareStatement("Insert into equipment values (?,?,?)");
			stmt.setInt(1, billLadingId);
			stmt.setString(2, objEquipment.getEquipmentNo());
			stmt.setString(3, objEquipment.getEquipmentType());
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean insertIntoSeals(Equipment objEquipment, int billLadingId) {
		try {
			stmt = con.prepareStatement("Insert into seal values (?, ?, ?)");
			stmt.setInt(1, billLadingId);
			for (String seal : objEquipment.getSeals()) {
				stmt.setString(2, objEquipment.getEquipmentNo());
				stmt.setString(3, seal);
				stmt.executeUpdate();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean addPackages(Equipment objEquipment, int billLadingId) {
		try {
			stmt = con.prepareStatement(
					"Insert into packages " + " (bill_lading_id, package_id,equipment_number, marks, pieces, packages)"
							+ " values (?, ?, ?, ?, ?, ?)");
			stmt1 = con.prepareStatement("Insert into packages_details "
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			int packageIndex = 0;
			for (Package objPackage : objEquipment.getPackages()) {
				stmt.setInt(1, billLadingId);
				stmt.setInt(2, packageIndex);
				stmt.setString(3, objEquipment.getEquipmentNo());
				stmt.setString(4, objPackage.getMarks());
				stmt.setString(5, objPackage.getPieces());
				stmt.setString(6, objPackage.getPackageType());
				if (stmt.executeUpdate() != 1) {
					return false;
				}

				// following query till end of the list PackagesBean to insert notify party
				// records in packages table

				stmt1.setInt(1, billLadingId);
				stmt1.setInt(2, packageIndex);
				stmt1.setString(3, objEquipment.getEquipmentNo());
				stmt1.setDouble(4, objPackage.getWeight().getValue());
				stmt1.setDouble(5, objPackage.getWeight().getValue());
				stmt1.setDouble(6, objPackage.getVolume().getValue());
				stmt1.setDouble(7, objPackage.getVolume().getValue());// Fields not known
				stmt1.setDouble(8, objPackage.getLength().getValue());
				stmt1.setDouble(9, objPackage.getWidth().getValue());
				stmt1.setDouble(10, objPackage.getHeight().getValue());
				stmt1.setDouble(11, objPackage.getSet().getValue());
				stmt1.setDouble(12, objPackage.getMin().getValue());
				stmt1.setDouble(13, objPackage.getMax().getValue());
				stmt1.setDouble(14, objPackage.getVents().getValue());
				stmt1.setDouble(15, objPackage.getDrainage().getValue());
				stmt1.setString(16, objPackage.getWeight().getUnit());
				stmt1.setString(17, objPackage.getWeight().getUnit());
				stmt1.setString(18, objPackage.getVolume().getUnit());
				stmt1.setString(19, objPackage.getVolume().getUnit());
				stmt1.setString(20, objPackage.getLength().getUnit());
				stmt1.setString(21, objPackage.getWidth().getUnit());
				stmt1.setString(22, objPackage.getHeight().getUnit());
				stmt1.setString(23, objPackage.getSet().getUnit());
				stmt1.setString(24, objPackage.getMin().getUnit());
				stmt1.setString(25, objPackage.getMax().getUnit());
				stmt1.setString(26, objPackage.getVents().getUnit());
				stmt1.setString(27, objPackage.getDrainage().getUnit());

				if (stmt1.executeUpdate() != 1) {
					return false;
				}

				packageIndex++;
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean addCargos(Equipment objEquipment, int billLadingId) {
		// TODO Auto-generated method stub
		try {
			stmt = con.prepareStatement(
					"Insert into cargo " + " (bill_lading_id, cargo_id, equipment_number, description, harmonize_code, "
							+ " hazard_code, manufacturer, country,customer_id) values " + " (?,?,?,?,?,?,?,?,?)");
			int cargoIndex = 0;
			for (Cargo objCargo : objEquipment.getCargos()) {
				if (objCargo != null) {
					stmt.setInt(1, billLadingId);
					stmt.setInt(2, cargoIndex);
					stmt.setString(3, objEquipment.getEquipmentNo());
					stmt.setString(4, objCargo.getDescriptionsOfGoods());
					stmt.setString(5, objCargo.getHarmonizeCode());
					stmt.setString(6, objCargo.getHazardCode());
					stmt.setString(7, objCargo.getManufacturer());
					stmt.setString(8, objCargo.getCountry());
					stmt.setInt(9, 0);// Field cannot get
					if (stmt.executeUpdate() != 1) {
						return false;
					}
				}
				cargoIndex++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public boolean insertIntoBillDetailStatus(BillHeader objBillHeader, int billLadingId) {
		// TODO Auto-generated method stub
		try {
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
			stmt.setString(9, "");
			stmt.setBoolean(10, false);
			stmt.setBoolean(11, false);
			stmt.setBoolean(12, false);
			if (stmt.executeUpdate() != 1) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

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
			if (rs.next()) {
				stmt = con.prepareStatement("replace into voyage_port_details "
						+ "(login_scac, voyage_id, load_port, discharge_port, is_ams_sent, eta) values(?,?,?,?,?,?)");
				stmt.setString(1, objBillHeader.getLoginScac());
				stmt.setInt(2, objBillHeader.getVesselSchedule().getVoyageId());
				stmt.setString(3, objBillHeader.getVesselSchedule().getPortOfLoading());
				stmt.setString(4, dischargePort);
				stmt.setBoolean(5, true);
				stmt.setString(6, rs.getString(1));
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//	public Boolean isMISent(int voyageId,String loadPortCode,String dischargePortCode,String loginScac) throws Exception{
//		Boolean result=false;
//	
//		MIstmt=con.prepareStatement("Select * from voyage_port_details where login_scac=? and voyage_id=? and load_port=? and discharge_port=? and is_ams_sent=true");
//		MIstmt.setString(1,loginScac);
//		MIstmt.setInt(2,voyageId);
//		MIstmt.setString(3,loadPortCode);
//		MIstmt.setString(4,dischargePortCode);
//		MIrs= MIstmt.executeQuery();
//		if (MIrs.next())		
//			result=true;
//		else
//			result=false;
//	
//		return result;
//	}
	public boolean isFROBBill(String portOfDischarge) {
		// TODO Auto-generated method stub
		Boolean isFROB = false;
		try {
			stmt = con.prepareStatement("select port_code from foreign_port where port_code=?");
			stmt.setString(1, portOfDischarge);
			if (stmt.executeQuery().next())
				isFROB = true;
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
			if (rs.next()) {
				districtPort = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return districtPort;
	}

}
