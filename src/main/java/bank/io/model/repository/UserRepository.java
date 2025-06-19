package bank.io.model.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bank.io.db.H2Database;
import bank.io.model.User;
import bank.io.util.PasswordUtil;

public class UserRepository {
	public boolean register(User user) {
		try {
			// JDBC 연결을 통해 DB 연결하고 있다.
			// INSERT INTO를 통해 SQL 받을 준비를 하고
			// 각 입력값에 해당하는 값을 세팅하고, DB에 삽입했다.
			PreparedStatement ps = H2Database.getConnection().prepareStatement("""
                INSERT INTO users (username, password_hash, salt, age, gender, job)
                VALUES (?, ?, ?, ?, ?, ?)
            """);
			ps.setString(1, user.username);
			ps.setString(2, user.passwordHash);
			ps.setString(3, user.salt);
			ps.setInt(4, user.age);
			ps.setString(5, user.gender);
			ps.setString(6, user.job);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public User login(String username, String rawPassword) {
		try {
			// 사용자가 입력한 username을 통해서 DB에서 검색한다.
			PreparedStatement ps = H2Database.getConnection().prepareStatement("""
                SELECT * FROM users WHERE username = ?
            """);
			ps.setString(1, username);
			// ResultSet으로 찾은 결과를 가져오고, 해당유저가 존재하면 비밀번호 생성
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				// util에서 생성된 salt를 불러와서 기존 rawPassword랑 합치고
				// DB에 저장되어있는 password_hash와 비교해서 일치하면 새로운 user 객체를 생성한다.
				String salt = rs.getString("salt");
				String expectedHash = rs.getString("password_hash");
				String inputHash = PasswordUtil.hashPassword(rawPassword, salt);
				if (inputHash.equals(expectedHash)) {
					return new User(
						rs.getString("username"),
						expectedHash,
						salt,
						rs.getInt("age"),
						rs.getString("gender"),
						rs.getString("job")
					);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
