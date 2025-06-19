package bank.io.util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

// 무작위로 salt를 생성한다
public class PasswordUtil {
	public static String generateSalt() {
		byte[] salt = new byte[16];
		new SecureRandom().nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	// 사용자가 입력한 password에다가 무작위로 생성한 salt를 추가하고
	// SHA-256 해시함수를 통해서 해당 비밀번호를 암호화한다.
	// 최종적으로 Base64로 인코딩해서 문자열로 저장하게 된다.
	public static String hashPassword(String password, String salt) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update((salt + password).getBytes());
			return Base64.getEncoder().encodeToString(md.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
