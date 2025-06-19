package bank.io;

import bank.io.db.H2Database;
import bank.io.view.LoginView;

public class MainApp {
    public static void main(String[] args) {
        H2Database.init();
        javax.swing.SwingUtilities.invokeLater(() -> new LoginView().show());
    }
}
