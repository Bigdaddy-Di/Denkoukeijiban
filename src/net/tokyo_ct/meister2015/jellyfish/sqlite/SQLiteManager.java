package net.tokyo_ct.meister2015.jellyfish.sqlite;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.RandomStringUtils;

public class SQLiteManager {
	String connUrl;
	Connection con;
	Statement stmt;

	public SQLiteManager() {
		this("jdbc:sqlite:C:/Users/TeamET/setting.sqlite3");
	}

	public SQLiteManager(String connUrl) {
		super();
		this.connUrl = connUrl;
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(connUrl);
			stmt = con.createStatement();

			if (stmt.executeQuery(
					"SELECT count(*) FROM sqlite_master WHERE type='table' AND name='users';")
					.getInt("count(*)") == 0) {
				String sql = "CREATE TABLE users(id TEXT,hashedpass TEXT, permission TEXT, accessToken TEXT, accessTokenSecret TEXT);";
				stmt.execute(sql);
			}
			if (stmt.executeQuery(
					"SELECT count(*) FROM sqlite_master WHERE type='table' AND name='accessTokens';")
					.getInt("count(*)") == 0) {
				String sql = "CREATE TABLE accessTokens(id TEXT, accessToken TEXT, accessTokenSecret TEXT);";
				stmt.execute(sql);
			}
			if (stmt.executeQuery(
					"SELECT count(*) FROM sqlite_master WHERE type='table' AND name='weather';")
					.getInt("count(*)") == 0) {
				String sql = "CREATE TABLE weather(pref INTEGER, city INTEGER);";
				stmt.execute(sql);
			}

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

	public String accessToken(String at, String ats) {
		String sql = "SELECT * FROM users WHERE (accessToken = '" + at
				+ "') AND (accessTokenSecret = '" + ats + "');";
		String id = "";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				id = rs.getString("id");
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return id;
	}

	public String[] login(String id, String hashedPass) {
		String sql = "SELECT * FROM users WHERE (id = '" + id
				+ "')  AND (hashedPass = '" + hash(hashedPass) + "');";
		String[] accessToken = new String[2];
		try {
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				accessToken[0] = RandomStringUtils.randomAlphabetic(10);
				accessToken[1] = RandomStringUtils.randomAlphabetic(15);
				this.addAccessToken(id, accessToken);
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return accessToken;
	}

	private void addAccessToken(String id, String[] accessToken)
			throws SQLException {
		stmt.execute("UPDATE users SET accessToken='" + accessToken[0]
				+ "', accessTokenSecret='" + accessToken[1] + "' WHERE id='"
				+ id + "';");
	}

	public void setWeatherSetting(int pref, int city) {
		String sql = "DELETE FROM weather; INSERT INTO weather VALUES(" + pref
				+ "," + city + ");";
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public static String hash(String source) {
		StringBuilder sb = new StringBuilder();

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(source.getBytes());
			byte[] hash = md.digest();
			for (byte b : hash) {
				sb.append(String.format("%02x", b));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
