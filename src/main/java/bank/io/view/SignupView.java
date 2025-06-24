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
		JComboBox<String> genderBox = new JComboBox<>(new String[]{"남성", "여성", "기타"});
		JComboBox<String> jobBox = new JComboBox<>(new String[]{"학생", "직장인", "프리랜서", "기타"});
		JButton signupBtn = new JButton("가입하기");

		panel.add(new JLabel("아이디/이름:")); panel.add(userField);
		panel.add(new JLabel("비밀번호:")); panel.add(passField);
		panel.add(new JLabel("나이:")); panel.add(ageField);
		panel.add(new JLabel("성별:")); panel.add(genderBox);
		panel.add(new JLabel("직업:")); panel.add(jobBox);
		panel.add(new JLabel("")); panel.add(signupBtn);


		signupBtn.addActionListener(e -> {
			String user = userField.getText().trim();
			String pass = new String(passField.getPassword()).trim();
			int age = Integer.parseInt(ageField.getText());
			String gender = (String) genderBox.getSelectedItem();
			String job = (String) jobBox.getSelectedItem();

			if(user.length() > 10) {
				JOptionPane.showMessageDialog(null, "아이디는 최대 10글자까지 가능합니다.");
				return;
			}

			if(pass.length() < 4) {
				JOptionPane.showMessageDialog(null, "비밀번호는 최소 4글자 이상이어야 합니다.");
				return;
			}

			if(!pass.matches(".*[!@#$%^&*()\\\\-_=+{}\\\\[\\\\]|;:'\\\",.<>/?].*")) {
				JOptionPane.showMessageDialog(null, "반드시 특수문자를 포함해야합니다.");
				return;
			}

			if (controller.register(user, pass, age, gender, job)) {
				JOptionPane.showMessageDialog(frame, "가입 완료!");
				frame.dispose();
				new LoginView().show();
			} else {
				JOptionPane.showMessageDialog(frame, "이미 존재하는 아이디입니다!");
				frame.dispose();
				new LoginView().show();
			}
		});

		frame.add(panel);
		frame.setVisible(true);
	}
}
