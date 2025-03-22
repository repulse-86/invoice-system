package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConfig;
import models.Service;

public class ServiceDAO {
	public void addService(Service service) {
		String sql = "INSERT INTO services (name, hourly_rate) VALUES (?, ?)";
		try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, service.getName());
			stmt.setDouble(2, service.getHourlyRate());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Service> getAllServices() {
		List<Service> services = new ArrayList<>();
		String sql = "SELECT * FROM services";
		try (Connection conn = DatabaseConfig.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				services.add(new Service(rs.getInt("id"), rs.getString("name"), rs.getDouble("hourly_rate")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return services;
	}

	public void updateService(Service service) {
		String sql = "UPDATE services SET name = ?, hourly_rate = ? WHERE id = ?";
		try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, service.getName());
			stmt.setDouble(2, service.getHourlyRate());
			stmt.setInt(3, service.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteService(int serviceId) {
		String sql = "DELETE FROM services WHERE id = ?";
		try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, serviceId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
