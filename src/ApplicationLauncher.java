import db_objs.User;
import guis.BankingAppGui;
import guis.LoginGUI;
import guis.RegisterGUI;

import javax.swing.*;
import java.math.BigDecimal;

public class ApplicationLauncher {
    public static void main(String[] args) {
        // use invokeLater() to make updates to the GUI more thread safe
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
//                new LoginGUI().setVisible(true);
                new RegisterGUI().setVisible(true);
//                new BankingAppGui(
//                        new User(1, "username", "password", new BigDecimal("20.00")))
//                        .setVisible(true);
            }
        });

        }
    }