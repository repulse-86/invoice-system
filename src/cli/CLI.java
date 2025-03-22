package cli;

import java.util.Scanner;

import events.EventDispatcher;

public class CLI {
	private final Scanner scanner = new Scanner(System.in);
	private final EventDispatcher dispatcher;

	public CLI(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public void start() {
		while (true) {
			System.out.println("=== Virtual Assistant Invoice System ===");
			System.out.println("1. Client Management");
			System.out.println("2. Service Management");
			System.out.println("3. Invoice Management");
			System.out.println("4. Exit");
			System.out.print("Enter your choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				System.out.println("Client Management Selected");
				break;
			case 2:
				System.out.println("Service Management Selected");
				break;
			case 3:
				System.out.println("Invoice Management Selected");
				break;
			case 4:
				return;
			default:
				System.out.println("Invalid choice.");
			}
		}
	}
}
