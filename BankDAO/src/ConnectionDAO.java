import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionDAO {
	public static final String CONNECTION_URL = "jdbc:mysql://localhost/bank_db";
	
	// Get connection status
		public Connection getConnection() throws SQLException {
			return DriverManager.getConnection(CONNECTION_URL, "root", "");
		}

		// End connection
		public void closeConnection(Connection conn) throws SQLException {
			conn.close();
		}
}
