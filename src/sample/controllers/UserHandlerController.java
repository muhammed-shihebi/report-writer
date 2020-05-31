package sample.controllers;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import sample.handlers.DatabaseHandler;
import sample.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class UserHandlerController {

    private static final String ERORRTEXTFIELDSTYLE = "-fx-border-color: red;";
    public static final int ADDMODE = 1;
    public static final int EDITMODE = 2;
    private int mode;
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


    // ====== On Action ==============================

    @FXML
    private void addButtonOnAction(ActionEvent event) throws SQLException {
        resetStyle();
        // all text fields must not be empty if the mode = ADDMODE
        // all text fields must not be empty except password field in the mode = EDITMODE
        if(!areFieldsEmpty() && areFieldsValid()){
            User newUser = new User(
                    usernameField.getText(),
                    passwordField.getText(),
                    nameField.getText(),
                    surnameField.getText(),
                    levelComboBox.getValue()
            );
            if (mode == ADDMODE){
                DatabaseHandler.addNewUser(newUser);
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

    @FXML
    void nameFieldOnKey(KeyEvent event) {
        nameField.setStyle(null);
        nameMesg.setVisible(false);
        String str = nameField.getText();
        if(str.equals("")) {
            setNameEmpty();
        }else if(!User.isNameValid(str)){
            setNameNotValid();
        }
    }

    @FXML
    void passwordFieldOnKey(KeyEvent event) {
        passwordField.setStyle(null);
        passwordMesg.setVisible(false);
        String str = passwordField.getText();

        if(str.equals("")){
            if(mode != EDITMODE){
                setPasswordEmpty();
            }
        }else if(!User.isPasswordValid(str)){
                setPasswordNotValid();

        }
    }

    @FXML
    void surnameFieldOnKey(KeyEvent event) {
        surnameField.setStyle(null);
        surnameMesg.setVisible(false);
        String str = surnameField.getText();
        if(str.equals("")) {
            setSurnameEmpty();
        }else if(!User.isNameValid(str)){
            setSurnameNotValid();
        }
    }

    @FXML
    void usernameFieldOnKey(KeyEvent event) throws SQLException{
        usernameField.setStyle(null);
        usernameMesg.setVisible(false);
        String str = usernameField.getText();

        if(str.equals("")) {
            setUsernameEmpty();
        }else {
            if(DatabaseHandler.isUsernameTaken(str)){
                // if EDITMODE is set and the user did not change the username than this username
                // should be valid
                if (mode != EDITMODE || !selectedUser.getUsername().equals(str)) {
                    setUsernameNotValid("mevcut");
                }
            }
            if (!User.isUsernameValid(str)){
                setUsernameNotValid("geçerli değil");
            }
        }
    }
















    // ====== Helper functions =======================

    private void initLevelComboBox(){
        ObservableList<Integer> listOfLevels = FXCollections.observableArrayList(User.LEVEL1, User.LEVEL2, User.LEVEL3);
        levelComboBox.setItems(listOfLevels);
    }

    private void editUser(User oldUser, User newUser) throws SQLException {
        boolean isUserUpdated = DatabaseHandler.editUser(oldUser, newUser);
        if(!isUserUpdated){
            // failing to update the user means we are trying to reduce the level of the only admin in the
            // system which is not allowed
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
        addButton.setText("kaydet");
        passwordLabel.setText("Yeni Şifre");
        passwordMesg.setStyle("-fx-color: black");
        passwordMesg.setText("Bu alan zorunlu değil");
        setMode(EDITMODE);
        fillFields();
    }

    // ====== Alerts =================================

    private void showFailingAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Çalışan bilgilerini düzenleme");
        alert.setHeaderText(null);
        alert.setContentText("Sistemdeki tek 3 seviyeli personel seviyesini azaltamazsınız.");
        ButtonType buttonYes = new ButtonType("Tamam");
        alert.getButtonTypes().setAll(buttonYes);
        alert.showAndWait();
        // make the value of the level comboBox go back to the original to reinforce the
        // logic of the wrongness of deleting the only admin in the system
        levelComboBox.setValue(selectedUser.getLevel());
    }

    // ====== Error Messages Handler =================











        // ====== Validation checking functions ======

    private boolean areFieldsValid() throws SQLException {
        boolean validation = true;
        if(DatabaseHandler.isUsernameTaken(usernameField.getText())){
            // if EDITMODE is set and the user did not change the username than this username
            // should be valid
            if (mode != EDITMODE || !selectedUser.getUsername().equals(usernameField.getText())) {
                validation = false;
                setUsernameNotValid("mevcut");
            }
        }
        if (!User.isUsernameValid(usernameField.getText())){
            validation = false;
            setUsernameNotValid("geçerli değil");
        }
        if(!User.isPasswordValid(passwordField.getText())){
            // if EDITMODE is set and the password is empty then the old password will be used
            // and the condition is valid
            if(mode != EDITMODE || !passwordField.getText().equals("")) {
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

        // ====== Validation error Messages setters ==

    private void setUsernameNotValid(String str){
        usernameMesg.setText("Kullanıcı adı "+ str);
        usernameField.setStyle(ERORRTEXTFIELDSTYLE);
        usernameMesg.setVisible(true);
    }

    private void setPasswordNotValid(){
        passwordMesg.setText("Girilen şifre çok kısa.");
        passwordField.setStyle(ERORRTEXTFIELDSTYLE);
        passwordMesg.setVisible(true);
    }

    private void setNameNotValid(){
        nameMesg.setText("Lütfen geçerli bir isim girin.");
        nameField.setStyle(ERORRTEXTFIELDSTYLE);
        nameMesg.setVisible(true);
    }

    private void setSurnameNotValid(){
        surnameMesg.setText("Lütfen geçerli bir soyadı girin");
        surnameField.setStyle(ERORRTEXTFIELDSTYLE);
        surnameMesg.setVisible(true);
    }














        // ====== Emptiness checking functions =======

    private boolean areFieldsEmpty(){
        boolean emptiness = false;

        if(usernameField.getText().equals("")) {
            setUsernameEmpty(); emptiness = true;}

        if(passwordField.getText().equals("")){
            if(mode != EDITMODE){
                setPasswordEmpty(); emptiness = true;
            }
        }

        if(nameField.getText().equals("")) {
            setNameEmpty(); emptiness = true;
        }

        if(surnameField.getText().equals("")) {
            setSurnameEmpty(); emptiness = true;
        }

        if(levelComboBox.getValue() == null) {
            setLevelEmpty(); emptiness = true;
        }

        return emptiness;
    }

        // ====== Emptiness error Messages setters ===

    private void setUsernameEmpty(){
        usernameMesg.setText("Lütfen bir kullanıcı adı giriniz");
        usernameField.setStyle(ERORRTEXTFIELDSTYLE);
        usernameMesg.setVisible(true);
    }

    private void setPasswordEmpty(){
        passwordMesg.setText("Lütfen bir şifre girin");
        passwordField.setStyle(ERORRTEXTFIELDSTYLE);
        passwordMesg.setVisible(true);
    }

    private void setNameEmpty(){
        nameMesg.setText("Lütfen bir ad girin");
        nameField.setStyle(ERORRTEXTFIELDSTYLE);
        nameMesg.setVisible(true);
    }

    private void setSurnameEmpty(){
        surnameMesg.setText("Lütfen bir soyadı girin");
        surnameField.setStyle(ERORRTEXTFIELDSTYLE);
        surnameMesg.setVisible(true);
    }

    private void setLevelEmpty(){
        levelMesg.setText("Lütfen bir seviye seçin");
        levelComboBox.setStyle(ERORRTEXTFIELDSTYLE);
    }










        // ====== Setters and Getters ================

    private void resetStyle(){
        usernameMesg.setText("");
        usernameField.setStyle(null);
        if (mode != EDITMODE || !passwordField.getText().equals("")){
            passwordMesg.setText("");
            passwordField.setStyle(null);
        }
        nameMesg.setText("");
        nameField.setStyle(null);
        surnameMesg.setText("");
        surnameField.setStyle(null);
        levelMesg.setText("");
        levelComboBox.setStyle(null);
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
}

