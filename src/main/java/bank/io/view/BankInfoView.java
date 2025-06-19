package bank.io.view;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URI;

public class BankInfoView extends JFrame {

    public BankInfoView() {
        setTitle("은행 정보 보기");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        String[][] banks = {
                {"카카오뱅크", "images/kakao.png", "https://www.kakaobank.com/products/savings"},
                {"국민은행", "images/KB.png", "https://obank.kbstar.com/quics?page=C060866&cc=b030658:b029684&isNew=N&prcode=DP01001510"},
                {"우리은행", "images/wori.jpeg", "https://spot.wooribank.com/pot/Dream?withyou=PODEP0001"},
                {"신한은행", "images/sinhan.png", "https://bank.shinhan.com/index.jsp#020102010110"}
        };

        for (String[] bank : banks) {
            try {
                Image img = ImageIO.read(new File(bank[1]));
                Image scaled = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                JLabel label = new JLabel(new ImageIcon(scaled));
                label.setToolTipText(bank[0]);
                label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                label.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        try {
                            Desktop.getDesktop().browse(new URI(bank[2]));
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(panel, "웹페이지 열기에 실패했습니다.");
                        }
                    }
                });
                panel.add(label);
            } catch (Exception ex) {
                JLabel fallback = new JLabel(bank[0]);
                panel.add(fallback);
            }
        }

        add(panel);
    }
}

