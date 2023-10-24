package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBConnection {

	private static Connection conn = null;
	
	public static Connection getConnection() {
		if (conn == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			} catch (ClassNotFoundException e) {
				throw new DBException(e.getMessage());
			}
		}
		return conn;
	}

	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}

	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("D:\\Dev\\java\\crud-jsp-demo\\db.properties")) {
			System.out.println(fs);
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new DBException(e.getMessage());
		}
	}

	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}
	
	public void testeConexao() {
		try {
			Connection conn = getConnection();
			System.out.println(conn);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
