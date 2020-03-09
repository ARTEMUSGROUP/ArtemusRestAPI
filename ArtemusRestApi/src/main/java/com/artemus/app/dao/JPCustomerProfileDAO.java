package com.artemus.app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.artemus.app.connection.DBConnectionFactory;
import com.artemus.app.model.request.AddressInfo;
import com.artemus.app.model.request.BillHeader;
import com.artemus.app.model.request.Party;

public class JPCustomerProfileDAO {

	private Connection con;
	private java.sql.PreparedStatement stmt = null;
	private ResultSet rs = null;

	public JPCustomerProfileDAO() {

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

	public boolean updateCustomerPhone(Party partyBean, String loginScac) {
		boolean isUpdated = false;
		try {
			stmt = con.prepareStatement("update" + " customer set phone_number=?"
					+ " where login_scac_code=? and customer_name=? and address1=? and address2=? and country=? and state=? and city=? and zip_code=? ");
			stmt.setString(1, partyBean.getAddressInfo().getPhoneNo());
			stmt.setString(2, loginScac);
			stmt.setString(3, partyBean.getName());
			stmt.setString(4, partyBean.getAddressInfo().getAddressLine1());
			stmt.setString(5, partyBean.getAddressInfo().getAddressLine2());
			stmt.setString(6, partyBean.getAddressInfo().getCountry());
			stmt.setString(7, partyBean.getAddressInfo().getState());
			stmt.setString(8, partyBean.getAddressInfo().getCity());
			stmt.setString(9, partyBean.getAddressInfo().getZipCode());

			System.out.println(stmt.toString());

			if (stmt.executeUpdate() != 1) {
				isUpdated = false;
				return true;
			} else {
				isUpdated = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public boolean addCustomer(Party partyBean, String loginScac) {
		// TODO Auto-generated method stub
		setAddressInfo(partyBean);
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

	public void setAddressInfo(Party partyBean) {
		if (partyBean.getAddressInfo() == null) {
			partyBean.setAddressInfo(new AddressInfo());
		}
		partyBean.getAddressInfo().setAddressType("main");
		partyBean.getAddressInfo().setCountryOfIssuance("");
		partyBean.getAddressInfo().setFaxNo("");
		partyBean.getAddressInfo().setDob("");
		partyBean.getAddressInfo().setEntityType("");
		partyBean.getAddressInfo().setEntityNumber("");
		partyBean.getAddressInfo().setCreatedUser("admin");
		partyBean.getAddressInfo().setCreatedDate("");

	}

}
