package bank.io.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class H2Database {
	private static Connection conn;

	public static void init() {
		try {
			conn = DriverManager.getConnection("jdbc:h2:./shared-financedb;AUTO_SERVER=TRUE", "sa", "");
			createTable();
			insertInitialProducts();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createTable() throws SQLException {
		Statement stmt = conn.createStatement();

		stmt.execute("""
				CREATE TABLE IF NOT EXISTS products (
					id INT AUTO_INCREMENT PRIMARY KEY,
					name VARCHAR(255),
					type VARCHAR(50),
					interest_rate DOUBLE,
					period_months INT,
					target_age_min INT,
					target_age_max INT,
					gender VARCHAR(10),
					job VARCHAR(50)
				);
			""");

		stmt.execute("""
				CREATE TABLE IF NOT EXISTS users (
					id INT AUTO_INCREMENT PRIMARY KEY,
					username VARCHAR(50) UNIQUE,
					password_hash VARCHAR(255),
					salt VARCHAR(255),
					age INT,
					gender VARCHAR(10),
					job VARCHAR(50)
				);
			""");
	}

	private static void insertInitialProducts() throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM products");
		rs.next();
		if (rs.getInt(1) == 0) {
			stmt.execute("""
					INSERT INTO products (name, type, interest_rate, period_months, target_age_min, target_age_max, gender, job) VALUES
					('우리 첫급여적금', '적금', 3.7, 12, 20, 29, 'all', 'office'),
					('신한 드림적금', '적금', 4.2, 24, 20, 39, 'all', 'student'),
					('카카오뱅크 자유적금', '적금', 3.3, 12, 20, 60, 'all', 'all'),
					('토스뱅크 정기적금', '적금', 3.5, 6, 20, 60, 'all', 'all'),
					('국민 청년적금', '적금', 4.5, 36, 19, 34, 'all', 'all'),
					('기업은행 아이적금', '적금', 4.0, 12, 25, 45, 'all', 'office'),
					('우리은행 정기예금', '예금', 3.1, 12, 20, 65, 'all', 'all'),
					('신한은행 주거래예금', '예금', 3.0, 6, 20, 60, 'all', 'office'),
					('하나은행 스마트예금', '예금', 2.9, 24, 20, 60, 'all', 'all'),
					('농협 큰기쁨예금', '예금', 3.2, 36, 30, 65, 'all', 'all'),
					('카카오뱅크 예금', '예금', 3.3, 12, 20, 60, 'all', 'all'),
					('토스뱅크 예금', '예금', 3.5, 6, 20, 60, 'all', 'all'),
					('기업은행 목돈예금', '예금', 3.4, 24, 25, 60, 'all', 'office'),
					('수협 행복예금', '예금', 3.0, 12, 20, 65, 'all', 'all'),
					('신협 사랑예금', '예금', 3.1, 24, 30, 70, 'all', 'all'),
					('우리 청년우대적금', '적금', 4.3, 36, 20, 34, 'all', 'all'),
					('하나 내집마련적금', '적금', 4.0, 24, 25, 45, 'all', 'office'),
					('농협 NH e적금', '적금', 3.6, 12, 20, 60, 'all', 'all'),
					('대구은행 스마트적금', '적금', 3.5, 12, 20, 60, 'all', 'all'),
					('부산은행 금복적금', '적금', 3.9, 24, 20, 40, 'all', 'all')
				""");
		}

	}

	public static Connection getConnection() {
		return conn;
	}
}
