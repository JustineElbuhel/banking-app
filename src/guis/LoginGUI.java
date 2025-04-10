package guis;

import db_objs.MyJDBC;
import db_objs.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
    This gui will allow the user to login or launch the register gui
    This extends from the BaseFrame which means we will need to define our own addGuiComponent()
 */
public class LoginGUI extends BaseFrame{

    public LoginGUI(){
        super("Banking App Login");
    }

    @Override
    protected void addGuiComponents() {
        // create banking app label
        JLabel bankingAppLabel = new JLabel("Banking Application");

        // set the location and the size of the gui component
        bankingAppLabel.setBounds(0, 20, super.getWidth(), 40);

        // change the font style
        bankingAppLabel.setFont(new Font("Dialog", Font.BOLD, 32));

        // center the text for bankingAppLabel
        bankingAppLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // add to gui
        add(bankingAppLabel);

        // username label
        JLabel usernameLabel = new JLabel("Username:");

        // getWidth returns us the width of our frame which is about 420
        usernameLabel.setBounds(20, 120, getWidth() - 30, 24);

        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(usernameLabel);

        // create username field
        JTextField usernameField = new JTextField();
        usernameField.setBounds(20, 160, getWidth() - 50, 40);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(usernameField);

        // password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 220, getWidth() - 30, 24);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordLabel);

        // password field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20, 260, getWidth() - 50, 40);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(passwordField);

        // create login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(20, 400, getWidth() - 50, 40);
        loginButton.setFont(new Font("Dialog", Font.BOLD, 28));

        // set cursor to hand  when hovering over the button
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // add functionality to login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get username
                String username = usernameField.getText();

                // get password
                String password = String.valueOf(passwordField.getPassword());

                // validate login credentials
                User user = MyJDBC.validateLogin(username, password);

                // if user in null it means invalid login credentials otherwise it is a valid account
                if (user != null) {
                    // means valid login
                    // dispose of this gui
                    LoginGUI.this.dispose();

                    // launch bank app gui
                    BankingAppGui bankingAppGui = new BankingAppGui(user);
                    bankingAppGui.setVisible(true);

                    // show success dialog
                    JOptionPane.showMessageDialog(bankingAppGui, "Login Success!");
                } else {
                    // invalid login credentials
                    JOptionPane.showMessageDialog(LoginGUI.this, "Login Failed");

                }
            }
        });
        add(loginButton );

        // create register label
        JLabel registerLabel = new JLabel("<html><a href=\"#\">Don't have an account? Register Here</a></html>");
        registerLabel.setBounds(0, 460, getWidth() - 10, 30);
        registerLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // add an event listener so when the mouse is clicked it closes the loginGui and launch the RegisterGui
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // dispose of this gui
                LoginGUI.this.dispose();

                // open the registration gui
                RegisterGUI registerGUI = new RegisterGUI();
                registerGUI.setVisible(true);
            }
        });
        add(registerLabel);
    }
}









































