package db_objs;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.FormatterClosedException;

/*
    JDBC class is used to interact with our MySQL db to perform actions such as retrieving and updating our db
 */
public class MyJDBC {
    // db configurations
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/bank_app";
    private static final String DB_USERNAME = "username";
    private static final String DB_PASSWORD = "password";

    public static User validateLogin(String username, String password){
        // if valid login credentials return an object with the user's information
        try{
            // establish a connection to the db using configurations
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            // create SQL query
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ? AND password = ?"
            );

            // replace the ?s with values
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // execute query and store into a result set
            ResultSet resultSet = preparedStatement.executeQuery();

            // next() returns true or false
            // true - query returned data and result set now points to the first row
            // false - query returned no data and resultSet equals null
            if(resultSet.next()){
                // success
                // get id
                int userId = resultSet.getInt("id");

                // get current balance
                BigDecimal currentBalance = resultSet.getBigDecimal("current_balance");

                // return user obj
                return new User(userId, username, password, currentBalance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    // register new user to the db
    // true - register success
    // false - register failed
    public static boolean register(String username, String password){
        try{
            // check if username is taken, if it is available add new user
            if(!checkUser(username)){
                Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

                PreparedStatement addNewUser = connection.prepareStatement(
                        "INSERT INTO users " +
                                "(username, password, current_balance)" + "Values(?,?,?)"
                );

                addNewUser.setString(1, username);
                addNewUser.setString(2, password);
                addNewUser.setBigDecimal(3, new BigDecimal(0));

                addNewUser.executeUpdate();
                return true;
            }


        } catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    // check if username already exists in the db
    // true - user exists
    // false - user does not exist in the db
    private static boolean checkUser(String username){
        try{
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            PreparedStatement checkUserExists = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ?"
            );

            checkUserExists.setString(1, username);
            ResultSet resultSet = checkUserExists.executeQuery();

            // this means the resultSet returned no data meaning the username is available
            if(!resultSet.next()){
                return false;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // true - update to db was a success
    // false - failed to update db
    public static boolean addTransactionToDatabase(Transaction transaction){
        try{
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            // NOW() puts in the current date
            PreparedStatement insertTransaction = connection.prepareStatement(
                    "INSERT INTO transactions (user_id, transaction_type, transaction_amount, transaction_date) " +
                            "VALUES(?,?,?,NOW())"
            );

            insertTransaction.setInt(1, transaction.getUserId());
            insertTransaction.setString(2, transaction.getTransactionType());
            insertTransaction.setBigDecimal(3, transaction.getTransactionAmount());

            insertTransaction.executeUpdate();

            return true;
        } catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    // true - update balance success
    // false - update balance fail
    public static boolean updateCurrentBalance(User user){
        try{
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            PreparedStatement updateBalance = connection.prepareStatement(
                    "Update users SET current_balance = ? WHERE id = ?"
            );

            updateBalance.setBigDecimal(1, user.getCurrentBalance());
            updateBalance.setInt(2, user.getId());

            updateBalance.executeUpdate();

            return true;
        } catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    // true - transaction success
    // false - transaction failed
    public static boolean transfer(User user, String transferredUsername, float transferAmount){
        try{
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            PreparedStatement queryUser = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ?"
            );

            queryUser.setString(1, transferredUsername);

            ResultSet resultSet = queryUser.executeQuery();

            while(resultSet.next()){
                User transferredUser = new User(
                        resultSet.getInt("id"),
                        transferredUsername,
                        resultSet.getString("password"),
                        resultSet.getBigDecimal("current_balance")
                );

                // create a transaction to remove out of the original account making a transfer
                Transaction transactionFrom = new Transaction(
                        user.getId(),
                        "Transfer",
                        new BigDecimal(-transferAmount),
                        null);

                // create a transaction for the user receiving the transfer
                Transaction transactionTo = new Transaction(
                        transferredUser.getId(),
                        "Transfer",
                        new BigDecimal(transferAmount),
                        null);

                // update the transfer user
                transferredUser.setCurrentBalance(transferredUser.getCurrentBalance().add(new BigDecimal(transferAmount)));
                updateCurrentBalance(transferredUser);

                // update the current user
                user.setCurrentBalance(user.getCurrentBalance().subtract(new BigDecimal(transferAmount)));
                updateCurrentBalance(user);

                // add these transactions to db
                addTransactionToDatabase(transactionTo);
                addTransactionToDatabase(transactionFrom);

                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public static ArrayList<Transaction> getPastTransactions(User user){
        ArrayList<Transaction> pastTransactions = new ArrayList<>();
        try{
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            PreparedStatement transactionQuery = connection.prepareStatement(
                    "SELECT * FROM transactions WHERE user_id = ?"
            );

            transactionQuery.setInt(1, user.getId());
            ResultSet resultSet = transactionQuery.executeQuery();

            while (resultSet.next()) {
                Transaction transaction = new Transaction(
                        resultSet.getInt("user_id"),
                        resultSet.getString("transaction_type"),
                        resultSet.getBigDecimal("transaction_amount"),
                        resultSet.getDate("transaction_date")
                );

                pastTransactions.add(transaction);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return pastTransactions;
    }
}











