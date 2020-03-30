package sample.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import sample.database.DatabaseHandler;

public class AddEmployeeController {

    @FXML
    private AnchorPane addEmployeePane;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField levelField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField telField;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label levelLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label telLabel;

    @FXML
    void initialize() {

        // ========== Add button clicked ==========

        addButton.setOnAction(event -> {
            restStyle();
            if(!areFieldsEmpty()){
                try {
                    if (areFieldsValid()){
                        classes.User user = new classes.User(
                                usernameField.getText(),
                                passwordField.getText(),
                                nameField.getText(),
                                surnameField.getText(),
                                Integer.parseInt(levelField.getText()),
                                emailField.getText(),
                                telField.getText()
                        );
                        addNewUser(user);
                        showSuccessAlert();
                        addEmployeePane.getScene().getWindow().hide();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                // do something
            }
        });

        // =========== Cancel button clicked ==========

        cancelButton.setOnAction(event -> {
            levelField.getScene().getWindow().hide();
        });
    }

    // ============ Helper functions ===============

    private boolean areFieldsEmpty(){
        boolean emptiness = false;
        if(usernameField.getText().equals("")) {
            usernameIsEmpty(); emptiness = true;}
        if(passwordField.getText().equals("")) {
            passwordIsEmpty(); emptiness = true;}
        if(nameField.getText().equals("")) {
            nameIsEmpty(); emptiness = true;}
        if(surnameField.getText().equals("")) {
            surnameIsEmpty(); emptiness = true;}
        if(levelField.getText().equals("")) {
            levelIsEmpty(); emptiness = true;}
        if(emailField.getText().equals("")){
            emailIsEmpty(); emptiness = true;
        }
        if(telField.getText().equals("")){
            telIsEmpty(); emptiness = true;
        }
        return emptiness;
    }

    private boolean areFieldsValid() throws SQLException {
        boolean validation = true;
        if(isUsernameTaken(usernameField.getText())){
            validation = false;
            usernameNotValid();
        }
        if(!isPasswordValid(passwordField.getText())){
            validation = false;
            passwordNotValid();
        }
        if(!isStringText(nameField.getText())){
            validation = false;
            nameNotValid();
        }
        if(!isStringText(surnameField.getText())){
            validation = false;
            surnameNotValid();
        }
        if(!isLevelValid(Integer.parseInt(levelField.getText()))){
            validation = false;
            levelNotValid();
        }
        if(!isStringTel(telField.getText())){
            validation = false;
            telNotValid();
        }
        if(!isEmailValid(emailField.getText())){
            validation = false;
            emailNotValid();
        }
        return validation;
    }

    private void addNewUser(classes.User user) throws SQLException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        databaseHandler.addNewUser(user);
    }

    private void showSuccessAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User Addition");
        alert.setHeaderText(null);
        alert.setContentText("You have added a new user correctly");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(addEmployeePane.getScene().getWindow());
        alert.showAndWait();
    }

    // =========== Validation checking function =========

    private boolean isUsernameTaken(String username) throws SQLException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        return databaseHandler.isUsernameTaken(username);
    }

    private boolean isPasswordValid(String password){
        if (password.length() < 8 || password.length() > 50) return false;
        return true;
    }
    // this used for the name and surname
    private boolean isStringText(String str) {
        if (str.length() > 50) return false;
        str = str.toLowerCase();
        char[] charArray = str.toCharArray();
        for (char ch : charArray) {
            if (!(ch >= 'a' && ch <= 'z')) {
                return false;
            }
        }
        return true;
    }

    private boolean isLevelValid(int level){
        if (level > 3 || level < 1) return false;
        return true;
    }

    private boolean isStringTel(String str) {
        if(str.length() != 11) return false;
        char[] charArray = str.toCharArray();
        for (char ch : charArray) {
            if (!(ch >= '0' && ch <= '9')) {
                return false;
            }
        }
        return true;
    }

    // not my implementation i don't completely understand it
    private boolean isEmailValid(String email){
        // compiling the regular expression to be a pattern (but what is a pattern?)
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");

        // what is here is not quite clear
        Matcher m = p.matcher(email);
        if(m.find() && m.group().equals(email)){
            return true;
        }
        return false;
    }

    // ============ Printing the empty stuff ===============

    private void usernameIsEmpty(){
        usernameLabel.setText("Please enter a username");
        usernameField.setStyle("-fx-border-color: red;");
    }

    private void passwordIsEmpty(){
        passwordLabel.setText("Please enter a password");
        passwordField.setStyle("-fx-border-color: red;");
    }

    private void nameIsEmpty(){
        nameLabel.setText("Please enter a name");
        nameField.setStyle("-fx-border-color: red;");
    }

    private void surnameIsEmpty(){
        surnameLabel.setText("Please enter a surname");
        surnameField.setStyle("-fx-border-color: red;");
    }

    private void levelIsEmpty(){
        levelLabel.setText("Please enter a level");
        levelField.setStyle("-fx-border-color: red;");
    }

    private void emailIsEmpty(){
        emailLabel.setText("Please enter an email");
        emailField.setStyle("-fx-border-color: red;");
    }

    private void telIsEmpty(){
        telLabel.setText("Enter a telephone number");
        telField.setStyle("-fx-border-color: red;");
    }

    // ============ Printing the non valid stuff ============

    private void usernameNotValid(){
        usernameLabel.setText("Username is taken");
        usernameField.setStyle("-fx-border-color: red;");;
    }

    private void passwordNotValid(){
        passwordLabel.setText("Please enter a too short");
        passwordField.setStyle("-fx-border-color: red;");
    }

    private void nameNotValid(){
        nameLabel.setText("Please enter a valid name");
        nameField.setStyle("-fx-border-color: red;");
    }

    private void surnameNotValid(){
        surnameLabel.setText("Please enter a valid surname");
        surnameField.setStyle("-fx-border-color: red;");
    }

    private void levelNotValid(){
        levelLabel.setText("Enter number between 1 and 3");
        levelField.setStyle("-fx-border-color: red;");
    }

    private void telNotValid(){
        telLabel.setText("Enter valid telephone number");
        telField.setStyle("-fx-border-color: red;");
    }

    private void emailNotValid(){
        emailLabel.setText("Please enter a valid email");
        emailField.setStyle("-fx-border-color: red;");
    }

    // ========== reset labels and colors of the fields ==========

    private void restStyle(){
        usernameLabel.setText("");
        usernameField.setStyle(null);
        passwordLabel.setText("");
        passwordField.setStyle(null);
        nameLabel.setText("");
        nameField.setStyle(null);
        surnameLabel.setText("");
        surnameField.setStyle(null);
        levelLabel.setText("");
        levelField.setStyle(null);
        telLabel.setText("");
        telField.setStyle(null);
        emailLabel.setText("");
        emailField.setStyle(null);
    }
}

