package net.tokyo_ct.meister2015.jellyfish.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteManager {
	String connUrl;
	Connection con;
	Statement stmt;

	public SQLiteManager(String connUrl) {
		super();
		this.connUrl = connUrl;
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(connUrl);
			stmt = con.createStatement();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public int passwordVerification(String id, String hashedPass) {
		String sql = "SELECT * FROM users WHERE id = '" + id + "';";
		int code = 0; // Table has neither the id and the hashedPass.
		try {
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				if (code == 2) {
					break;
				}
				code = 1; // Table has the id but doesn't have the hashedPass.
				if (rs.getString("hashedPass").equals(hashedPass)) {
					code = 2; // Table has both the id and the hashedPass.
				}
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return code;
	}

	public String permission(String id) {
		String sql = "SELECT * FROM users WHERE id = '" + id + "';";
		String permission = "";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				permission = rs.getString("permission");
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return permission;
	}

}
