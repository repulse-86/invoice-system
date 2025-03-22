package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConfig;
import models.Client;

public class ClientDAO {
	// no need to explain. basta nag iinsert lang to sa database and the rest of DAO
	// files
	public void addClient(Client client) {
		String sql = "INSERT INTO clients (name, email, phone) VALUES (?, ?, ?)";
		try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, client.getName());
			stmt.setString(2, client.getEmail());
			stmt.setString(3, client.getPhone());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Client> getAllClients() {
		List<Client> clients = new ArrayList<>();
		String sql = "SELECT * FROM clients";
		try (Connection conn = DatabaseConfig.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				clients.add(new Client(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
						rs.getString("phone")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clients;
	}

	public void updateClient(Client client) {
		String sql = "UPDATE clients SET name = ?, email = ?, phone = ? WHERE id = ?";
		try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, client.getName());
			stmt.setString(2, client.getEmail());
			stmt.setString(3, client.getPhone());
			stmt.setInt(4, client.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteClient(int clientId) {
		String sql = "DELETE FROM clients WHERE id = ?";
		try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, clientId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
