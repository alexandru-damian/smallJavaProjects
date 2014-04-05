
public class ClientFactory {
	public ClientDAO<Client> createClientDAOFactory(){
		return new ClientImpl_SQL();
	}
}
