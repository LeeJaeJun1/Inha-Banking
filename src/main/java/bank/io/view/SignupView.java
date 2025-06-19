package bank.io.view;

import java.awt.*;

import javax.swing.*;

import bank.io.controller.AuthController;

public class SignupView {
	private final AuthController controller = new AuthController();

	public void show() {
		JFrame frame = new JFrame("회원가입");
		frame.setSize(400, 400);

		JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
		JTextField userField = new JTextField();
		JPasswordField passField = new JPasswordField();
		JTextField ageField = new JTextField();
		JComboBox<String> genderBox = new JComboBox<>(new String[]{"male", "female", "all"});
		JComboBox<String> jobBox = new JComboBox<>(new String[]{"student", "office", "freelance", "all"});
		JButton signupBtn = new JButton("가입하기");

		panel.add(new JLabel("아이디:")); panel.add(userField);
		panel.add(new JLabel("비밀번호:")); panel.add(passField);
		panel.add(new JLabel("나이:")); panel.add(ageField);
		panel.add(new JLabel("성별:")); panel.add(genderBox);
		panel.add(new JLabel("직업:")); panel.add(jobBox);
		panel.add(new JLabel("")); panel.add(signupBtn);

		signupBtn.addActionListener(e -> {
			String user = userField.getText();
			String pass = new String(passField.getPassword());
			int age = Integer.parseInt(ageField.getText());
			String gender = (String) genderBox.getSelectedItem();
			String job = (String) jobBox.getSelectedItem();

			if (controller.register(user, pass, age, gender, job)) {
				JOptionPane.showMessageDialog(frame, "가입 완료!");
				frame.dispose();
				new LoginView().show();
			} else {
				JOptionPane.showMessageDialog(frame, "중복 아이디 존재!");
			}
		});

		frame.add(panel);
		frame.setVisible(true);
	}
}
