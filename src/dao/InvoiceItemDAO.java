package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DatabaseConfig;
import models.InvoiceItem;

public class InvoiceItemDAO {
	public void addInvoiceItem(int invoiceId, InvoiceItem item) {
		if (invoiceId <= 0) {
			System.out.println("Error: Invalid invoice ID. Cannot add invoice item.");
			return;
		}

		String sql = "INSERT INTO invoice_items (invoice_id, service_id, hours_worked, total_price) VALUES (?, ?, ?, ?)";
		try (Connection conn = DatabaseConfig.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, invoiceId);
			stmt.setInt(2, item.getService().getId());
			stmt.setDouble(3, item.getHoursWorked());
			stmt.setDouble(4, item.getTotalPrice());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
