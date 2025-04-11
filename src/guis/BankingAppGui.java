package guis;

import com.mysql.cj.log.Log;
import db_objs.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
    Performs banking functions such as depositing, withdrawing, seeing past transactions, and transferring
    This extends from the BaseFrame meaning we will need to define our own addGuiComponent()
 */
public class BankingAppGui extends BaseFrame implements ActionListener {
    private JTextField currentBalanceField;

    public JTextField getCurrentBalanceField(){
        return currentBalanceField;
    }

    public BankingAppGui(User user){
        super("Banking App", user);
    }

    @Override
    protected void addGuiComponents() {
        // create welcome message
        String welcomeMessage = "<html>" +
                "<body style='text-align:center'>" +
                "<b>Hello " + user.getUsername() + "</b><br>" +
                "What would you like to do today</body></html>";
        JLabel welcomeMessageLabel = new JLabel(welcomeMessage);
        welcomeMessageLabel.setBounds(0, 20, getWidth() - 18, 40);
        welcomeMessageLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
        welcomeMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeMessageLabel);

        // current balance label
        JLabel currentBalanceLabel = new JLabel("Current Balance:");
        currentBalanceLabel.setBounds(0,  80, getWidth() - 10, 30);
        currentBalanceLabel.setFont(new Font("Dialog", Font.BOLD, 22));
        currentBalanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(currentBalanceLabel);

        // current balance field
        currentBalanceField = new JTextField("$" + user.getCurrentBalance());
        currentBalanceField.setBounds(15, 120, getWidth() - 50, 40);
        currentBalanceField.setFont(new Font("Dialog", Font.PLAIN, 28));
        currentBalanceField.setHorizontalAlignment(SwingConstants.RIGHT);

        // remove ability to edit field
        currentBalanceField.setEnabled(false);
        currentBalanceField.setDisabledTextColor(Color.BLACK);
        add(currentBalanceField);

        // deposit button
        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(15, 180, getWidth() - 50, 50 );
        depositButton.setFont(new Font("Dialog", Font.BOLD, 28));
        depositButton.setFocusPainted(false);
        depositButton.addActionListener(this);
        add(depositButton);

        // withdraw button
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(15, 250, getWidth() - 50, 50);
        withdrawButton.setFont(new Font("Dialog", Font.BOLD, 28));
        withdrawButton.addActionListener(this);
        add(withdrawButton);


        // transfer button
        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(15, 320, getWidth() - 50, 50);
        transferButton.setFont(new Font("Dialog", Font.BOLD, 28));
        transferButton.addActionListener(this);
        add(transferButton);

        // past transaction button
        JButton pastTransactionButton = new JButton("Past Transactions");
        pastTransactionButton.setBounds(15, 390, getWidth() - 50, 50);
        pastTransactionButton.setFont(new Font("Dialog", Font.BOLD, 28));
        pastTransactionButton.addActionListener(this);
        add(pastTransactionButton);

        // logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(15, 460, getWidth() - 50, 50);
        logoutButton.setFont(new Font("Dialog", Font.BOLD, 28));
        logoutButton.addActionListener(this);
        add(logoutButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonPressed = e.getActionCommand();

        // logout clicked
        if(buttonPressed.equalsIgnoreCase("Logout")){
            new LoginGUI().setVisible(true);

            this.dispose();

            // don't continue running the rest of the code
            return;
        }

        // other functions
        BankingAppDialog bankingAppDialog = new BankingAppDialog(this, user);

        // set the title of the dialog header to the action
        bankingAppDialog.setTitle(buttonPressed);

        // if the button pressed  is deposit, withdraw, or transfer
        if(buttonPressed.equalsIgnoreCase("Deposit") || buttonPressed.equalsIgnoreCase("Withdraw")
                || buttonPressed.equalsIgnoreCase("Transfer")){
            // add in the current balance and the amount gui components to the dialog
            bankingAppDialog.addCurrentBalanceAndAmount();

            // add action button
            bankingAppDialog.addActionButton(buttonPressed);

            // the transfer button will need more components\
            if(buttonPressed.equalsIgnoreCase("Transfer")){
                bankingAppDialog.addUserField();
            }
            bankingAppDialog.setVisible(true);
        } else if(buttonPressed.equalsIgnoreCase("Past Transactions")){
            bankingAppDialog.addPastTransactionComponent();
        }

        // make the app dialog visible
        bankingAppDialog.setVisible(true);
    }
}
