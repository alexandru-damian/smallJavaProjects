
public interface AccountDAO<T> extends DAO<T>{
	/**
	 * The method will remove all the accounts related to a client.
	 * @param client A {@link Client} that will be removed from the data base.
	 */
	public void removeAll(Client client);
}
