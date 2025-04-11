package guis;

import db_objs.MyJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        // functionality to register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get username
                String username = usernameField.getText();

                // get password
                String password = String.valueOf(passwordField.getPassword());

                // get confirmed password
                String rePassword = String.valueOf(rePasswordField.getPassword());

                System.out.println(username);
                System.out.println(password);
                System.out.println(rePassword);

                // validate user input
                if(validateUserInput(username, password, rePassword)){
                    //register user to the db
                    if(MyJDBC.register(username, password)){
                        // close registration gui
                        RegisterGUI.this.dispose();

                        // open the login gui
                        LoginGUI loginGUI = new LoginGUI();
                        loginGUI.setVisible(true);

                        // show success message
                        JOptionPane.showMessageDialog(loginGUI, "Successfully registered account");
                    } else {
                        JOptionPane.showMessageDialog(RegisterGUI.this, "Failed to register account");
                    }
                } else {
                    // invalid user input
                    JOptionPane.showMessageDialog(RegisterGUI.this,
                            "Username must be at least 6 characters and/or" +
                                    " Passwords must match");
                }
            }
        });
        add(registerButton);

        // login link
        JLabel loginLabel = new JLabel("<html><a href=\"#\">Already a user? Login here</a></html>");
        loginLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        loginLabel.setBounds(0, 500,  getWidth() - 10, 30);
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(loginLabel);
    }

    // validates user input
    private boolean validateUserInput(String username, String password, String rePassword){
        // all fields must have a value
        if (username.length() == 0 || password.length() == 0 || rePassword.length() == 0) {
            return false;
        }

        if(username.length() < 6){
            return false;
        }

        if(!password.equals(rePassword)){
            return false;
        }

        return true;

    }
}
















