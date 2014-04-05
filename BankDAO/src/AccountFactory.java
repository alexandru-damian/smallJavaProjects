
public class AccountFactory {
	public AccountDAO<Account> createAccountDAOFactory(){
		return new AccountImpl_SQL();
	}
}

