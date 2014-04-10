import java.util.InputMismatchException;
import java.util.Map;

public class AccountMenu {

	private AccountFactory accountFactory = new AccountFactory();
	private AccountDAO<Account> accountDAO = accountFactory
			.createAccountDAOFactory();

	public void infoMenu() {
		System.out.println();
		System.out.println("============================");
		System.out.println("|     ACCOUNT OPTIONS      |");
		System.out.println("----------------------------");
		System.out.println("|1.    Add new account     |");
		System.out.println("|2.    Remove an account   |");
		System.out.println("|3.    Select an account   |");
		System.out.println("|4.    List accounts       |");
		System.out.println("|0.         BACK           |");
		System.out.println("============================");
	}

	public void infoBuild() {
		System.out.println("============================");
		System.out.println("|    TYPES OF ACCOUNTS     |");
		System.out.println("----------------------------");
		System.out.println("|new -Create with no sum   |");
		System.out.println("|newa-Create and add sum   |");
		System.out.println("============================");
	}

	private Account build(String cnp) {
		double sum;

		System.out.print("sum:");
		sum = ConsoleMenu.scanner.nextDouble();

		if (!checkValidSum(sum)) {
			return null;
		}

		System.out.println("Account succesfully created.");
		return new Account(0, cnp, sum);
	}

	public void list(Client client) {
		System.out.println("======================================");
		System.out.println("|              ACCOUNTS              |");
		System.out.println("--------------------------------------");
		for (Map.Entry<Integer, Account> x : accountDAO.getList().entrySet()) {
			Account account = x.getValue();

			if (account.getCnp().equals(client.getCnp())) {
				System.out.println(account);
				System.out.println("--------------------------------------");
			}
		}
	}

	public void selectType(String command, Client client) {

		switch (command) {
		case "new": {
			accountDAO.add(new Account(0, client.getCnp(), 0));
			System.out.println("Account succesfully created.");
		}
			break;
		case "newa": {
			Account account = build(client.getCnp());

			if (account != null) {
				accountDAO.add(account);
			}
		}
			break;
		default:
			System.out.println("Incorrect command!");
		}
	}

	public void infoSelect() {
		System.out.println("============================");
		System.out.println("|      ACCOUNT OPTIONS     |");
		System.out.println("----------------------------");
		System.out.println("|1.    Update account      |");
		System.out.println("|2.    Deposit sum         |");
		System.out.println("|0.          BACK          |");
		System.out.println("============================");
	}

	public void delete(Client client) {
		this.list(client);
		System.out.print("Select ID:");

		int id = ConsoleMenu.scanner.nextInt();
		if (!checkInvalidId(accountDAO.findById(id))) {
			return;
		}

		accountDAO.remove(id);
		System.out.println("Account succesfuly removed.");
	}

	private void currentStatus(Account account) {
		System.out.println();
		System.out.println("=====================================");
		System.out.println("|           CURRENT DATA            |");
		System.out.println("-------------------------------------");
		System.out.println(account);
		System.out.println("=====================================");
	}

	private void edit(Account account) {
		char option;

		this.currentStatus(account);

		System.out.println("Do you want to chane the current sum? y/n");
		option = ConsoleMenu.scanner.next().charAt(0);

		if (option == 'y') {
			System.out.print("Sum:");
			double sum = ConsoleMenu.scanner.nextDouble();
			if (!checkValidSum(sum)) {
				return;
			}

			account.setSumOfMoney(sum);
		}

		System.out.println("Account succesfully updated.");
		this.currentStatus(account);

		accountDAO.update(account);
	}

	public void select(Client client) {

		this.list(client);
		System.out.print("Select ID:");

		int id = ConsoleMenu.scanner.nextInt();

		try {

			Account account = accountDAO.findById(id);

			if (!checkInvalidId(account)) {
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
				this.edit(accountDAO.findById(id));
				break;
			case 2: {
				double sum = optionsDepositInput();
				if (!checkValidSum(sum)) {
					return;
				}
				optionDeposit(accountDAO.findById(id), sum);
			}
				break;
			default:
				System.out.println("Invalid option!");
				break;
			}

		} catch (InputMismatchException e) {
			System.out.println("The options must be a number!");
		}
	}

	private boolean checkValidSum(double sum) {
		if (sum < 0) {
			System.out.println("The sum must be a positive number.");
			return false;
		}

		if (sum > Double.MAX_VALUE) {
			System.out.println("Number is to high");
			return false;
		}

		return true;
	}

	private boolean checkInvalidId(Account account) {
		if (account == null) {
			System.out.println("Account does not exists!");
			return false;
		}
		return true;
	}

	private void optionDeposit(Account account, double money) {
		account.setSumOfMoney(account.getSumOfMoney() + money);
		accountDAO.update(account);
		System.out.println("You have deposited " + money + " in account.");
	}

	private double optionsDepositInput() {
		double sum;
		System.out.print("Insert the sum:");
		try {
			
			sum = ConsoleMenu.scanner.nextDouble();

		} catch (InputMismatchException e) {
			System.out.println("Input must be a number!");
			return -1;
		}
		return sum;
	}

}
