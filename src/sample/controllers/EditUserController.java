package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import sample.database.DatabaseHandler;
import sample.model.User;

import java.sql.SQLException;

public class EditUserController {

    private User selectedUser;

    @FXML
    private AnchorPane EditUserPane;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private Label levelLabelName;

    @FXML
    private TextField levelField;

    @FXML
    private Button editButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label usernameMesg;

    @FXML
    private Label nameMesg;

    @FXML
    private Label surnameMesg;

    @FXML
    private Label levelMesg;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordMesg;

    @FXML
    private CheckBox editPassCheckBox;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label passwordStern;

    @FXML
    void initialize() {

        // ======= fill out fields =====

        usernameField.setText(selectedUser.getUsername());
        nameField.setText(selectedUser.getName());
        surnameField.setText(selectedUser.getSurname());
        levelField.setText(("" + selectedUser.getLevel()));

        // ====== Buttons ===========
        editButton.setOnAction(event ->     {
            restStyle();
            if(!areFieldsEmpty()){
                try {
                    if (areFieldsValid()){
                        if(editPassCheckBox.isSelected()){
                            User newUser = new User(
                                    usernameField.getText(),
                                    passwordField.getText(),
                                    nameField.getText(),
                                    surnameField.getText(),
                                    Integer.parseInt(levelField.getText())
                            );
                            editUser(selectedUser, newUser);
                            showSuccessAlert();
                            EditUserPane.getScene().getWindow().hide();
                        }else{
                            User newUser = new User(
                                    usernameField.getText(),
                                    // the old password will be copied in the database handler
                                    "",
                                    nameField.getText(),
                                    surnameField.getText(),
                                    Integer.parseInt(levelField.getText())
                            );
                            editUser(selectedUser, newUser);
                            showSuccessAlert();
                            EditUserPane.getScene().getWindow().hide();
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        cancelButton.setOnAction(event -> {
            EditUserPane.getScene().getWindow().hide();
        });

        // ====== Edit Password Check Box ======

        editPassCheckBox.setOnAction(event -> {
            if(editPassCheckBox.isSelected()){
                passwordField.setDisable(false);
                passwordMesg.setDisable(false);
                passwordLabel.setDisable(false);
                passwordStern.setDisable(false);
            }else {
                passwordField.setDisable(true);
                passwordMesg.setDisable(true);
                passwordLabel.setDisable(true);
                passwordStern.setDisable(true);
            }
        });

    }

    // ========== Helper functions  ===============

    private void editUser(User oldUser, User newUser) throws SQLException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        databaseHandler.editUser(oldUser, newUser);
    }

    // ========== Alerts ===================

    private void showSuccessAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Edit employee information");
        alert.setHeaderText(null);
        alert.setContentText("You have edited the employee Information correctly. ");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(EditUserPane.getScene().getWindow());
        alert.showAndWait();
    }

    // ========== Emptiness Checking ========

    private boolean areFieldsEmpty(){
        boolean emptiness = false;
        if(usernameField.getText().equals("")) {
            usernameIsEmpty(); emptiness = true;}

        // password could be empty if the Edit password Check bok is not selected

        if(editPassCheckBox.isSelected()){
            if(passwordField.getText().equals("")) {
                passwordIsEmpty(); emptiness = true;}
        }
        if(nameField.getText().equals("")) {
            nameIsEmpty(); emptiness = true;}
        if(surnameField.getText().equals("")) {
            surnameIsEmpty(); emptiness = true;}
        if(levelField.getText().equals("")) {
            levelIsEmpty(); emptiness = true;}
        return emptiness;
    }

    private void usernameIsEmpty(){
        usernameMesg.setText("Please enter a username");
        usernameField.setStyle("-fx-border-color: red;");
    }

    private void passwordIsEmpty(){
        passwordMesg.setText("Please enter a password");
        passwordField.setStyle("-fx-border-color: red;");
    }

    private void nameIsEmpty(){
        nameMesg.setText("Please enter a name");
        nameField.setStyle("-fx-border-color: red;");
    }

    private void surnameIsEmpty(){
        surnameMesg.setText("Please enter a surname");
        surnameField.setStyle("-fx-border-color: red;");
    }

    private void levelIsEmpty(){
        levelMesg.setText("Please enter a level");
        levelField.setStyle("-fx-border-color: red;");
    }

    // ========== Validation Checking =======

    private boolean areFieldsValid() throws SQLException {
        boolean validation = true;

        if(editPassCheckBox.isSelected()){
            if(!isPasswordValid(passwordField.getText())){
                validation = false;
                passwordNotValid();
            }
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
        return validation;
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

    // ===

    private void passwordNotValid(){
        passwordMesg.setText("Please enter a too short");
        passwordField.setStyle("-fx-border-color: red;");
    }

    private void nameNotValid(){
        nameMesg.setText("Please enter a valid name");
        nameField.setStyle("-fx-border-color: red;");
    }

    private void surnameNotValid(){
        surnameMesg.setText("Please enter a valid surname");
        surnameField.setStyle("-fx-border-color: red;");
    }

    private void levelNotValid(){
        levelMesg.setText("Enter number between 1 and 3");
        levelField.setStyle("-fx-border-color: red;");
    }

    // ========== reset things ==============

    private void restStyle(){
        usernameMesg.setText("");
        usernameField.setStyle(null);
        passwordMesg.setText("");
        passwordField.setStyle(null);
        nameMesg.setText("");
        nameField.setStyle(null);
        surnameMesg.setText("");
        surnameField.setStyle(null);
        levelMesg.setText("");
        levelField.setStyle(null);
    }

    // ========== Setter and getter =========

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
}
