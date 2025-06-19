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
			PreparedStatement ps = H2Database.getConnection().prepareStatement("""
                SELECT * FROM users WHERE username = ?
            """);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
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
