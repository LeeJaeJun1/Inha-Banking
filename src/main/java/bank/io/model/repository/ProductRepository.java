package bank.io.model.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bank.io.db.H2Database;
import bank.io.model.Product;

public class ProductRepository {
	public List<Product> findByUserCondition(int age, String gender, String job) {
		List<Product> list = new ArrayList<>();
		try {
			String sql = """
                SELECT * FROM products
                WHERE ? BETWEEN target_age_min AND target_age_max
                AND (gender = ? OR gender = 'all')
                AND (job = ? OR job = 'all')
            """;
			PreparedStatement ps = H2Database.getConnection().prepareStatement(sql);
			ps.setInt(1, age);
			ps.setString(2, gender);
			ps.setString(3, job);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new Product(
					rs.getString("name"),
					rs.getString("type"),
					rs.getDouble("interest_rate"),
					rs.getInt("period_months")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
