/**
 * 
 */
package com.artemus.app.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.artemus.app.utils.ArtemusFinals;
import com.artemus.app.utils.LoadProperty;

public class DBConnectionFactory {

	public static Connection getConnection() {
		Connection connection = null;
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource dataSource = (DataSource) envContext.lookup("jdbc/artemus");
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static Connection getConnectionFromProperty() {
		Connection connection = null;
		try {
			Properties objmProperties = LoadProperty.getProperty();
			Class.forName(objmProperties.getProperty("driver_name"));
			connection = DriverManager.getConnection(objmProperties.getProperty("url"));
		} catch (Exception e) {
			System.out.println("Error While Getting Connection.");
		}
		return connection;
	}

	public static Connection getConnectionFromUtils()  {
		Connection connection = null;
		try {
			Class.forName(ArtemusFinals.DRIVER_NAME);
			connection = DriverManager.getConnection(ArtemusFinals.URL);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error While Getting Connection.");
		}
		return connection;
	}
}
