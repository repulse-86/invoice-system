package cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import events.EventDispatcher;
import models.Client;
import models.Invoice;
import models.Service;

public class CLI {
	private final Scanner scanner = new Scanner(System.in);
	private final EventDispatcher dispatcher;
	private final List<Client> clients = new ArrayList<>();
	private final List<Service> services = new ArrayList<>();
	private final List<Invoice> invoices = new ArrayList<>();

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

			int choice = getIntInput();
			switch (choice) {
			case 1:
				manageClients();
				break;
			case 2:
				manageServices();
				break;
			case 3:
				manageInvoices();
				break;
			case 4:
				System.out.println("Exiting system...");
				return;
			default:
				System.out.println("Invalid choice. Try again.");
			}
		}
	}

	private void manageClients() {
		System.out.println("=== Client Management ===");
		System.out.print("Enter client name: ");
		String name = scanner.nextLine();
		System.out.print("Enter client email: ");
		String email = scanner.nextLine();
		System.out.print("Enter client phone: ");
		String phone = scanner.nextLine();

		Client client = new Client(clients.size() + 1, name, email, phone);
		clients.add(client);
		dispatcher.notify("client_added", client);
	}

	private void manageServices() {
		System.out.println("=== Service Management ===");
		System.out.print("Enter service name: ");
		String name = scanner.nextLine();
		System.out.print("Enter hourly rate: ");
		double rate = getDoubleInput();

		Service service = new Service(services.size() + 1, name, rate);
		services.add(service);
		dispatcher.notify("service_added", service);
	}

	private void manageInvoices() {
		if (clients.isEmpty()) {
			System.out.println("No clients available. Add a client first.");
			return;
		}

		Client client = selectClient();
		if (client == null)
			return;

		Invoice invoice = new Invoice(invoices.size() + 1, client);

		while (true) {
			Service service = selectService();
			if (service == null)
				break;

			System.out.print("Enter hours worked: ");
			double hours = getDoubleInput();

			invoice.addItem(service, hours);
		}

		invoices.add(invoice);
		dispatcher.notify("invoice_created", invoice);
	}

	private Client selectClient() {
		System.out.println("Select a client:");
		for (int i = 0; i < clients.size(); i++) {
			System.out.println((i + 1) + ". " + clients.get(i).getName());
		}
		System.out.print("Enter client number: ");
		int clientIndex = getIntInput() - 1;

		if (clientIndex < 0 || clientIndex >= clients.size()) {
			System.out.println("Invalid selection.");
			return null;
		}

		return clients.get(clientIndex);
	}

	private Service selectService() {
		System.out.println("Select a service to add:");
		for (int i = 0; i < services.size(); i++) {
			System.out.println(
					(i + 1) + ". " + services.get(i).getName() + " ($" + services.get(i).getHourlyRate() + "/hr)");
		}
		System.out.println((services.size() + 1) + ". Finish Invoice");
		System.out.print("Enter choice: ");
		int serviceChoice = getIntInput() - 1;

		if (serviceChoice == services.size())
			return null;
		if (serviceChoice < 0 || serviceChoice >= services.size()) {
			System.out.println("Invalid selection.");
			return null;
		}

		return services.get(serviceChoice);
	}

	private int getIntInput() {
		while (!scanner.hasNextInt()) {
			System.out.print("Invalid input. Enter a number: ");
			scanner.next();
		}
		return scanner.nextInt();
	}

	private double getDoubleInput() {
		while (!scanner.hasNextDouble()) {
			System.out.print("Invalid input. Enter a valid number: ");
			scanner.next();
		}
		return scanner.nextDouble();
	}
}
