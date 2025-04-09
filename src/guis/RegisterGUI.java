package guis;

import javax.swing.*;
import java.awt.*;

public class RegisterGUI extends BaseFrame{

    public RegisterGUI(){
        super("Banking App Register");
    }

    @Override
    protected void addGuiComponents() {
        // create register frame label
        JLabel registerFrameLabel = new JLabel("Register");
        registerFrameLabel.setBounds(0, 20, super.getWidth(), 50);
        registerFrameLabel.setFont(new Font("Dialog", Font.BOLD, 40));
        registerFrameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(registerFrameLabel);

        // create username label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 120, getWidth() - 30, 24);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(usernameLabel);

        // create username text field
        JTextField usernameField = new JTextField();
        usernameField.setBounds(20, 160, getWidth() - 50, 40);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(usernameField);


        // password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 220, getWidth() - 30, 24);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN,  20));
        add(passwordLabel);

        // password field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20, 260, getWidth() - 50, 40);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(passwordField);

        // retype password label
        JLabel rePasswordLabel = new JLabel("Re-type Password:");
        rePasswordLabel.setBounds(20, 320, getWidth() - 30, 24);
        rePasswordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(rePasswordLabel);

        // retype password field
        JPasswordField rePasswordField = new JPasswordField();
        rePasswordField.setBounds(20, 360, getWidth() - 50, 40);
        rePasswordField.setFont(new Font("dialog", Font.PLAIN, 28));
        add(rePasswordField);

        // register button
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(20, 440, getWidth() - 50, 40);
        registerButton.setFont(new Font("Dialog", Font.BOLD, 28));
        registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(registerButton);

        // login link
        JLabel loginLabel = new JLabel("<html><a href=\"#\">Already a user? Login here</a></html>");
        loginLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        loginLabel.setBounds(0, 500,  getWidth() - 10, 30);
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(loginLabel);
    }
}
















