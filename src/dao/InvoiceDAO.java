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
import models.Invoice;

public class InvoiceDAO {
	public int addInvoice(Invoice invoice) {
		String sql = "INSERT INTO invoices (client_id, subtotal, tax, total) VALUES (?, ?, ?, ?)";
		try (Connection conn = DatabaseConfig.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setInt(1, invoice.getClient().getId());
			stmt.setDouble(2, invoice.getSubtotal());
			stmt.setDouble(3, invoice.getTax());
			stmt.setDouble(4, invoice.getTotal());
			stmt.executeUpdate();

			ResultSet generatedKeys = stmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				return generatedKeys.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public List<Invoice> getAllInvoices() {
		List<Invoice> invoices = new ArrayList<>();
		String sql = "SELECT * FROM invoices";
		try (Connection conn = DatabaseConfig.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				invoices.add(new Invoice(rs.getInt("id"), new Client(rs.getInt("client_id"), "", "", "")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return invoices;
	}
}
