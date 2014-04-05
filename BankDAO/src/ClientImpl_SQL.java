import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class ClientImpl_SQL implements ClientDAO<Client> {

	ConnectionDAO connectionDAO = new ConnectionDAO();
	
	// @Override
	public void add(Client client) {
		try {

			Connection connection = connectionDAO.getConnection();
			PreparedStatement ps = connection.prepareStatement(
					"insert into clients(name,cnp) values(?,?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, client.getName());
			ps.setString(2, client.getCnp());
			ps.executeUpdate();
			ResultSet result = ps.getGeneratedKeys();
			if (result.next()) {
				client.setId(result.getInt(1));
			}
			connectionDAO.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean update(Client client) {
		try {
			Connection connection = connectionDAO.getConnection();
			PreparedStatement ps = connection.prepareStatement(
					"update clients set name = ?,cnp = ? where id=?",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, client.getName());
			ps.setString(2, client.getCnp());
			ps.setInt(3, client.getId());
			int affectedRows = ps.executeUpdate();
			connectionDAO.closeConnection(connection);
			return affectedRows == 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean remove(int id) {
		try {
			Connection connection = connectionDAO.getConnection();
			PreparedStatement ps = connection.prepareStatement(
					"delete from clients where id=?",
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
	public Client findById(int id) {

		Client client = null;

		try {
			Connection connection = connectionDAO.getConnection();
			PreparedStatement ps = connection.prepareStatement(
					"select * from clients where id=?",
					Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, id);
			ResultSet result = ps.executeQuery();
			if (result.next()) {
				int idFound = result.getInt("id");
				String nameFound = result.getString("name");
				String cnpFound = result.getString("cnp");
				client = new Client(idFound, nameFound, cnpFound);
			}
			connectionDAO.closeConnection(connection);
			return client;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Map<Integer,Client> getList() {
		try{
			Connection connection = connectionDAO.getConnection();
			PreparedStatement ps = connection.prepareStatement("select * from clients", Statement.RETURN_GENERATED_KEYS);
			ResultSet result = ps.executeQuery();
			Map<Integer,Client> map = new HashMap<>();
			while(result.next()){
				
				int id = result.getInt("id");
				String name = result.getString("name");
				String cnp = result.getString("cnp");
				map.put(id, new Client(id,name,cnp));
			}
			connectionDAO.closeConnection(connection);
			return map;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}

}
