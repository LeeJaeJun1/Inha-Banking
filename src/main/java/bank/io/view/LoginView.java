package bank.io.view;

import java.awt.*;

import javax.swing.*;

import bank.io.controller.AuthController;
import bank.io.model.User;

public class LoginView {
	private final AuthController authController = new AuthController();

	public void show() {
		JFrame frame = new JFrame("로그인");
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
		JTextField usernameField = new JTextField();
		JPasswordField passwordField = new JPasswordField();
		JButton loginBtn = new JButton("로그인");
		JButton goSignupBtn = new JButton("회원가입");

		panel.add(new JLabel("아이디/이름:")); panel.add(usernameField);
		panel.add(new JLabel("비밀번호:")); panel.add(passwordField);
		panel.add(loginBtn); panel.add(goSignupBtn);

		loginBtn.addActionListener(e -> {
			String user = usernameField.getText();
			String pass = new String(passwordField.getPassword());
			User loggedIn = authController.login(user, pass);
			if (loggedIn != null) {
				JOptionPane.showMessageDialog(frame, "로그인 성공");
				frame.dispose();
				new MainView(loggedIn).showMain();// 추천화면 진입
			} else {
				JOptionPane.showMessageDialog(frame, "로그인 실패");
			}
		});

		goSignupBtn.addActionListener(e -> {
			frame.dispose();
			new SignupView().show();
		});

		frame.add(panel);
		frame.setVisible(true);
	}
}
