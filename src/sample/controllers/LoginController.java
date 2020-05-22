package sample.controllers;

import javafx.event.ActionEvent;
import sample.handlers.DatabaseHandler;
import sample.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private AnchorPane loginPane;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label notFoundMesg;

    @FXML
    private Label usernameMesg;

    @FXML
    private Label passwordMesg;

    @FXML
    void initialize() {
    }

    // ====== on Action ==============================

    @FXML
    private void LoginButtonOnAction(ActionEvent event) throws IOException, SQLException {
        resetStyle();
        if(!areFieldsEmpty()){
            User user = DatabaseHandler.getUser(username.getText(), password.getText());
            if(user != null){
                showMain(user);
            }else{
                userNotFoundMesg();
            }
        }
    }

    // ====== Helper Functions =======================

    private  void showMain(User user) throws IOException {
        loginPane.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/main.fxml"));
        Parent root = fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();
        // showing settings if the user is an admin
        if(user.getLevel() >= User.LEVEL3)
            mainController.showSettingsButton();
        mainController.setUser(user);
        mainController.setHelloLabel();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Ana Ekran");
        stage.show();
    }

    // ====== Error Messages handler =================

    private boolean areFieldsEmpty(){
        boolean emptiness = false;
        if (username.getText().equals("")){
            emptiness = true;
            usernameErrorMesg();
        }
        if (password.getText().equals("")){
            emptiness = true;
            passwordErrorMesg();
        }
        return emptiness;
    }

    private  void usernameErrorMesg(){
        usernameMesg.setText("Lütfen geçerli bir kullanıcı adı girin!");
        username.setStyle("-fx-border-color: red;");
    }

    private  void passwordErrorMesg(){
        passwordMesg.setText("Lütfen geçerli bir şifre giriniz!");
        password.setStyle("-fx-border-color: red;");
    }

    private  void userNotFoundMesg(){
        notFoundMesg.setText("Kullanıcı adı veya şifre doğru değil");
    }

    // ======= Setters and Getters ===================

    private  void resetStyle(){
        usernameMesg.setText("");
        passwordMesg.setText("");
        notFoundMesg.setText("");
        username.setStyle(null);
        password.setStyle(null);
    }

}
