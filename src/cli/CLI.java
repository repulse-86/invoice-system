package cli;

import java.util.List;
import java.util.Scanner;

import dao.ClientDAO;
import dao.InvoiceDAO;
import dao.InvoiceItemDAO;
import dao.ServiceDAO;
import events.EventDispatcher;
import models.Client;
import models.Invoice;
import models.InvoiceItem;
import models.Service;

public class CLI {
	private final Scanner scanner = new Scanner(System.in);
	private final EventDispatcher dispatcher;
	private final ClientDAO clientDAO = new ClientDAO();
	private final ServiceDAO serviceDAO = new ServiceDAO();
	private final InvoiceDAO invoiceDAO = new InvoiceDAO();
	private final InvoiceItemDAO invoiceItemDAO = new InvoiceItemDAO();

	public CLI(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public void start() {
		while (true) {
			System.out.println("\n=== Virtual Assistant Invoice System ===");
			System.out.println("1. Client Management");
			System.out.println("2. Service Management");
			System.out.println("3. Invoice Management");
			System.out.println("4. Exit");
			System.out.print("Enter your choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine();

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
				System.out.println("\nExiting system...");
				return;
			default:
				System.out.println("\nInvalid choice. Try again.");
			}
		}
	}

	private void manageInvoices() {
		System.out.println("\n=== Invoice Management ===");
		List<Client> clients = clientDAO.getAllClients();
		if (clients.isEmpty()) {
			System.out.println("No clients available. Add a client first.");
			return;
		}

		System.out.println("\nSelect a client:");
		for (int i = 0; i < clients.size(); i++) {
			System.out.println((i + 1) + ". " + clients.get(i).getName());
		}
		System.out.print("Enter client number: ");
		int clientIndex = scanner.nextInt() - 1;
		scanner.nextLine();

		if (clientIndex < 0 || clientIndex >= clients.size()) {
			System.out.println("\nInvalid selection.");
			return;
		}

		Client client = clients.get(clientIndex);
		Invoice invoice = new Invoice(0, client);

		while (true) {
			List<Service> services = serviceDAO.getAllServices();
			if (services.isEmpty()) {
				System.out.println("\nNo services available. Add services first.");
				return;
			}

			System.out.println("\nSelect a service to add:");
			for (int i = 0; i < services.size(); i++) {
				System.out.println(
						(i + 1) + ". " + services.get(i).getName() + " ($" + services.get(i).getHourlyRate() + "/hr)");
			}
			System.out.println((services.size() + 1) + ". Finish Invoice");
			System.out.print("Enter choice: ");
			int serviceChoice = scanner.nextInt() - 1;
			scanner.nextLine();

			if (serviceChoice == services.size())
				break;
			if (serviceChoice < 0 || serviceChoice >= services.size()) {
				System.out.println("\nInvalid selection.");
				continue;
			}

			System.out.print("Enter hours worked: ");
			double hours = scanner.nextDouble();
			scanner.nextLine();

			invoice.addItem(services.get(serviceChoice), hours);
		}

		int invoiceId = invoiceDAO.addInvoice(invoice);
		if (invoiceId <= 0) {
			System.out.println("Error: Invoice creation failed. No items were added.");
			return;
		}

		for (InvoiceItem item : invoice.getItems()) {
			invoiceItemDAO.addInvoiceItem(invoiceId, item);
		}

		dispatcher.notify("invoice_created", invoice);
	}

	private void manageClients() {
		System.out.println("\n=== Client Management ===");
		System.out.println("1. Add Client");
		System.out.println("2. View Clients");
		System.out.println("3. Delete Client");
		System.out.println("4. Back");
		System.out.print("Enter choice: ");
		int choice = scanner.nextInt();
		scanner.nextLine();

		switch (choice) {
		case 1:
			System.out.print("\nEnter client name: ");
			String name = scanner.nextLine();
			System.out.print("Enter client email: ");
			String email = scanner.nextLine();
			System.out.print("Enter client phone: ");
			String phone = scanner.nextLine();

			Client client = new Client(0, name, email, phone);
			clientDAO.addClient(client);
			dispatcher.notify("client_added", client);
			break;
		case 2:
			System.out.println("\n=== List of Clients ===");
			List<Client> clients = clientDAO.getAllClients();
			for (Client c : clients) {
				System.out.println(c.getId() + ". " + c.getName() + " - " + c.getEmail());
			}
			break;
		case 3:
			System.out.print("\nEnter client ID to delete: ");
			int clientId = scanner.nextInt();
			clientDAO.deleteClient(clientId);
			System.out.println("Client deleted.");
			break;
		case 4:
			return;
		default:
			System.out.println("\nInvalid choice.");
		}
	}

	private void manageServices() {
		System.out.println("\n=== Service Management ===");
		System.out.println("1. Add Service");
		System.out.println("2. View Services");
		System.out.println("3. Delete Service");
		System.out.println("4. Back");
		System.out.print("Enter choice: ");
		int choice = scanner.nextInt();
		scanner.nextLine();

		switch (choice) {
		case 1:
			System.out.print("\nEnter service name: ");
			String name = scanner.nextLine();
			System.out.print("Enter hourly rate: ");
			double rate = scanner.nextDouble();

			Service service = new Service(0, name, rate);
			serviceDAO.addService(service);
			dispatcher.notify("service_added", service);
			break;
		case 2:
			System.out.println("\n=== List of Services ===");
			List<Service> services = serviceDAO.getAllServices();
			for (Service s : services) {
				System.out.println(s.getId() + ". " + s.getName() + " - $" + s.getHourlyRate() + "/hr");
			}
			break;
		case 3:
			System.out.print("\nEnter service ID to delete: ");
			int serviceId = scanner.nextInt();
			serviceDAO.deleteService(serviceId);
			System.out.println("Service deleted.");
			break;
		case 4:
			return;
		default:
			System.out.println("\nInvalid choice.");
		}
	}
}
