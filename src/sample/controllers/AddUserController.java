package sample.controllers;

import java.sql.SQLException;

import sample.config.Config;
import sample.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import sample.database.DatabaseHandler;

public class AddUserController {

    // 1 Add , 2 sign up, 3 edit
    private int mode = Config.ADDMODE;

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
    private Tooltip levelTooltip;

    @FXML
    private Label levelLabelName;

    @FXML
    private Label levelTooltipHelper;

    @FXML
    void initialize() {

        levelTooltipHelper.setDisable(true);
        // ========== Add button clicked ==========

        addButton.setOnAction(event -> {
            restStyle();
            if(!areFieldsEmpty()){
                try {
                    if (areFieldsValid()){
                        User user = new User(
                                usernameField.getText(),
                                passwordField.getText(),
                                nameField.getText(),
                                surnameField.getText(),
                                Integer.parseInt(levelField.getText())
                        );
                        addNewUser(user);
                        if(mode == Config.SIGNUPMODE){ // sign up
                            showSuccessAlert("You have signed up correctly");
                        }else if(mode == Config.ADDMODE) { // add
                            showSuccessAlert("You have added a new Employee correctly");
                        }
                        addEmployeePane.getScene().getWindow().hide();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        // =========== Cancel button clicked ==========

        cancelButton.setOnAction(event -> {
            levelField.getScene().getWindow().hide();
        });
    }

    // ============ Helper functions ===============

    private void addNewUser(User user) throws SQLException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        databaseHandler.addNewUser(user);
    }

    public void hideLevel(){
        levelTooltip.setText("New users can't have level higher than 1");
        levelTooltipHelper.setDisable(false);
        levelField.setText("1");
        levelField.setDisable(true);
        levelLabel.setDisable(true);
        levelLabelName.setDisable(true);
    }

    // ========== Alerts ============================

    private void showSuccessAlert(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User Addition");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(addEmployeePane.getScene().getWindow());
        alert.showAndWait();
    }

    // =========== Validation checking functions =========

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
        return validation;
    }

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

    // ===

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

    // ============ Emptiness checking functions ===============
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
        return emptiness;
    }

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
    }

    // ========== setter and getter ===========

    public int  getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}

