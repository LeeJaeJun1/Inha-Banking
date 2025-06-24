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
			// jdbc:h2 ~를 통해 로컬에 해당 db파일을 생성한다.
			// 뒤에 붙은 AUTO_SERVER=TRUE를 통해 여러 프로세스가 해당 DB에 접속할 수 있게 해준다.
			conn = DriverManager.getConnection("jdbc:h2:./shared-financedb;AUTO_SERVER=TRUE", "sa", "");
			createTable();
			insertInitialProducts();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// DB에 저장해야하는 상품과 유저 테이블이 존재하지 않으면 생성한다.
	private static void createTable() throws SQLException {
		Statement stmt = conn.createStatement();

		// id : 자동으로 증가시켜주는 기본키
		// name : 상품이름
		// type : 예금 / 적금
		// interest_rate : 금리
		// period_months : 기간
		// target_age_min : 상품 가입할 수 있는 최소 연령
		// target_age_max : 상품 가입할 수 있는 최대 연령
		// gender : 성별
		// job : 직업

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

	// 사용자의 환경에 따라 추천할 상품들 기본 조건을 입력한다.
	// 다양한 적금/예금 상품들을 미리 DB에 추가시킨다.
	private static void insertInitialProducts() throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM products");
		rs.next();
		if (rs.getInt(1) == 0) {
			stmt.execute("""
					INSERT INTO products (name, type, interest_rate, period_months, target_age_min, target_age_max, gender, job) VALUES
					('우리 첫급여적금', '적금', 3.7,12, 20, 29,'기타', '직장인'),
					('신한 드림적금', '적금', 4.2, 24, 20, 39, '기타','학생'),
					('카카오뱅크 자유적금', '적금', 3.3, 12,20, 60, '기타', '기타'),
					('토스뱅크 정기적금', '적금', 3.5,6, 20, 60, '기타', '기타'),
					('국민 청년적금', '적금', 4.5, 36, 19, 34, '기타', '기타'),
					('기업은행 아이적금', '적금', 4.0,12, 25, 45, '기타', '직장인'),
					('우리은행 정기예금', '예금', 3.1, 12,20, 65, '기타', '기타'),
					('신한은행 주거래예금', '예금', 3.0, 6, 20, 60, '기타', '직장인'),
					('하나은행 스마트예금', '예금', 2.9,24, 20, 60, '기타', '기타'),
					('농협 큰기쁨예금', '예금', 3.2, 36, 30,65, '기타', '기타'),
					('카카오뱅크 예금', '예금', 3.3,12, 20, 60, '기타','기타'),
					('토스뱅크 예금', '예금', 3.5, 6, 20, 60, '기타', '기타'),
					('기업은행 목돈예금', '예금', 3.4, 24,25, 60, '기타', '직장인'),
					('수협 행복예금', '예금', 3.0, 12,20, 65, '기타', '기타'),
					('신협 사랑예금', '예금', 3.1, 24, 30,70, '기타', '기타'),
					('우리 청년우대적금', '적금', 4.3,36,20, 34, '기타','기타'),
					('하나 내집마련적금', '적금', 4.0, 24,25, 45, '기타', '직장인'),
					('농협 NH e적금', '적금', 3.6, 12,20, 60, '기타', '기타'),
					('대구은행 스마트적금', '적금', 3.5, 12,20, 60, '기타', '기타'),
					('부산은행 금복적금', '적금', 3.9, 24,20, 40, '기타', '기타');
				""");
		}

	}

	// 이 메서드를 사용해서 외부 클래스에서 JDBC 연결을 공유할 수 있게 해준다.
	public static Connection getConnection() {
		return conn;
	}
}
