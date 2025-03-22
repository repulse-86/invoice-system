package models;

import java.util.ArrayList;
import java.util.List;

// invoice ay ang final summary for all the services availed
public class Invoice {
	private final int id;
	private final Client client;
	// ang isang invoice can have one or more services na included
	private final List<InvoiceItem> items;
	private double subtotal;
	private double tax;
	private double total;

	public Invoice(int id, Client client) {
		this.id = id;
		this.client = client;
		this.items = new ArrayList<>();
	}

	public void addItem(Service service, double hours) {
		items.add(new InvoiceItem(service, hours));
		recalculateTotals();
	}

	private void recalculateTotals() {
		subtotal = items.stream().mapToDouble(InvoiceItem::getTotalPrice).sum();
		tax = subtotal * 0.10;
		total = subtotal + tax;
	}

	public int getId() {
		return id;
	}

	public Client getClient() {
		return client;
	}

	public List<InvoiceItem> getItems() {
		return items;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public double getTax() {
		return tax;
	}

	public double getTotal() {
		return total;
	}
}
