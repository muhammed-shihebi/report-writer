package sample.controllers;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.Modality;
import sample.Main;
import sample.database.DatabaseHandler;
import sample.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class UserHandlerController {

    private static final String ERORRTEXTFILESTYLE = "-fx-border-color: red;";

    public static final int ADDMODE = 1;
    public static final int EDITMODE = 2;
    private int mode = ADDMODE;

    private User selectedUser;

    @FXML
    private Button addButton;

    @FXML
    private Label passwordLabel;

    @FXML
    private AnchorPane addUserPane;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private Label usernameMesg;

    @FXML
    private Label passwordMesg;

    @FXML
    private Label nameMesg;

    @FXML
    private Label surnameMesg;

    @FXML
    private Label levelMesg;

    @FXML
    private Label passwordStern;

    @FXML
    private ComboBox<Integer> levelComboBox;

    @FXML
    public void initialize() {
        initLevelComboBox();
    }


    // ============ On Action ======================

    @FXML
    private void addButtonOnAction(ActionEvent event) throws SQLException {
        restStyle();
        // User user = new User();
        // areFieldsValid(user);
        if(!areFieldsEmpty() && areFieldsValid()){
            User newUser = new User(
                    usernameField.getText().toLowerCase(),
                    passwordField.getText(),
                    nameField.getText(),
                    surnameField.getText(),
                    levelComboBox.getValue()
            );
            if (mode == ADDMODE){
                addNewUser(newUser);
            }else {
                editUser(selectedUser, newUser);
            }
            addUserPane.getScene().getWindow().hide();
        }
    }

    @FXML
    private void cancelButtonOnAction(ActionEvent event) {
        addUserPane.getScene().getWindow().hide();
    }

    // ============ Helper functions ===============

    private void addNewUser(User user) throws SQLException {
        DatabaseHandler.addNewUser(user);
    }

    private void initLevelComboBox(){
        ObservableList<Integer> listOfLevels = FXCollections.observableArrayList(User.LEVEL1, User.LEVEL2, User.LEVEL3);
        levelComboBox.setItems(listOfLevels);
    }

    private void editUser(User oldUser, User newUser) throws SQLException {
        boolean isUserUpdated = DatabaseHandler.editUser(oldUser, newUser);
        if(!isUserUpdated){
            showFailingAlert();
        }else {
            addUserPane.getScene().getWindow().hide();
        }
    }

    public void fillFields(){
        usernameField.setText(selectedUser.getUsername());
        nameField.setText(selectedUser.getName());
        surnameField.setText(selectedUser.getSurname());
        levelComboBox.setValue(selectedUser.getLevel());
    }

    public void setEditMode(){
        passwordStern.setVisible(false); // password is not mandatory
        addButton.setText("Düzenle");
        passwordLabel.setText("Yeni Şifre");
        passwordMesg.setStyle("-fx-color: black");
        passwordMesg.setText("Bu alan zorunlu değil");
    }

    // ========== Alerts ===================

    private void showFailingAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Çalışan bilgilerini düzenleme");
        alert.setHeaderText(null);
        alert.setContentText("Sistemdeki tek 3 seviyeli personel seviyesini azaltamazsınız.");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(addUserPane.getScene().getWindow());
        alert.showAndWait();
        levelComboBox.setValue(selectedUser.getLevel());
    }

    // =========== Validation checking functions =========

    private boolean areFieldsValid() throws SQLException {
        // user
        boolean validation = true;

        if(mode == EDITMODE && (usernameField.getText().toLowerCase()
                .equals(selectedUser.getUsername().toLowerCase()))){ // do nothing the username is valid
        }else{ // edit mode and usernames are different or not edit mode
            if (DatabaseHandler.isUsernameTaken(usernameField.getText().toLowerCase())) {
                validation = false;
                setUsernameNotValid("mevcut");
            }
        }
        if (!User.isUsernameValid(usernameField.getText().toLowerCase())){
            validation = false;
            setUsernameNotValid("geçerli değil");
        }

        if(mode == EDITMODE && passwordField.getText().equals("")){
            // do nothing the old password will stay
        }else{ // edit mode and password is new or not edit mode
            if(!User.isPasswordValid(passwordField.getText())){
                validation = false;
                setPasswordNotValid();
            }
        }
        if(!User.isNameValid(nameField.getText())){
            validation = false;
            setNameNotValid();
        }
        if(!User.isNameValid(surnameField.getText())){
            validation = false;
            setSurnameNotValid();
        }
        return validation;
    }

    private void setUsernameNotValid(String str){
        usernameMesg.setText("Kullanıcı adı "+ str);
        usernameField.setStyle(ERORRTEXTFILESTYLE);;
    }

    private void setPasswordNotValid(){
        passwordMesg.setText("Girilen şifre çok kısa.");
        passwordField.setStyle(ERORRTEXTFILESTYLE);
    }

    private void setNameNotValid(){
        nameMesg.setText("Lütfen geçerli bir isim girin.");
        nameField.setStyle(ERORRTEXTFILESTYLE);
    }

    private void setSurnameNotValid(){
        surnameMesg.setText("Lütfen geçerli bir soyadı girin");
        surnameField.setStyle(ERORRTEXTFILESTYLE);
    }

    // ============ Emptiness checking functions ===============

    private boolean areFieldsEmpty(){
        boolean emptiness = false;
        if(usernameField.getText().equals("")) {
            setUsernameEmpty(); emptiness = true;}

        if(mode == EDITMODE && passwordField.getText().equals("")){
            // do nothing, the old password will be stored
        }else{ // new password with edit mode or add mode
            if(passwordField.getText().equals("")) {
                setPasswordEmpty(); emptiness = true;}
        }
        if(nameField.getText().equals("")) {
            setNameEmpty(); emptiness = true;}
        if(surnameField.getText().equals("")) {
            setSurnameEmpty(); emptiness = true;}
        if(levelComboBox.getValue() == null) {
            setLevelEmpty(); emptiness = true;}
        return emptiness;
    }

    private void setUsernameEmpty(){
        usernameMesg.setText("Lütfen bir kullanıcı adı giriniz");
        usernameField.setStyle(ERORRTEXTFILESTYLE);
    }

    private void setPasswordEmpty(){
        passwordMesg.setText("Lütfen bir şifre girin");
        passwordField.setStyle(ERORRTEXTFILESTYLE);
    }

    private void setNameEmpty(){
        nameMesg.setText("Lütfen bir ad girin");
        nameField.setStyle(ERORRTEXTFILESTYLE);
    }

    private void setSurnameEmpty(){
        surnameMesg.setText("Lütfen bir soyadı girin");
        surnameField.setStyle(ERORRTEXTFILESTYLE);
    }

    private void setLevelEmpty(){
        levelMesg.setText("Lütfen bir seviye seçin");
        levelComboBox.setStyle(ERORRTEXTFILESTYLE);
    }

    // ========== reset labels and colors of the fields ==========

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

    // =========== Setter and Getter ==============================

    public void setMode(int mode) {
        this.mode = mode;
    }
    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
}

