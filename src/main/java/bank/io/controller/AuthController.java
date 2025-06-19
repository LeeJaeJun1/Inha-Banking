package bank.io.controller;

import bank.io.model.User;
import bank.io.model.repository.UserRepository;
import bank.io.util.PasswordUtil;

public class AuthController {
	private final UserRepository repo = new UserRepository();


	public boolean register(String username, String password, int age, String gender, String job) {
		String salt = PasswordUtil.generateSalt();
		String hash = PasswordUtil.hashPassword(password, salt);
		User user = new User(username, hash, salt, age, gender, job);
		return repo.register(user);
	}

	public User login(String username, String password) {
		return repo.login(username, password);
	}
}
