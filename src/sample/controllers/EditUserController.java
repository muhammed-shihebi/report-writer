package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import sample.config.Config;
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
    private ComboBox<Integer> levelComboBox;

    @FXML
    void initialize() {

        // ============= Give info to the level compo box=============

        initLevelComboBox();

        // ====== Buttons ===========
        editButton.setOnAction(event ->     {
            restStyle();
            if(!areFieldsEmpty()){
                try {
                    if (areFieldsValid()){
                        if(editPassCheckBox.isSelected()){
                            User newUser = new User(
                                    usernameField.getText().toLowerCase(),
                                    passwordField.getText(),
                                    nameField.getText(),
                                    surnameField.getText(),
                                    levelComboBox.getValue()
                            );
                            editUser(selectedUser, newUser);
                        }else{
                            User newUser = new User(
                                    usernameField.getText().toLowerCase(),
                                    // the old password will be copied in the database handler
                                    "",
                                    nameField.getText(),
                                    surnameField.getText(),
                                    levelComboBox.getValue()
                            );
                            editUser(selectedUser, newUser);
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
        boolean isUserUpdated = databaseHandler.editUser(oldUser, newUser);
        if(!isUserUpdated){
            showFailingAlert();
        }else {
            showSuccessAlert();
            EditUserPane.getScene().getWindow().hide();
        }
    }

    public void fillFields(){
        usernameField.setText(selectedUser.getUsername());
        nameField.setText(selectedUser.getName());
        surnameField.setText(selectedUser.getSurname());
        levelComboBox.setValue(selectedUser.getLevel());
    }

    public void initLevelComboBox(){
        ObservableList<Integer> listOfLevels = FXCollections.observableArrayList(Config.Level1, Config.Level2, Config.Level3);
        levelComboBox.setItems(listOfLevels);
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

    private void showFailingAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Edit employee information");
        alert.setHeaderText(null);
        alert.setContentText("You can't reduce the level of the only Admin in the system.");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(EditUserPane.getScene().getWindow());
        alert.showAndWait();
        levelComboBox.setValue(selectedUser.getLevel());
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
        if(levelComboBox.getValue() == null) {
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
        levelComboBox.setStyle("-fx-border-color: red;");
    }

    // ========== Validation Checking =======

    private boolean areFieldsValid() throws SQLException {
        boolean validation = true;

        if(isUsernameTaken(usernameField.getText().toLowerCase())){
            validation = false;
            usernameNotValidMesg("taken");
        }
        if (!isUsernameValid(usernameField.getText().toLowerCase())){
            validation = false;
            usernameNotValidMesg("too long");
        }
        if(editPassCheckBox.isSelected()){
            if(!isPasswordValid(passwordField.getText())){
                validation = false;
                passwordNotValidMesg();
            }
        }
        if(!isNameValid(nameField.getText())){
            validation = false;
            nameNotValidMesg();
        }
        if(!isNameValid(surnameField.getText())){
            validation = false;
            surnameNotValidMesg();
        }
        if(!isLevelValid((levelComboBox.getValue()))){
            validation = false;
            levelNotValidMesg();
        }
        return validation;
    }

    private boolean isUsernameTaken(String newUsername) throws SQLException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        return databaseHandler.isUsernameTaken(newUsername, selectedUser.getUsername());
    }

    private boolean isUsernameValid(String username){
        if(username.length() >= Config.MAXLENGTH || !username.matches("[a-z0-9]+")){
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(String password){
        if (password.length() < 8 || password.length() > 50) return false;
        return true;
    }
    // this used for the name and surname
    private boolean isNameValid(String str) {
        if (str.length() > Config.MAXLENGTH) return false;
        return str.matches("\\p{L}+");
    }

    private boolean isLevelValid(int level){
        if (level > Config.Level3 || level < Config.Level1) return false;
        return true;
    }

    // ========== Validation Messeges ==========

    private void usernameNotValidMesg(String str){
        usernameMesg.setText("Username is " + str);
        usernameField.setStyle("-fx-border-color: red;");
    }

    private void passwordNotValidMesg(){
        passwordMesg.setText("Please enter a too short");
        passwordField.setStyle("-fx-border-color: red;");
    }

    private void nameNotValidMesg(){
        nameMesg.setText("Please enter a valid name");
        nameField.setStyle("-fx-border-color: red;");
    }

    private void surnameNotValidMesg(){
        surnameMesg.setText("Please enter a valid surname");
        surnameField.setStyle("-fx-border-color: red;");
    }

    private void levelNotValidMesg(){
        levelMesg.setText("Enter number between 1 and 3");
        levelComboBox.setStyle("-fx-border-color: red;");
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
        levelComboBox.setStyle(null);
    }

    // ========== Setter and getter =========

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
}
