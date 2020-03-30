package sample.database;

import classes.User;

import java.sql.*;

public class DatabaseHandler {
    private Connection getConnection(){
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
    // return user with username and level if exist and null if not
    public User getUser(String username, String password) throws SQLException {
        Connection con = getConnection();
        Statement statement = con.createStatement();
        ResultSet resultSet;
        int hashedPassword = password.hashCode();
        System.out.println("Trying to get the user: " + username);
        resultSet = statement.executeQuery("SELECT * FROM user WHERE username = '" + username + "' AND password = " + hashedPassword + "    ;");

        // cursor is initially positioned before the first row
        // next() returns true if the new current row is valid; false if there are no more rows
        if(resultSet.next()){
            System.out.println("The existing of " + username + " is confirmed and the Password has been accepted");
            User user = new User();
            user.setUsername(resultSet.getString("username"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setLevel(resultSet.getInt("level"));
            user.setEmail(resultSet.getString("email"));
            user.setTel(resultSet.getString("tel"));
            con.close();
            return user;
        }else{
            System.out.println("Username or Password passed by the user is wrong");
            con.close();
            return null;
        }
    }

    public boolean isUsernameTaken(String username) throws SQLException {
        Connection con = getConnection();
        Statement statement = con.createStatement();
        ResultSet resultSet;
        resultSet = statement.executeQuery("SELECT * FROM user WHERE username = '" + username + "' ;");
        if(resultSet.next()){
            System.out.println("Username is taken");
            con.close();
            return true;
        }else{
            System.out.println("Username is not taken");
            con.close();
            return false;
        }
    }

    public void addNewUser(User user) throws SQLException {
        Connection con = getConnection();
        Statement statement = con.createStatement();
        int result;
        int password = user.getPassword().hashCode();
        result = statement.executeUpdate("INSERT INTO USER (username, password, name, surname, level, email, tel)" +
                " VALUES ('"+ user.getUsername() +"', " + password + ", '" + user.getName()
                + "', '" + user.getSurname() + "', "+ user.getLevel() +", '" + user.getEmail()
                + "', '"+user.getTel()+"');");
        con.close();
    }

}
