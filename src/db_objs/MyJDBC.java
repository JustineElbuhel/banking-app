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

}











