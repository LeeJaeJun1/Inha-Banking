package bank.io.view;

import bank.io.controller.ProductController;
import bank.io.model.Product;
import bank.io.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class RecommendationView extends JFrame {
    private final ProductController controller = new ProductController();
    private JPanel resultPanel;
    private JScrollPane scrollPane;

    public RecommendationView(User user) {
        setTitle(user.username + "님을 위한 예적금 추천");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(resultPanel);

        JPanel buttonPanel = new JPanel();
        JButton btn100 = new JButton("100만원 기준");
        JButton btn500 = new JButton("500만원 기준");
        JButton btn1000 = new JButton("1000만원 기준");

        btn100.addActionListener(e -> updateResults(user, 1000000));
        btn500.addActionListener(e -> updateResults(user, 5000000));
        btn1000.addActionListener(e -> updateResults(user, 10000000));

        buttonPanel.add(btn100);
        buttonPanel.add(btn500);
        buttonPanel.add(btn1000);

        setLayout(new BorderLayout(10, 10));
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        updateResults(user, 1000000);
    }

    private void updateResults(User user, double principal) {
        resultPanel.removeAll();

        List<Product> products = controller.getRecommendations(user.age, user.gender, user.job);
        List<ProductScore> scores = new ArrayList<>();

        for (Product p : products) {
            double score = calculateTotal(principal, p.interestRate, p.periodMonths);
            scores.add(new ProductScore(p, score));
        }

        scores.sort((a, b) -> Double.compare(b.total, a.total));

        JLabel topLabel = new JLabel((int)(principal / 10000) + "만원 기준 추천 순위 (1~3위)");
        topLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        topLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultPanel.add(topLabel);

        List<ProductScore> top3 = scores.subList(0, Math.min(3, scores.size()));
        Set<String> top3Names = top3.stream().map(ps -> ps.product.name).collect(Collectors.toSet());

        int rank = 1;
        for (ProductScore ps : top3) {
            JPanel rankCard = new JPanel(new GridLayout(0, 1));
            rankCard.setBorder(BorderFactory.createTitledBorder(rank + "위: " + ps.product.name));
            rankCard.add(new JLabel("종류: " + ps.product.type));
            rankCard.add(new JLabel("금리: " + ps.product.interestRate + "%"));
            rankCard.add(new JLabel("기간: " + ps.product.periodMonths + "개월"));
            rankCard.add(new JLabel("예상 수익: " + String.format("%,d원", (int)(ps.total - principal))));
            rank++;
            resultPanel.add(rankCard);
        }

        JLabel allLabel = new JLabel("전체 추천 상품 목록");
        allLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        allLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultPanel.add(allLabel);

        for (Product p : products) {
            if (top3Names.contains(p.name)) continue; // 중복 방지
            JPanel card = new JPanel(new GridLayout(0, 1));
            card.setBorder(BorderFactory.createTitledBorder(p.name));
            card.add(new JLabel("종류: " + p.type));
            card.add(new JLabel("금리: " + p.interestRate + "%"));
            card.add(new JLabel("기간: " + p.periodMonths + "개월"));
            double total = calculateTotal(principal, p.interestRate, p.periodMonths);
            card.add(new JLabel("예상 수익: " + String.format("%,d원", (int)(total - principal))));
            resultPanel.add(card);
        }

        resultPanel.revalidate();
        resultPanel.repaint();
    }

    private double calculateTotal(double principal, double rate, int months) {
        return principal * Math.pow(1 + (rate / 100.0), months / 12.0);
    }

    private static class ProductScore {
        Product product;
        double total;

        ProductScore(Product product, double total) {
            this.product = product;
            this.total = total;
        }
    }
}
