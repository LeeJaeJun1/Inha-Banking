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

		// 상품 추천에 필요한 사용자 정보인 나이, 성별, 직업에 따라 적절한 상품을 DB에서 찾는다.
		try {
			String sql = """
                SELECT * FROM products
                WHERE ? BETWEEN target_age_min AND target_age_max
                AND (gender = ? OR gender = 'all')
                AND (job = ? OR job = 'all')
            """;

			// H2 데이터베이스에 연결을 생성하고 있음. 아래 세 줄을 통해 동적으로 사용자 입력값인 age,gender,job을 바인딩하고 있다.
			PreparedStatement ps = H2Database.getConnection().prepareStatement(sql);
			ps.setInt(1, age);
			ps.setString(2, gender);
			ps.setString(3, job);

			// sql을 실행하여 결과인 ResultSet을 받아온다.
			ResultSet rs = ps.executeQuery();

			// rs로 결과를 받아왔고, 해당 결과로 각 객체를 생성한다.
			// list에 add해서 최종적으로 추천할 수 있는 상품들을 리스트로 반환한다.

			while (rs.next()) {
				list.add(new Product(
					rs.getString("name"),
					rs.getString("type"),
					rs.getDouble("interest_rate"),
					rs.getInt("period_months")
				));
			}
			// 이 오류는 잘못된 sql문장이나, db 연결 실패 등 JDBC를 사용할 때 발생할 수 있는 예외처리다.

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
