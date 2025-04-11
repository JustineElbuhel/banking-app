package guis;

import db_objs.MyJDBC;
import db_objs.Transaction;
import db_objs.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

/*
    Displays a custom message dialog for the BankingAppGui
 */
public class BankingAppDialog extends JDialog implements ActionListener {
    private User user;
    private BankingAppGui bankingAppGui;
    private JLabel balanceLabel;
    private JLabel enterAmountLabel;
    private JLabel enterUserLabel;
    private JTextField enterAmountField;
    private JTextField enterUserField;
    private JButton actionButton;
    private JPanel pastTransactionPanel;
    private ArrayList<Transaction> pastTransactions;

    public BankingAppDialog(BankingAppGui bankingAppGui, User user){
        // set the size
        setSize(400, 400);

        // add focus to the dialog (can't interact with anything else until the dialog is closed)
        setModal(true);

        //loads in the center of our banking gui
        setLocationRelativeTo(bankingAppGui);

        // when the user closes the dialog,it releases its resources that are being used
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // allows us to manually specify the size and position of each component
        setLayout(null);

        // we will need reference to our gui so that we can update the current balance
        this.bankingAppGui = bankingAppGui;

        // we will need access to the user info to make updates to our db or retrieve data
        this.user = user;
    }

    public void addCurrentBalanceAndAmount(){
        // balance label
        balanceLabel = new JLabel("Balance: $" + user.getCurrentBalance());
        balanceLabel.setBounds(0, 10, getWidth() - 20, 20);
        balanceLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(balanceLabel);

        // enter amount label
        enterAmountLabel = new JLabel("Enter Amount:");
        enterAmountLabel.setBounds(0, 50, getWidth() - 20, 20);
        enterAmountLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountLabel);

