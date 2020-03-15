package com.artemus.app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.artemus.app.connection.DBConnectionFactory;
import com.artemus.app.model.request.AddressInfo;
import com.artemus.app.model.request.BillHeader;
import com.artemus.app.model.request.Party;

public class CustomerProfileDAO {

	private Connection con;
	private java.sql.PreparedStatement stmt = null, stmt1 = null;
	private ResultSet rs = null;
	StringBuilder custErrorMessage = new StringBuilder("");

	public CustomerProfileDAO() {

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

	public Connection getConnection() {
		return con;
	}

	public boolean validateBillHeaderParties(BillHeader objBillHeader) {

		String name = "";

		System.out.println("validateBillHeaderParties ::");
		validateCustomer(objBillHeader.getShipper(), objBillHeader.getLoginScac());
		validateCustomer(objBillHeader.getBookingParty(), objBillHeader.getLoginScac());
		validateCustomer(objBillHeader.getSeller(), objBillHeader.getLoginScac());
		validateCustomer(objBillHeader.getConsolidator(), objBillHeader.getLoginScac());
		validateCustomer(objBillHeader.getStuffer(), objBillHeader.getLoginScac());
		validateCustomer(objBillHeader.getConsignee(), objBillHeader.getLoginScac(), objBillHeader);
		validateCustomer(objBillHeader.getNotify(), objBillHeader.getLoginScac());
		validateCustomer(objBillHeader.getImporter(), objBillHeader.getLoginScac(), objBillHeader);
		validateCustomer(objBillHeader.getBuyer(), objBillHeader.getLoginScac());
		validateCustomer(objBillHeader.getShipTo(), objBillHeader.getLoginScac());

		return true;

	}

	public void validateCustomer(Party objParty, String loginScac) {
		boolean customerGen = false;

		if (objParty != null) {
			if (isCustomerExists(objParty, loginScac)) {
				customerGen = true;
			} else {
				setAddressInfo(objParty);
				customerGen = addCustomer(objParty, loginScac);
			}
		}

	}

	public void validateCustomer(Party objParty, String loginScac, BillHeader objBillHeader) {
		boolean customerGen = false;

		if (objParty != null) {
			if (isCustomerExists(objParty, loginScac)) {
				customerGen = true;
				if (isEntityNumberExists(objParty, loginScac, objBillHeader)) {
					System.out.println("Entity Number Exists");
				}

			} else {
				setEntityTypeNumber(objParty);
				customerGen = addCustomer(objParty, loginScac);
			}
		}

	}

	private boolean isEntityNumberExists(Party objParty, String loginScac, BillHeader objBillHeader) {
		try {
			stmt = con.prepareStatement("select entity_number " + " from customer "
					+ " where login_scac_code=? and customer_name=? and address1=? and address2=? and country=? and state=? and city=? and zip_code=? and entity_number=?");
			stmt.setString(1, loginScac);
			stmt.setString(2, objParty.getName());
			stmt.setString(3, objParty.getAddressInfo().getAddressLine1());
			stmt.setString(4, objParty.getAddressInfo().getAddressLine2());
			stmt.setString(5, objParty.getAddressInfo().getCountry());
			stmt.setString(6, objParty.getAddressInfo().getState());
			stmt.setString(7, objParty.getAddressInfo().getCity());
			stmt.setString(8, objParty.getAddressInfo().getZipCode());
			stmt.setString(9, objParty.getAddressInfo().getEntityNumber());

			System.out.println(stmt.toString());
			rs = stmt.executeQuery();

			// Set Customer ID
			if (rs.next()) {
				objParty.getAddressInfo().setEntityNumber(rs.getString(1));

				if (objParty.getAddressInfo().getEntityNumber() != "") {
					System.out.print("Entity Number Exist");
				}
				return true;
			} else {
				custErrorMessage.append("<br>Entity Number is required for " + objParty.getName());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public void setAddressInfo(Party partyBean) {
		if (partyBean.getAddressInfo() == null) {
			partyBean.setAddressInfo(new AddressInfo());
		}
		partyBean.getAddressInfo().setAddressType("main");
		partyBean.getAddressInfo().setCountryOfIssuance("");
		partyBean.getAddressInfo().setPhoneNo("");
		partyBean.getAddressInfo().setFaxNo("");
		partyBean.getAddressInfo().setDob(null);
		if (partyBean.getAddressInfo().getEntityType() == null
				|| partyBean.getAddressInfo().getEntityType().isEmpty()) {
			partyBean.getAddressInfo().setEntityType("");
		}
		if (partyBean.getAddressInfo().getEntityNumber() == null
				|| partyBean.getAddressInfo().getEntityNumber().isEmpty()) {
			partyBean.getAddressInfo().setEntityNumber("");
		} else {
		}

		partyBean.getAddressInfo().setCreatedUser("admin");
		partyBean.getAddressInfo().setCreatedDate("");

	}

	public void setEntityTypeNumber(Party partyBean) {
		System.out.println("Inside setEntityTypeNumber");

		Pattern p = null;
		Matcher m = null;
		if (partyBean.getAddressInfo() == null) {
			partyBean.setAddressInfo(new AddressInfo());
		}
		partyBean.getAddressInfo().setAddressType("main");
		partyBean.getAddressInfo().setPhoneNo("");
		partyBean.getAddressInfo().setFaxNo("");

		if (partyBean.getAddressInfo().getEntityType().equalsIgnoreCase("SSN")
				&& partyBean.getAddressInfo().getDob() == "") {
			custErrorMessage.append("Date of birth not entered for party " + partyBean.getName());
		}
		if (partyBean.getAddressInfo().getEntityType().equalsIgnoreCase("Passport")
				&& partyBean.getAddressInfo().getDob() == "") {
			custErrorMessage.append("Date of birth not enteredfor party " + partyBean.getName());
		}
		if (partyBean.getAddressInfo().getEntityType().equalsIgnoreCase("Passport")
				&& partyBean.getAddressInfo().getCountry() == "") {
			custErrorMessage.append("Please select country of issuance for party " + partyBean.getName());
		}
		if (partyBean.getAddressInfo().getEntityType().equalsIgnoreCase("Employee ID")) {
			if (partyBean.getAddressInfo().getEntityNumber() != null
					|| !partyBean.getAddressInfo().getEntityNumber().isEmpty()) {
				p = Pattern.compile("^[0-9]{2}-[0-9]{7}[A-Z]{2}$"); // the pattern to search for
				Pattern p1 = Pattern.compile("\\d{2}-\\d{7}$");
				m = p.matcher(partyBean.getAddressInfo().getEntityNumber());
				Matcher m1 = p1.matcher(partyBean.getAddressInfo().getEntityNumber());
				if (m.matches() || m1.matches()) {
					System.out.println("Inside Entity num match for EmployeeID");
				} else {
					// alert("Invalid Employee ID. Use the format NN-NNNNNNNXX OR NN-NNNNNNN");
					custErrorMessage.append("Invalid Employee ID. Use the format NN-NNNNNNNXX OR NN-NNNNNNN.");
				}
			} else {
				custErrorMessage.append("Entity number is mandatory for Consignee and Importer Party ");
			}
		}

		if (partyBean.getAddressInfo().getEntityType().equalsIgnoreCase("SSN")) {
			Pattern p2 = Pattern.compile("^[0-9]{3}-[0-9]{2}-[0-9]{4}$");
			Matcher m2 = p2.matcher(partyBean.getAddressInfo().getEntityNumber());
			if (m2.matches()) {
				System.out.println("SSN matches");
			} else {
				custErrorMessage.append("Invalid SSN. Use the format NNN-NN-NNNN for Party ");
			}
		}
		if (partyBean.getAddressInfo().getEntityType().equalsIgnoreCase("Passport")) {
			p = Pattern.compile("^[A-Z]{5,15}$");
			m = p.matcher(partyBean.getAddressInfo().getEntityNumber());
			if (!m.matches()) {
				custErrorMessage.append("Invalid Passport Number. Use the format XXXXXXXXXXXXXXX");
			}
		}
		if (partyBean.getAddressInfo().getEntityType().equalsIgnoreCase("CBP Encrypted Consignee ID")) {
			p = Pattern.compile("-.{11}$/");
			m = p.matcher(partyBean.getAddressInfo().getEntityNumber());
			if (!m.matches()) {
				custErrorMessage.append("Invalid CBP Encrypted Consignee ID. Use the format -CCCCCCCCCCC");
			}
		}
		if (partyBean.getAddressInfo().getEntityType().equalsIgnoreCase("Importer/Consignee")) {
			p = Pattern.compile("^[0-9]{2}[[A-Z]]{4}-[0-9]{5}$");
			m = p.matcher(partyBean.getAddressInfo().getEntityNumber());
			if (!m.matches()) {
				custErrorMessage.append("Invalid Importer/Consignee. Use the format YYDDPP-NNNNN");
			}
		}
		if (partyBean.getAddressInfo().getEntityType().equalsIgnoreCase("DUNS")) {
			p = Pattern.compile("^[0-9]{9}$");
			m = p.matcher(partyBean.getAddressInfo().getEntityNumber());
			if (!m.matches()) {
				custErrorMessage.append("Invalid DUNS Number. Use the format NNNNNNNNN");
			}
		}
		if (partyBean.getAddressInfo().getEntityType().equalsIgnoreCase("DUNS 4")) {
			p = Pattern.compile("^[0-9]{13}$");
			m = p.matcher(partyBean.getAddressInfo().getEntityNumber());
			if (!m.matches()) {
				custErrorMessage.append("Invalid DUNS Number. Use the format NNNNNNNNNNNNN");
			}
		}

		partyBean.getAddressInfo().setCreatedUser("admin");
		partyBean.getAddressInfo().setCreatedDate("");

	}

	private boolean addCustomer(Party partyBean, String loginScac) {
		// TODO Auto-generated method stub

		try {
			stmt = con.prepareStatement("INSERT INTO customer(" + " login_scac_code, customer_name, address_type,"
					+ " address1, address2, country, state, city, " + " zip_code,phone_number, fax_number, "
					+ " entity_type, entity_number,created_user,created_date,dob,country_of_issuance)"
					+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?)", Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, loginScac);
			stmt.setString(2, partyBean.getName());
			stmt.setString(3, partyBean.getAddressInfo().getAddressType());
			stmt.setString(4, partyBean.getAddressInfo().getAddressLine1());
			stmt.setString(5, partyBean.getAddressInfo().getAddressLine2());
			stmt.setString(6, partyBean.getAddressInfo().getCountry());
			stmt.setString(7, partyBean.getAddressInfo().getState());
			stmt.setString(8, partyBean.getAddressInfo().getCity());
			stmt.setString(9, partyBean.getAddressInfo().getZipCode());
			stmt.setString(10, partyBean.getAddressInfo().getPhoneNo());
			stmt.setString(11, partyBean.getAddressInfo().getFaxNo());
			stmt.setString(12, partyBean.getAddressInfo().getEntityType());
			stmt.setString(13, partyBean.getAddressInfo().getEntityNumber());
			stmt.setString(14, partyBean.getAddressInfo().getCreatedUser());

			if (partyBean.getAddressInfo().getDob() == "") {
				stmt.setDate(15, null);
			} else {
				java.util.Date date2;
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/dd");
					date2 = sdf.parse(partyBean.getAddressInfo().getDob());
					java.sql.Date sqlDate = new java.sql.Date(date2.getTime());
					stmt.setDate(15, sqlDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			if (partyBean.getAddressInfo().getDob().equals(""))
				stmt.setString(15, null);
			else
				stmt.setString(15, partyBean.getAddressInfo().getDob());

			stmt.setString(16, partyBean.getAddressInfo().getCountryOfIssuance());

			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if (rs.next())
				partyBean.setCustomerId(rs.getInt(1));

			System.out.println(stmt.toString());

			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public boolean isCustomerExists(Party partyBean, String loginScac) {
		try {
			stmt = con.prepareStatement("select * " + " from customer "
					+ " where login_scac_code=? and customer_name=? and address1=? and address2=? and country=? and state=? and city=? and zip_code=? ");
			stmt.setString(1, loginScac);
			stmt.setString(2, partyBean.getName());
			stmt.setString(3, partyBean.getAddressInfo().getAddressLine1());
			stmt.setString(4, partyBean.getAddressInfo().getAddressLine2());
			stmt.setString(5, partyBean.getAddressInfo().getCountry());
			stmt.setString(6, partyBean.getAddressInfo().getState());
			stmt.setString(7, partyBean.getAddressInfo().getCity());
			stmt.setString(8, partyBean.getAddressInfo().getZipCode());

			System.out.println(stmt.toString());
			rs = stmt.executeQuery();

			// Set Customer ID
			if (rs.next()) {
				partyBean.setCustomerId(rs.getInt(2));

				if (partyBean.getCustomerId() != 0) {
					System.out.print("Customer Exist");
				}
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public StringBuilder getErrorMessage() {
		return custErrorMessage;
	}

	public String getScacUserType(String loginScac) {
		String userType = "";
		try {
			stmt = con.prepareStatement(
					"SELECT group_type,if(group_type=1,'master','nvocc') scac_user_type FROM login_scac where scac_code = ?;");
			stmt.setString(1, loginScac);
			System.out.println(stmt.toString());
			rs = stmt.executeQuery();
			// Set Customer ID
			if (rs.next()) {
				userType = rs.getString("scac_user_type");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			userType = "";

		}
		return userType;
	}

}
