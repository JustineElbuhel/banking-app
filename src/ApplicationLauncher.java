import guis.LoginGUI;
import guis.RegisterGUI;

import javax.swing.*;

public class ApplicationLauncher {
    public static void main(String[] args) {
        // use invokeLater() to make updates to the GUI more thread safe
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
//                new LoginGUI().setVisible(true);
                new RegisterGUI().setVisible(true);
            }
        });
    }
}