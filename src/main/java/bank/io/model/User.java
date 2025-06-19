package bank.io.model;

public class User {
	public int id;
	public String username;
	public String passwordHash;
	public String salt;
	public int age;
	public String gender;
	public String job;

	public User(String username, String passwordHash, String salt, int age, String gender, String job) {
		this.username = username;
		this.passwordHash = passwordHash;
		this.salt = salt;
		this.age = age;
		this.gender = gender;
		this.job = job;
	}
}
