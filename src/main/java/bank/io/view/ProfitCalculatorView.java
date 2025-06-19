package bank.io.view;

import bank.io.controller.ProductController;
import bank.io.model.Product;
import bank.io.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProfitCalculatorView extends JFrame {

    private final ProductController controller = new ProductController();

    public ProfitCalculatorView(User user) {
        setTitle("추천 상품 기반 수익 계산기");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel productLabel = new JLabel("추천 상품:");
        productLabel.setForeground(Color.BLACK);
        JComboBox<Product> productCombo = new JComboBox<>();
        List<Product> products = controller.getRecommendations(user.age, user.gender, user.job);
        for (Product p : products) {
            productCombo.addItem(p);
        }

        JLabel rateLabel = new JLabel("금리 (%):");
        rateLabel.setForeground(Color.BLACK);
        JTextField rateField = new JTextField();
        rateField.setEditable(false);

        JLabel periodLabel = new JLabel("기간 (개월):");
        periodLabel.setForeground(Color.BLACK);
        JTextField periodField = new JTextField();
        periodField.setEditable(false);

        JLabel amountLabel = new JLabel("원금 입력 (만원):");
        amountLabel.setForeground(Color.BLACK);
        JTextField amountField = new JTextField();

        JLabel resultLabel = new JLabel("예상 수익:");
        resultLabel.setForeground(Color.BLACK);
        JLabel resultValue = new JLabel("");
        resultValue.setForeground(Color.BLACK);

        JButton calculateBtn = new JButton("계산하기");

        // 상품 선택 시 자동으로 금리/기간 채우기
        productCombo.addActionListener(e -> {
            Product selected = (Product) productCombo.getSelectedItem();
            if (selected != null) {
                rateField.setText(String.valueOf(selected.interestRate));
                periodField.setText(String.valueOf(selected.periodMonths));
            }
        });

        // 계산 버튼
        calculateBtn.addActionListener(e -> {
            try {
                double principal = Double.parseDouble(amountField.getText()) * 10000;
                double rate = Double.parseDouble(rateField.getText()); // 퍼센트 그대로 입력됨
                int months = Integer.parseInt(periodField.getText());

                double total = principal * Math.pow(1 + (rate / 100), months / 12.0);
                int profit = (int) (total - principal);
                resultValue.setText(String.format("%,d원", profit));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "모든 항목을 정확히 입력해주세요.");
            }
        });

        // 기본 선택 설정
        if (!products.isEmpty()) {
            productCombo.setSelectedIndex(0);
            Product p = products.get(0);
            rateField.setText(String.valueOf(p.interestRate));
            periodField.setText(String.valueOf(p.periodMonths));
        }

        panel.add(productLabel); panel.add(productCombo);
        panel.add(rateLabel); panel.add(rateField);
        panel.add(periodLabel); panel.add(periodField);
        panel.add(amountLabel); panel.add(amountField);
        panel.add(resultLabel); panel.add(resultValue);
        panel.add(new JLabel()); panel.add(calculateBtn);

        add(panel);
    }
}
