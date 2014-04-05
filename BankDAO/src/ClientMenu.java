import java.util.InputMismatchException;
import java.util.Map;

public class ClientMenu {

	private ClientFactory clientFactory = new ClientFactory();
	private ClientDAO<Client> clientDAO = clientFactory
			.createClientDAOFactory();

	public void infoMenu() {
		System.out.println();
		System.out.println("============================");
		System.out.println("|          OPTIONS         |");
		System.out.println("----------------------------");
		System.out.println("|1.    Add new client      |");
		System.out.println("|2.    Remove a client     |");
		System.out.println("|3.    Select a client     |");
		System.out.println("|4.    List all clients    |");
		System.out.println("|9.    Info options        |");
		System.out.println("|0.         EXIT           |");
		System.out.println("============================");
	}

	private Client build() {
		String name, cnp;

		System.out.print("Name:");
		name = ConsoleMenu.scanner.next();

		System.out.print("CNP:");
		cnp = ConsoleMenu.scanner.next();

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

		switch (command) {
		case "new":
			clientDAO.add(new Client());
			break;
		case "newc":
			clientDAO.add(build());
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
		System.out.println("|0.          BACK          |");
		System.out.println("============================");
	}

	public void delete() {
		this.list();
		System.out.print("Select ID:");

		int id = ConsoleMenu.scanner.nextInt();

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

		System.out.println("Do you want to chane the name? y/n");
		option = ConsoleMenu.scanner.next().charAt(0);

		if (option == 'y') {
			System.out.print("Name:");
			client.setName(ConsoleMenu.scanner.next());
		}

		System.out.println("Do you want to chane the CNP? y/n");
		option = ConsoleMenu.scanner.next().charAt(0);

		if (option == 'y') {
			System.out.print("CNP:");
			client.setCnp(ConsoleMenu.scanner.next());
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

			this.infoSelect();

			System.out.print("Select client option:");
			int option = ConsoleMenu.scanner.nextInt();

			// Exit method
			if (option == 0) {
				return;
			}

			switch (option) {
			case 1:
				this.edit(clientDAO.findById(id));
				break;
			case 2:
				System.out.println("acc options");
				break;
			default:
				System.out.println("Invalid option!");
				break;
			}

		} catch (InputMismatchException e) {
			System.out.println("The options must be a number!");
		}
	}
}
