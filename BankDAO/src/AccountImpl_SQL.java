import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class AccountImpl_SQL implements AccountDAO<Account> {

	ConnectionDAO connectionDAO = new ConnectionDAO();

	@Override
	public void add(Account account) {
		try {
			Connection connection = connectionDAO.getConnection();
			PreparedStatement ps = connection.prepareStatement(
					"insert into accounts(cnp,sum) values(?,?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, account.getCnp());
			ps.setDouble(2, account.getSumOfMoney());
			ps.executeUpdate();
			ResultSet result = ps.getGeneratedKeys();
			if (result.next()) {
				account.setId(result.getInt(1));
			}
			connectionDAO.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean remove(int id) {
		try {
			Connection connection = connectionDAO.getConnection();
			PreparedStatement ps = connection.prepareStatement(
					"delete from accounts where id=?",
					Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, id);
			int affectedRows = ps.executeUpdate();
			connectionDAO.closeConnection(connection);
			return affectedRows == 1;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Account account) {
		try {
			Connection connection = connectionDAO.getConnection();
			PreparedStatement ps = connection.prepareStatement(
					"update accounts set  sum = ? where id=?",
					Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, account.getSumOfMoney());
			ps.setInt(2, account.getId());
			int affectedRows = ps.executeUpdate();
			connectionDAO.closeConnection(connection);
			return affectedRows == 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Account findById(int id) {
		Account account = null;

		try {
			Connection connection = connectionDAO.getConnection();
			PreparedStatement ps = connection.prepareStatement(
					"select * from accounts where id=?",
					Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, id);
			ResultSet result = ps.executeQuery();
			if (result.next()) {
				int idFound = result.getInt("id");
				String cnpFound = result.getString("cnp");
				Double sumFound = result.getDouble("sum");
				account = new Account(idFound, cnpFound, sumFound);
			}
			connectionDAO.closeConnection(connection);
			return account;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

	@Override
	public Map<Integer, Account> getList() {
		try {
			Connection connection = connectionDAO.getConnection();
			PreparedStatement ps = connection.prepareStatement(
					"select * from accounts", Statement.RETURN_GENERATED_KEYS);
			ResultSet result = ps.executeQuery();
			Map<Integer, Account> map = new HashMap<>();
			while (result.next()) {

				int id = result.getInt("id");
				String cnp = result.getString("cnp");
				Double sumOfMoney = result.getDouble("sum");
				map.put(id, new Account(id, cnp, sumOfMoney));
			}
			connectionDAO.closeConnection(connection);
			return map;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void removeAll(Client client) {

		try {

			Connection connection = connectionDAO.getConnection();
			PreparedStatement ps = connection.prepareStatement(
					"delete from accounts where cnp= ?",
					Statement.NO_GENERATED_KEYS);
			ps.setString(1, client.getCnp());
			ps.executeUpdate();
			connectionDAO.closeConnection(connection);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
