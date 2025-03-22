package cli;

import java.util.List;
import java.util.Scanner;

import dao.ClientDAO;
import dao.ServiceDAO;
import events.EventDispatcher;
import models.Client;
import models.Service;

public class CLI {
	private final Scanner scanner = new Scanner(System.in);
	private final EventDispatcher dispatcher;
	private final ClientDAO clientDAO = new ClientDAO();
	private final ServiceDAO serviceDAO = new ServiceDAO();

	public CLI(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public void start() {
		while (true) {
			System.out.println("=== Virtual Assistant Invoice System ===");
			System.out.println("1. Client Management");
			System.out.println("2. Service Management");
			System.out.println("3. Exit");
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
				System.out.println("Exiting system...");
				return;
			default:
				System.out.println("Invalid choice. Try again.");
			}
		}
	}

	private void manageClients() {
		System.out.println("=== Client Management ===");
		System.out.println("1. Add Client");
		System.out.println("2. View Clients");
		System.out.println("3. Delete Client");
		System.out.println("4. Back");
		System.out.print("Enter choice: ");
		int choice = scanner.nextInt();
		scanner.nextLine();

		switch (choice) {
		case 1:
			System.out.print("Enter client name: ");
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
			List<Client> clients = clientDAO.getAllClients();
			for (Client c : clients) {
				System.out.println(c.getId() + ". " + c.getName() + " - " + c.getEmail());
			}
			break;
		case 3:
			System.out.print("Enter client ID to delete: ");
			int clientId = scanner.nextInt();
			clientDAO.deleteClient(clientId);
			System.out.println("Client deleted.");
			break;
		case 4:
			return;
		default:
			System.out.println("Invalid choice.");
		}
	}

	private void manageServices() {
		System.out.println("=== Service Management ===");
		System.out.println("1. Add Service");
		System.out.println("2. View Services");
		System.out.println("3. Delete Service");
		System.out.println("4. Back");
		System.out.print("Enter choice: ");
		int choice = scanner.nextInt();
		scanner.nextLine();

		switch (choice) {
		case 1:
			System.out.print("Enter service name: ");
			String name = scanner.nextLine();
			System.out.print("Enter hourly rate: ");
			double rate = scanner.nextDouble();

			Service service = new Service(0, name, rate);
			serviceDAO.addService(service);
			dispatcher.notify("service_added", service);
			break;
		case 2:
			List<Service> services = serviceDAO.getAllServices();
			for (Service s : services) {
				System.out.println(s.getId() + ". " + s.getName() + " - $" + s.getHourlyRate() + "/hr");
			}
			break;
		case 3:
			System.out.print("Enter service ID to delete: ");
			int serviceId = scanner.nextInt();
			serviceDAO.deleteService(serviceId);
			System.out.println("Service deleted.");
			break;
		case 4:
			return;
		default:
			System.out.println("Invalid choice.");
		}
	}
}
