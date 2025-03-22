package models;

public class InvoiceItem {
	private final Service service;
	private double hoursWorked;
	private double totalPrice;

	public InvoiceItem(Service service, double hoursWorked) {
		this.service = service;
		this.hoursWorked = hoursWorked;
		this.totalPrice = service.getHourlyRate() * hoursWorked;
	}

	public Service getService() {
		return service;
	}

	public double getHoursWorked() {
		return hoursWorked;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setHoursWorked(double hoursWorked) {
		this.hoursWorked = hoursWorked;
		this.totalPrice = service.getHourlyRate() * hoursWorked;
	}
}
