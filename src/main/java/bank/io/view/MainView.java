package bank.io.view;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import bank.io.controller.ProductController;
import bank.io.model.Product;
import bank.io.model.User;

public class MainView {
	private final ProductController controller = new ProductController();

	public void createWithUser(User user) {
		JFrame frame = new JFrame(user.username + "님을 위한 예적금 추천");
		frame.setSize(500, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(2, 2));
		infoPanel.add(new JLabel("나이:"));
		infoPanel.add(new JLabel(String.valueOf(user.age)));
		infoPanel.add(new JLabel("성별/직업:"));
		infoPanel.add(new JLabel(user.gender + " / " + user.job));

		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane(resultPanel);

		List<Product> products = controller.getRecommendations(user.age, user.gender, user.job);
		for (Product p : products) {
			JPanel card = new JPanel(new GridLayout(0, 1));
			card.setBorder(BorderFactory.createTitledBorder(p.name));
			card.add(new JLabel("종류: " + p.type));
			card.add(new JLabel("금리: " + p.interestRate + "%"));
			card.add(new JLabel("기간: " + p.periodMonths + "개월"));
			card.add(new JLabel("예상 수익: " + calculate(p) + "원"));
			resultPanel.add(card);
		}

		frame.setLayout(new BorderLayout(10, 10));
		frame.add(infoPanel, BorderLayout.NORTH);
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	private String calculate(Product p) {
		double principal = 1000000;
		double total = principal * Math.pow(1 + (p.interestRate / 100), p.periodMonths / 12.0);
		return String.format("%,d", (int) total);
	}
}
