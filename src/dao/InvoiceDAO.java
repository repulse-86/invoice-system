package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DatabaseConfig;
import models.Invoice;

public class InvoiceDAO {
	public int addInvoice(Invoice invoice) {
		String invoiceNumber = generateInvoiceNumber(invoice.getClient().getId());

		String sql = "INSERT INTO invoices (invoice_number, client_id, subtotal, tax, total) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = DatabaseConfig.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, invoiceNumber);
			stmt.setInt(2, invoice.getClient().getId());
			stmt.setDouble(3, invoice.getSubtotal());
			stmt.setDouble(4, invoice.getTax());
			stmt.setDouble(5, invoice.getTotal());
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

	private String generateInvoiceNumber(int clientId) {
		return "INV-" + clientId + "-" + System.currentTimeMillis();
	}
}
