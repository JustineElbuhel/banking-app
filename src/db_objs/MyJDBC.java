package db_objs;

import java.math.BigDecimal;
import java.sql.*;

/*
    JDBC class is used to interact with our MySQL db to perform actions such as retrieving and updating our db
 */
public class MyJDBC {
    // db configurations
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/bank_app";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "4152050516";

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
                                "(username, password)" + "Values(?,?)"
                );

                addNewUser.setString(1, username);
                addNewUser.setString(2, password);

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
}











