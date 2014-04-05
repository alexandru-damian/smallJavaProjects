import java.util.*;

public class ConsoleMenu {

	public static Scanner scanner = new Scanner(System.in);

	private void infoAccountMenu() {
		System.out.println();
		System.out.println("============================");
		System.out.println("|	  ACCOUNT OPTIONS      |");
		System.out.println("----------------------------");
		System.out.println("|1.    Add new account     |");
		System.out.println("|2.    Remove an account   |");
		System.out.println("|3.    Select an account   |");
		System.out.println("|4.    List accounts       |");
		System.out.println("|0.    		BACK           |");
		System.out.println("============================");
	}

	private void infoProgram() {
		System.out.println("============================");
		System.out.println("| BANK SIMULATOR DAO(BETA) |");
		System.out.println("============================");
	}

	private void endProgram() {
		scanner.close();
		System.out.println("Good Bye");
	}

	public void run() {
		infoProgram();
		ClientMenu clientMenu = new ClientMenu();
		
		clientMenu.infoMenu();
		do {
			if (mainMenu(clientMenu)) {
				break;
			}
		} while (true);

		endProgram();
	}

	//true  - The program will run
	//false - The program will exit
	private boolean mainMenu(ClientMenu clientMenu) {
		
		
		boolean status = false;

		try {
			System.out.print("Select an option:");
			int option = scanner.nextInt();
			switch (option) {
			case 1: {
				clientMenu.infoBuild();
				System.out.print("Select type:");
				clientMenu.selectType(scanner.next());
				
			}
				break;
			case 2:
				clientMenu.delete();
				break;
			case 3:
				clientMenu.select();
				break;
			case 4:
				clientMenu.list();
				break;
			case 9:
				clientMenu.infoMenu();
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
			scanner.next();
			return false;
		} catch (IllegalStateException e) {
			System.err
					.println("\nThe parser has been closed!\nFailed to read from the console.");
			return true;
		}
		return status;
	}
	
	private void edit(){
		System.out.println("Select");
	}

}
