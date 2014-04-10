import java.util.InputMismatchException;
import java.util.Map;

public class ClientMenu {

	private ClientFactory clientFactory = new ClientFactory();
	private ClientDAO<Client> clientDAO = clientFactory
			.createClientDAOFactory();
	
	private final int CNP_LENGTH = 13;

	public void infoMenu() {
		System.out.println();
		System.out.println("============================");
		System.out.println("|          OPTIONS         |");
		System.out.println("----------------------------");
		System.out.println("|1.    Add new client      |");
		System.out.println("|2.    Remove a client     |");
		System.out.println("|3.    Select a client     |");
		System.out.println("|4.    List all clients    |");
		//System.out.println("|4.    BONUS clients       |");
		System.out.println("|9.    Info options        |");
		System.out.println("|0.         EXIT           |");
		System.out.println("============================");
	}

	private Client build(boolean isDefault) {
		String name = "", cnp;

		if (!isDefault) {
			System.out.print("Name:");
			name = ConsoleMenu.scanner.next();
		}

		System.out.print("CNP:");
		cnp = ConsoleMenu.scanner.next();

		if(!checkValidCnp(cnp)){
			return null;
		}

		System.out.println("Client succesfully created.");
		return new Client(0, name, cnp);
	}

	public void infoBuild() {
		System.out.println("============================");
		System.out.println("|     TYPES OF CLIENTS     |");
		System.out.println("----------------------------");
		System.out.println("|new -Create with no fields|");
		System.out.println("|newc-Create with fields   |");
		System.out.println("============================");
	}

	public void selectType(String command) {
		Client client;

		switch (command) {
		case "new": {
			client = build(true);
			if (client != null) {
				clientDAO.add(client);
			}
		}
			break;
		case "newc": {
			client = build(false);
			if (client != null) {
				clientDAO.add(client);
			}
		}
			break;
		default:
			System.out.println("Incorrect command!");
		}
	}

	public void list() {
		System.out.println("======================================");
		System.out.println("|              CLIENTS               |");
		System.out.println("--------------------------------------");
		for (Map.Entry<Integer, Client> x : clientDAO.getList().entrySet()) {
			System.out.println(x.getValue());
			System.out.println("--------------------------------------");
		}
	}

	public void infoSelect() {
		System.out.println("============================");
		System.out.println("|      CLIENT OPTIONS      |");
		System.out.println("----------------------------");
		System.out.println("|1.    Update client       |");
		System.out.println("|2.    Account options     |");
		System.out.println("|3.    List all            |");
		System.out.println("|0.          BACK          |");
		System.out.println("============================");
	}

	public void delete() {
		this.list();
		System.out.print("Select ID:");

		int id = ConsoleMenu.scanner.nextInt();
		if(!checkInvalidId(clientDAO.findById(id))){
			return;
		}

		AccountFactory accountFactory = new AccountFactory();
		AccountDAO<Account> accountDAO = accountFactory
				.createAccountDAOFactory();

		accountDAO.removeAll(clientDAO.findById(id));
		System.out.println("All client's accounts were successfuly removed.");
		clientDAO.remove(id);
		System.out.println("Client succesfuly removed.");
	}

	private void currentStatus(Client client) {
		System.out.println();
		System.out.println("=====================================");
		System.out.println("|           CURRENT DATA            |");
		System.out.println("-------------------------------------");
		System.out.println(client);
		System.out.println("=====================================");
	}

	private void edit(Client client) {
		char option;

		this.currentStatus(client);

		System.out.println("Do you want to change the name? y/n");
		option = ConsoleMenu.scanner.next().charAt(0);

		if (option == 'y') {
			System.out.print("Name:");
			client.setName(ConsoleMenu.scanner.next());
		}

		System.out.println("Client succesfully updated.");
		this.currentStatus(client);

		clientDAO.update(client);
	}

	public void select() {

		this.list();
		System.out.print("Select ID:");

		int id = ConsoleMenu.scanner.nextInt();

		try {
			Client client = clientDAO.findById(id);
			
			if(!checkInvalidId(client)){
				return;
			}

			this.infoSelect();

			System.out.print("Select options:");
			int option = ConsoleMenu.scanner.nextInt();

			// Exit method
			if (option == 0) {
				return;
			}

			switch (option) {
			case 1:
				this.edit(client);
				break;
			case 2:
				this.runAccount(client);
				break;
			case 3:
				this.optionListAll(client);
				break;
			default:
				System.out.println("Invalid option!");
				break;
			}

		} catch (InputMismatchException e) {
			System.out.println("The options must be a number!");
		}
	}

	public void runAccount(Client client) {
		AccountMenu accountMenu = new AccountMenu();

		accountMenu.infoMenu();
		do {
			if (mainAccountMenu(accountMenu, client)) {
				break;
			}
		} while (true);
	}

	private boolean mainAccountMenu(AccountMenu accountMenu, Client client) {

		boolean status = false;

		try {
			System.out.print("Select an account option:");
			int option = ConsoleMenu.scanner.nextInt();
			switch (option) {
			case 1: {
				accountMenu.infoBuild();
				System.out.print("Select type:");
				accountMenu.selectType(ConsoleMenu.scanner.next(), client);

			}
				break;
			case 2:
				accountMenu.delete(client);
				break;
			case 3:
				accountMenu.select(client);
				break;
			case 4:
				accountMenu.list(client);
				break;
			case 9:
				accountMenu.infoMenu();
				break;
			case 0:
				status = true;
				break;
			default:
				System.out.println("Invalid option!");
				break;
			}

		}

		catch (InputMismatchException e) {
			System.out.println("The input must be a number!");
			ConsoleMenu.scanner.next();
			return false;
		} catch (IllegalStateException e) {
			System.err
					.println("\nThe parser has been closed!\nFailed to read from the console.");
			return true;
		}
		return status;
	}
	
	private boolean checkValidCnp(String cnp){
		if (clientDAO.findByCnp(cnp) != null) {
			System.out
					.println("Cnp already in the list!\nDuplicate field error.");
			return false;
		}
		
		if(cnp.length()>CNP_LENGTH){
			System.out.println("Cnp has more than "+CNP_LENGTH+" digits.");
			return false;
		}
		return true;
	}
	
	private boolean checkInvalidId(Client client){
		if (client == null) {
			System.out.println("Client does not exists!");
			return false;
		}
		return true;
	}
	
	private void optionListAll(Client client){
		this.currentStatus(client);
		AccountMenu accountMenu = new AccountMenu();
		accountMenu.list(client);
	}
}
