// MainView.java
package bank.io.view;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import bank.io.model.User;

public class MainView extends JFrame {
	private final User user;

	public MainView(User user) {
		this.user = user;

		setTitle("INHA-Banking");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLayout(new BorderLayout());
		setJMenuBar(createMenuBar());

		JPanel centerPanel = new JPanel(null); // 배치 직접 제어
		centerPanel.setBackground(Color.WHITE);

		// 이미지 아이콘
		JLabel imageLabel = new JLabel();
		try {
			Image img = ImageIO.read(new File("images/Duck.png")); // 실제 이미지 경로
			Image scaled = img.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
			imageLabel.setIcon(new ImageIcon(scaled));
		} catch (IOException e) {
			e.printStackTrace();
		}
		imageLabel.setBounds(30, 30, 120, 120);
		centerPanel.add(imageLabel);

		JLabel greeting = new JLabel("안녕하세요, " + user.username + "님. INHA-Banking입니다.");
		greeting.setFont(new Font("SansSerif", Font.BOLD, 20));
		greeting.setForeground(Color.BLACK);
		greeting.setBounds(170, 70, 400, 40);
		centerPanel.add(greeting);

		add(centerPanel, BorderLayout.CENTER);
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		// 첫 번째 메뉴: 추천 상품
		JMenu recommendMenu = new JMenu("추천 상품");
		JMenuItem recommendItem = new JMenuItem("추천 상품 보기");
		recommendItem.addActionListener(e -> {
			RecommendationView view = new RecommendationView(user);
			view.setVisible(true);
		});
		recommendMenu.add(recommendItem);

		// 두 번째 메뉴: 수익 계산
		JMenu profitMenu = new JMenu("수익 계산");
		JMenuItem profitItem = new JMenuItem("예상 수익 확인");
		profitItem.addActionListener(e -> {
			ProfitCalculatorView view = new ProfitCalculatorView(user);
			view.setVisible(true);
		});
		profitMenu.add(profitItem);

		// 세 번째 메뉴: 자세한 정보
		JMenu detailMenu = new JMenu("자세한 정보");
		JMenuItem detailItem = new JMenuItem("은행 정보 보기");
		detailItem.addActionListener(e -> {
			BankInfoView view = new BankInfoView();  // 새 창으로 은행 이미지 및 링크 보여줌
			view.setVisible(true);
		});
		detailMenu.add(detailItem);

	// 메뉴바에 세 메뉴 추가
		menuBar.add(recommendMenu);
		menuBar.add(profitMenu);
		menuBar.add(detailMenu);


		return menuBar;
	}

	public void showMain() {
		setVisible(true);
	}
}
