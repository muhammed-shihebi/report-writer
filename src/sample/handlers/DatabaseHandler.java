package sample.handlers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.*;

import java.sql.*;

public class DatabaseHandler {

    private static Connection con;
    private static Statement statement;
    private static ResultSet resultSet;
    private static int result;

    // ====== General Functions ======================

    public static void init() throws SQLException {
        con = getConnection();
        statement = con.createStatement();
    }

    public static void close() throws SQLException {
        System.out.println("Closing database connection...");
        con.close();
        System.out.println("Connection is closed");
    }

    public static Connection getConnection(){
        Connection con = null;
        try{
            System.out.println("Connecting database...");
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
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

    // ====== User Functions =========================

    public static User getUser(String username, String password) throws SQLException {
        // return user with user if exist and null if not
        int hashedPassword = password.hashCode();
        resultSet = statement.executeQuery("SELECT * FROM user WHERE username = '" + username + "' AND password = " + hashedPassword + " ;");
        if(resultSet.next()){
            User user = new User();
            user.setUsername(resultSet.getString("username"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setLevel(resultSet.getInt("level"));
            System.out.println("The existing of " + username + " is confirmed and the Password has been accepted");
            return user;
        }else{
            System.out.println("The username or password entered is incorrect");
            return null;
        }
    }

    public static boolean isUsernameTaken(String username) throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM user WHERE username = '" + username + "' ;");
        if (resultSet.next()){
            System.out.println("Username is taken!");
            return true;
        }else {
            System.out.println("Username is available");
            return false;
        }
    }

    public static void addNewUser(User user) throws SQLException {
        int password = user.getPassword().hashCode();
        result = statement.executeUpdate("INSERT INTO USER (username, password, name, surname, level) " +
                " VALUES ('"+ user.getUsername() +"', " + password + ", '" + user.getName()
                + "', '" + user.getSurname() + "', "+ user.getLevel() +") ;");
        if(result == 0){
            System.out.println("An error occurred while adding the user. The user was not added.");
        }
    }

    // Also used to get all Operators
    public static ObservableList<User> getAllUsers() throws SQLException {
        resultSet = statement.executeQuery("SELECT * from user;");
        ObservableList<User> users = FXCollections.observableArrayList();
        while (resultSet.next()){
            users.add(new User(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4),
                    resultSet.getInt(5)));
        }
        return users;
    }

    public static boolean deleteUser(User user) throws SQLException {
        // return false if the user to be deleted is the only admin in the system
        // this admin will not be deleted
        // return true otherwise with deleting the user
        if(user.getLevel() > User.LEVEL2){
            if(isOnlyAdmin()){
                System.out.println("User is trying to delete the only admin in the System");
                return false;
            }
        }
        result = statement.executeUpdate("Delete FROM user WHERE username = '" + user.getUsername() + "';");
        if (result == 0){
            System.out.println("An error occurred while deleting the user. The user was not deleted.");
        }
        return true;
    }

    public static boolean editUser(User oldUser, User newUser) throws SQLException {
        // if level has been changed check if the user is the only admin
        if(oldUser.getLevel() > 2 && newUser.getLevel() <= 2){ // if the level has been reduced
            if(isOnlyAdmin()){
                System.out.println("User is trying to reduce the level of the only admin.");
                return false;
            }
        }
        int newPassword;
        if(newUser.getPassword().equals("")){ // password is the same as the old one
            newPassword = getOldPassword(oldUser);
        }else{
            newPassword = newUser.getPassword().hashCode();
        }
        result = statement.executeUpdate(
                "UPDATE user SET " +
                "username = '" + newUser.getUsername() + "'," +
                "password = "+ newPassword +"," +
                "name = '"+ newUser.getName() +"'," +
                "surname = '"+ newUser.getSurname() +"', " +
                "level = "+ newUser.getLevel() +" " +
                "WHERE username = '"+ oldUser.getUsername() +"';");
        if (result == 0){
            System.out.println("An error occurred while updating the user. The user was not updated.");
        }
        return true;
    }

    private static boolean isOnlyAdmin() throws SQLException {
        resultSet = statement.executeQuery("SELECT * from user WHERE level > 2; ");
        int count = 0;
        while (resultSet.next()){
            count++;
        }
        return count <= 1;
    }

    private static int getOldPassword(User oldUser) throws SQLException {
        int newPassword;
        resultSet = statement.executeQuery("SELECT password FROM user WHERE username = '" +
                oldUser.getUsername() + "' ;");
        resultSet.next();
        newPassword = resultSet.getInt("password");
        return newPassword;
    }

    public static ObservableList<User> getConformers() throws SQLException {
        resultSet = statement.executeQuery("SELECT * from user WHERE level > 2;");
        ObservableList<User> users = FXCollections.observableArrayList();
        while (resultSet.next()){
            users.add(new User(
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("name"),
                    resultSet.getString("surname"),
                    resultSet.getInt("level")));
        }
        return users;
    }

    public static ObservableList<User> getEvaluators() throws SQLException {
        resultSet = statement.executeQuery("SELECT * from user WHERE level > 1;");
        ObservableList<User> users = FXCollections.observableArrayList();
        while (resultSet.next()){
            users.add(new User(
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("name"),
                    resultSet.getString("surname"),
                    resultSet.getInt("level")));
        }
        return users;
    }


    // ====== Customer Functions =========================

    public static void addNewCustomer(Customer newCustomer) throws SQLException {
        result = statement.executeUpdate("INSERT INTO customer (name, testplace) VALUES  ('" +
                newCustomer.getName()+ "', '"+ newCustomer.getTestPlace() +"')");
        if(result == 0){
            System.out.println("An error occurred while adding the customer. The customer was not added.");
        }
        for(int i = 0; i < newCustomer.getJobOrderNos().size(); i++){
            result = statement.executeUpdate("INSERT INTO joborderno (number, customerid) " +
                    "VALUES  ('"+ newCustomer.getJobOrderNos().get(i).getNumber() +"', IDENTITY())");
        }
        for(int i = 0; i < newCustomer.getProjectNames().size(); i++){
            result = statement.executeUpdate("INSERT INTO projectname (name, customerid) " +
                    "VALUES  ('"+ newCustomer.getProjectNames().get(i).getName() +"', IDENTITY())");
        }
        for(int i = 0; i < newCustomer.getOfferNos().size(); i++){
            result = statement.executeUpdate("INSERT INTO offerno (number, customerid) " +
                    "VALUES  ('"+ newCustomer.getOfferNos().get(i).getNumber() +"', IDENTITY())");
        }
    }

    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        resultSet = statement.executeQuery("SELECT * from customer;");
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        while (resultSet.next()){
            customers.add(new Customer(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("testplace")
            ));
        }
        for(int i = 0; i < customers.size(); i++){
            ObservableList<JobOrderNo> jobOrderNos = FXCollections.observableArrayList();
            ObservableList<ProjectName> projectNames = FXCollections.observableArrayList();
            ObservableList<OfferNo> offerNos = FXCollections.observableArrayList();

            resultSet = statement.executeQuery("SELECT * from joborderno " +
                    "where customerid = "+ customers.get(i).getId() +";");
            while (resultSet.next()){
                jobOrderNos.add(new JobOrderNo(resultSet.getString("number")));
            }

            resultSet = statement.executeQuery("SELECT * from projectname " +
                    "where customerid = "+ customers.get(i).getId() +";");
            while (resultSet.next()){
                projectNames.add(new ProjectName(resultSet.getString("name")));
            }

            resultSet = statement.executeQuery("SELECT * from offerno " +
                    "where customerid = "+ customers.get(i).getId() +";");
            while (resultSet.next()){
                offerNos.add(new OfferNo(resultSet.getString("number")));
            }
            customers.get(i).setJobOrderNos(jobOrderNos);
            customers.get(i).setProjectNames(projectNames);
            customers.get(i).setOfferNos(offerNos);
        }
        return customers;
    }

    public static void deleteCustomer(Customer customer) throws SQLException {
        result = statement.executeUpdate("Delete from joborderno where customerid = " + customer.getId() +
                " Delete from projectname where customerid = "+customer.getId()+
                " Delete from offerno where customerid = "+customer.getId()+
                " Delete From customer where id = " + customer.getId());
    }

    public static void editCustomer(Customer newCustomer, Customer oldCustomer) throws SQLException {
        deleteCustomer(oldCustomer);
        addNewCustomer(newCustomer);
    }

    // ====== Equipment Functions =======================

    public static void addNewEquipment(Equipment equipment) throws SQLException {
        result = statement.executeUpdate("INSERT INTO equipment (poleDistance, equipment, " +
                "MPCarrierMedium, magTech, UVLightIntensity, distanceOfLight) " +
                "VALUES  ("+ equipment.getPoleDistance() +", '"+ equipment.getEquipment() +"', " +
                "'"+equipment.getMPCarrierMedium()+"', '"+equipment.getMagTech() +"', " +
                "'"+ equipment.getUVLightIntensity() +"', '"+ equipment.getDistanceOfLight() +"'); ");
    }

    public static void deleteEquipment(Equipment equipment) throws SQLException {
        result = statement.executeUpdate("delete from equipment where id = "+ equipment.getId() +"");
    }

    public static void editEquipment(Equipment newEquipment, Equipment oldEquipment) throws SQLException {
        deleteEquipment(oldEquipment);
        addNewEquipment(newEquipment);
    }

    public static ObservableList<Equipment> getAllEquipments() throws SQLException {
        resultSet = statement.executeQuery("SELECT * from equipment;");
        ObservableList<Equipment> equipments = FXCollections.observableArrayList();
        while (resultSet.next()){
            equipments.add(new Equipment(
                    resultSet.getInt("id"),
                    resultSet.getDouble("poleDistance"),
                    resultSet.getString("equipment"),
                    resultSet.getString("MPCarrierMedium"),
                    resultSet.getString("magTech"),
                    resultSet.getString("UVLightIntensity"),
                    resultSet.getString("distanceOfLight")
            ));
        }
        return equipments;
    }

    // ====== Other Things Functions =======================

    public static void addStageOfExamination(StageOfExamination stageOfExamination) throws SQLException {
        result = statement.executeUpdate("INSERT INTO StageOfExamination (stage) " +
                "VALUES  ('"+ stageOfExamination.getStage() +"');");
    }

    public static void deleteStageOfExamination(StageOfExamination stageOfExamination) throws SQLException {
        result = statement.executeUpdate("delete from StageOfExamination " +
                "where id = "+ stageOfExamination.getId() +"");
    }

    public static ObservableList<StageOfExamination> getAllStageOfExaminations() throws SQLException {
        resultSet = statement.executeQuery("SELECT * from stageOfExamination;");
        ObservableList<StageOfExamination> stageOfExaminations = FXCollections.observableArrayList();
        while (resultSet.next()){
            stageOfExaminations.add(new StageOfExamination(
                    resultSet.getInt("id"),
                    resultSet.getString("stage")
            ));
        }
        return stageOfExaminations;
    }

    public static void addSurfaceCondition(SurfaceCondition surfaceCondition) throws SQLException {
        result = statement.executeUpdate("INSERT INTO SurfaceCondition (condition) " +
                "VALUES  ('"+surfaceCondition.getCondition()+"');");
    }

    public static void deleteSurfaceCondition(SurfaceCondition surfaceCondition) throws SQLException {
        result = statement.executeUpdate("delete from SurfaceCondition " +
                "where id = " + surfaceCondition.getId()+"");
    }

    public static ObservableList<SurfaceCondition> getAllSurfaceConditions() throws SQLException {
        resultSet = statement.executeQuery("select * from SurfaceCondition;");
        ObservableList<SurfaceCondition> SurfaceConditions = FXCollections.observableArrayList();
        while (resultSet.next()){
            SurfaceConditions.add(new SurfaceCondition(
                    resultSet.getInt("id"),
                    resultSet.getString("condition")
            ));
        }
        return SurfaceConditions;
    }
}