        // enter amount field
        enterAmountField = new JTextField();
        enterAmountField.setBounds(15, 80, getWidth() - 50, 40);
        enterAmountField.setFont(new Font("Dialog", Font.BOLD, 20));
        enterAmountField.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountField);
    }

    public void addActionButton(String actionButtonType){
        actionButton = new JButton(actionButtonType);
        actionButton.setBounds(15, 300, getWidth() - 50, 40);
        actionButton.setFont(new Font("Dialog", Font.BOLD, 20));
        actionButton.addActionListener(this);
        add(actionButton);

    }

    public void addUserField(){
        // enter user label
        enterUserLabel = new JLabel("Enter User:");
        enterUserLabel.setBounds(0, 160, getWidth() - 20, 20);
        enterUserLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterUserLabel);

        // enter user  field
        enterUserField = new JTextField();
        enterUserField.setBounds(15, 190, getWidth() - 50, 40);
        enterUserField.setFont(new Font("Dialog", Font.BOLD, 20));
        add(enterUserField);
    }

    public void addPastTransactionComponent(){
        // container where we will store each transaction
        pastTransactionPanel = new JPanel();

        // make layout 1x1
        pastTransactionPanel.setLayout(new BoxLayout(pastTransactionPanel, BoxLayout.Y_AXIS));

        // allow user to scroll on container
        JScrollPane scrollPane = new JScrollPane(pastTransactionPanel);

        // only displays the vertical scroll when it is required
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 20, getWidth() - 15, getHeight() - 80);

        // perform db call to retrieve all past transactions and store into an ArrayList
        pastTransactions = MyJDBC.getPastTransactions(user);

        // iterate through the list and add to the gui
        for(int i = 0; i < pastTransactions.size(); i++){
            // store transaction
            Transaction transaction = pastTransactions.get(i);

            // create a container to store each transaction
            JPanel pastTransactionContainer = new JPanel();
            pastTransactionContainer.setLayout(new BorderLayout());

            // transaction type label
            JLabel transactionTypeLabel = new JLabel(String.valueOf(transaction.getTransactionType()));
            transactionTypeLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            // transaction amount label
            JLabel transactionAmountLabel = new JLabel(String.valueOf(transaction.getTransactionAmount()));
            transactionAmountLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            // transaction date label
            JLabel transactionDateLabel = new JLabel(String.valueOf(transaction.getTransactionDate()));
            transactionDateLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            // add to container
            pastTransactionContainer.add(transactionTypeLabel, BorderLayout.WEST);
            pastTransactionContainer.add(transactionAmountLabel, BorderLayout.EAST);
            pastTransactionContainer.add(transactionDateLabel, BorderLayout.SOUTH);

            // add white background color
            pastTransactionContainer.setBackground(Color.white);

            // add a black border around each transaction
            pastTransactionContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            // add transaction to transaction panel
            pastTransactionPanel.add(pastTransactionContainer);
        }

        // add to the dialog
        add(scrollPane);
    }

    private void handleTransaction(String transactionType, Float amountVal){
        Transaction transaction;

        if(transactionType.equalsIgnoreCase("Deposit")){
            // deposit - add to current balance
            user.setCurrentBalance(user.getCurrentBalance().add(new BigDecimal(amountVal)));

            // create transaction
            // we leave data null bc we are going to be using the NOW() in sql which will get the current date
            transaction = new Transaction(user.getId(), transactionType, new BigDecimal(amountVal), null);
        } else {
            // withdraw - subtract from current balance
            user.setCurrentBalance(user.getCurrentBalance().subtract(new BigDecimal(amountVal)));

            // we want to show a negative sign for the amount val when withdrawing
            transaction = new Transaction(user.getId(), transactionType, new BigDecimal(-amountVal), null);
        }

        // update the db
        if(MyJDBC.addTransactionToDatabase(transaction) && MyJDBC.updateCurrentBalance(user)){
            // show success dialog
            JOptionPane.showMessageDialog(this, transactionType + " Successfully");

            // reset the fields
            resetFieldsAndUpdateCurrentBalance();
        } else {
            // show failure dialog
            JOptionPane.showMessageDialog(this, transactionType + " Failed");
        }
    }

    private void handleTransfer(User user, String transferredUser, float amountVal){
        // attempt to perform transfer
        if(MyJDBC.transfer(user, transferredUser, amountVal)){
            // show success message
            JOptionPane.showMessageDialog(this, "Transfer Success");
            resetFieldsAndUpdateCurrentBalance();
        } else {
            JOptionPane.showMessageDialog(this, "Transfer Failed");

        }
    }

    private void resetFieldsAndUpdateCurrentBalance(){
        // reset fields
        enterAmountField.setText("");

        // only appears when transfer is clicked
        if (enterUserField != null) {
            enterUserField.setText("");
        }

        // update current balance on dialog
        balanceLabel.setText("Balance: $" + user.getCurrentBalance());

        // update current balance on the main gui
        bankingAppGui.getCurrentBalanceField().setText("$" + user.getCurrentBalance());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonPressed = e.getActionCommand();

        // get amount value
        float amountVal = Float.parseFloat(enterAmountField.getText());

        if(buttonPressed.equalsIgnoreCase("Deposit")){
            // deposit
            handleTransaction(buttonPressed, amountVal);
        } else {
            // withdrawal or transfer

            // validate input by making sure that withdraw or transfer amount is less than the current balance
            // if result is -1 it means that the entered amount is bigger than the current balance, 0 means equal, 1
            // means that the enteredd amount is less
            int result = user.getCurrentBalance().compareTo(BigDecimal.valueOf(amountVal));
            if(result < 0){
                // display error dialog
                JOptionPane.showMessageDialog(this, "Error: Amount exceeds current balance");
                return;
            }

            if(buttonPressed.equalsIgnoreCase("Withdraw")){
                // withdraw
                handleTransaction(buttonPressed, amountVal);
            } else {
                // transfer
                String transferredUser = enterUserField.getText();
                handleTransfer(user, transferredUser, amountVal);
            }
        }
    }
}

































