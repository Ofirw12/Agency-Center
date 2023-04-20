import java.sql.*;

public class Database {
	private Connection conn;
	private Statement stmt;

	public Database() {
		this.makeConnection();
	}

	private void makeConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/test";
			this.conn = DriverManager.getConnection(url, "root", "root");
			this.stmt = conn.createStatement();
		} catch (ClassNotFoundException c) {

		} catch (SQLException s) {
		}
	}

	public void createTable() {
		try {
			this.stmt.executeUpdate(
					"CREATE TABLE Strategies (ID int, Code varchar(50), Client int, Severity int, Time int)");
		} catch (SQLException e) {
		}
	}

	public void insert(int id, String code, int client, int severity, int time) {

		try {
			this.stmt.executeUpdate("INSERT INTO Strategies(ID, Code, Client, Severity, Time) VALUES('" + id + "','"
					+ code + "','" + client + "','" + severity + "','" + time + "')");
		} catch (SQLException e) {
		}
	}

	public void dropTable() {
		try {
			this.stmt.executeUpdate("DROP TABLE Strategies");
		} catch (SQLException e) {
		}
	}

}
