public class SignupValidatorTest {
    public static void main(String[] args) {
        testScenario("이재준", "!1234", true);
        testScenario("이재준이민기김나현박은경", "!1234", true);
        testScenario("이재준", "1234", true);
        testScenario("이재준이민기김나현박은경", "1234", true);
        testScenario("", "", true);
    }

    private static void testScenario(String username, String password, boolean expected) {
        boolean isValid = isValidSignup(username, password);
        if (isValid == expected) {
            System.out.println("테스트 성공: 입력(" + username + ", " + password + ")");
        } else {
            System.out.println("테스트 실패: 입력(" + username + ", " + password + ")");
        }
    }

    private static boolean isValidSignup(String username, String password) {
        if (username.length() > 10 || username.isEmpty()) return false;
        if (password.length() < 4 || !password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) return false;
        return true;
    }
}

