package sample.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.User;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler {
    public static Connection getConnection(){
        Connection con = null;
        try{
            System.out.println("Connecting database...");
            //Registering the HSQLDB JDBC driver
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            //Creating the connection with HSQLDB
            String url = "jdbc:hsqldb:file:C:\\Users\\Nour\\Documents\\INF202_PROJECT\\src\\assets\\mydatabase\\;shutdown=true";
            con = DriverManager.getConnection(url, "nour", "268953");
            if (con != null){
                System.out.println("Connection created successfully");
            }else{
                System.out.println("Problem with creating connection");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database connection error:" + e);
        }
        return con;
    }


    // ============= user related ===================

    public static User getUser(String username, String password) throws SQLException {
        // return user with username and level if exist and null if not
        Connection con = getConnection();
        Statement statement = con.createStatement();
        ResultSet resultSet;
        int hashedPassword = password.hashCode();
        resultSet = statement.executeQuery("SELECT * FROM user WHERE username = '" + username + "' AND password = " + hashedPassword + " ;");

        // cursor is initially positioned before the first row
        // next() returns true if the new current row is valid; false if there are no more rows
        if(resultSet.next()){
            System.out.println("The existing of " + username + " is confirmed and the Password has been accepted");
            User user = new User();
            user.setUsername(resultSet.getString("username"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setLevel(resultSet.getInt("level"));
            con.close();
            return user;
        }else{
            System.out.println("Username or Password passed by the user is wrong");
            con.close();
            System.out.println("the connection is closed");
            return null;
        }
    }

    public static boolean isUsernameTaken(String username) throws SQLException {
        Connection con = getConnection();
        Statement statement = con.createStatement();
        ResultSet resultSet;
        resultSet = statement.executeQuery("SELECT * FROM user WHERE username = '" + username + "' ;");
        if(resultSet.next()){
            System.out.println("Username is taken");
            con.close();
            System.out.println("the connection is closed");
            return true;
        }else {
            System.out.println("Username is not taken");
            con.close();
            System.out.println("the connection is closed");
            return false;
        }
    }

    public static void addNewUser(User user) throws SQLException {
        Connection con = getConnection();
        Statement statement = con.createStatement();
        int result;
        int password = user.getPassword().hashCode();
        result = statement.executeUpdate("INSERT INTO USER (username, password, name, surname, level) " +
                " VALUES ('"+ user.getUsername() +"', " + password + ", '" + user.getName()
                + "', '" + user.getSurname() + "', "+ user.getLevel() +") ;");
        con.close();
        System.out.println("the connection is closed");
    }

    public static ObservableList<User> getAllUsers() throws SQLException {
        Connection con = getConnection();
        Statement statement = con.createStatement();
        ResultSet resultSet;
        resultSet = statement.executeQuery("SELECT * from user;");

        ObservableList<User> users = FXCollections.observableArrayList();
        while (resultSet.next()){
            users.add(new User(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4),
                    resultSet.getInt(5)));
        }
        con.close();
        return users;
    }

    public static boolean deleteUser(User user) throws SQLException {
        // return false if the user to be deleted is the only admin in the system
        // this admin will not be deleted
        // return true otherwise with deleting the user
        Connection con = getConnection();
        Statement statement = con.createStatement();
        ResultSet resultSet;
        int result;
        if(user.getLevel() > 2){
            resultSet = statement.executeQuery("SELECT * from user WHERE level > 2; ");
            int count = 0;
            while (resultSet.next()){
                count++;
            }
            if(count == 1){
                return false;
            }
        }
        result = statement.executeUpdate("Delete FROM user WHERE username = '" + user.getUsername() + "';");
        if (result == 0){
            System.out.println("Something went wrong when deleting the user. the user has not been deleted");
        }
        con.close();
        return true;
    }

    public static boolean editUser(User oldUser, User newUser) throws SQLException {

        // ======= getting connection =======
        Connection con = getConnection();
        Statement statement = con.createStatement();
        int result;
        ResultSet resultSet;

        // ========= dealing with only admin thing ========
        // if level has been changed check if the user is the only admin
        if(oldUser.getLevel() != newUser.getLevel()){ // if the level has been changed
            if(oldUser.getLevel() > 2 && newUser.getLevel() <= 2){ // if the level has been reduced
                resultSet = statement.executeQuery("SELECT * from user WHERE level > 2; ");
                int count = 0;
                while (resultSet.next()){
                    count++;
                }
                if(count == 1){
                    // old user is the only admin and his level has been reduced
                    // this is not acceptable and the change will not be done
                    con.close();
                    return false;
                }
            }
        }
        // all other possibilities are acceptable
        // ============= dealing with the password ==============

        int newPassword;
        if(newUser.getPassword().equals("")){
            resultSet = statement.executeQuery("SELECT password FROM user WHERE username = '" + oldUser.getUsername() + "' ;");
            resultSet.next();
            newPassword = resultSet.getInt("password");
        }else{
            newPassword = newUser.getPassword().hashCode();
        }
        // ============== update the user information =============
        result = statement.executeUpdate(
                "UPDATE user SET " +
                "username = '" + newUser.getUsername() + "'," +
                "password = "+ newPassword +"," +
                "name = '"+ newUser.getName() +"'," +
                "surname = '"+ newUser.getSurname() +"', " +
                "level = "+ newUser.getLevel() +" " +
                "WHERE username = '"+ oldUser.getUsername() +"';");
        con.close();
        return true;
    }
}
