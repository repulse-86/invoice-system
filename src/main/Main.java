package main;

import cli.CLI;
import events.EventDispatcher;
import models.Client;
import models.Invoice;
import models.InvoiceItem;
import models.Service;

public class Main {
	public static void main(String[] args) {
		EventDispatcher dispatcher = new EventDispatcher();

		// iregister ang mga "events" sa dispatcher
		// sila ang possbile events na mag occur
		dispatcher.subscribe("client_added", (event, data) -> {
			Client client = (Client) data;
			System.out.println("[Event] New client added: " + client.getName());
		});

		dispatcher.subscribe("service_added", (event, data) -> {
			Service service = (Service) data;
			System.out.println(
					"[Event] New service added: " + service.getName() + " ($" + service.getHourlyRate() + "/hr)");
		});

		dispatcher.subscribe("invoice_created", (event, data) -> {
			Invoice invoice = (Invoice) data;
			System.out.println("[Event] Invoice created for: " + invoice.getClient().getName());
			for (InvoiceItem item : invoice.getItems()) {
				System.out.println("- " + item.getService().getName() + ": " + item.getHoursWorked() + " hrs × $"
						+ item.getService().getHourlyRate() + " = $" + item.getTotalPrice());
			}
			System.out.println("Subtotal: $" + invoice.getSubtotal());
			System.out.println("Tax: $" + invoice.getTax());
			System.out.println("Total: $" + invoice.getTotal());
		});

		// use the dispatcher in the CLI
		// display the cli
		CLI cli = new CLI(dispatcher);
		cli.start();
	}
}
