
public interface ClientDAO<T> extends DAO<T>{
	
	public Client findByCnp(String cnp);
}
