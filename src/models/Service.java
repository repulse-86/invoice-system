package models;

public class Service {
	private final int id;
	private String name;
	private double hourlyRate;

	public Service(int id, String name, double hourlyRate) {
		this.id = id;
		this.name = name;
		this.hourlyRate = hourlyRate;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getHourlyRate() {
		return hourlyRate;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHourlyRate(double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}
}
