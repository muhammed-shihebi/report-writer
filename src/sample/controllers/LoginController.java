package sample.controllers;

import javafx.event.ActionEvent;
import sample.database.DatabaseHandler;
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


    // =========== on Action ==================

    @FXML
    private void LoginButtonOnAction(ActionEvent event) throws IOException, SQLException {
        resetStyle();
        if(!areFieldsEmpty()){
            User user = DatabaseHandler.getUser(username.getText().toLowerCase(), password.getText());
            if(user != null){
                showMain(user);
            }else{
                userNotFound();
            }
        }
    }

    // =========== helper functions ===========

    private  void showMain(User user) throws IOException {
        // hiding the login window
        loginPane.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/view/main.fxml"));
        // after loading the fxml file the initialize() method in MainController is called
        fxmlLoader.load();
        // fxmlLoader.getController has the very same object (MainController of the fxml file that we loaded)
        // so from here we could manipulate the objects (Nodes) in that widow
        MainController mainController = fxmlLoader.getController();
        // if the user has level lower then 3 the setting Button will be disabled
        if(user.getLevel() < User.LEVEL3)
            mainController.hideSettings();
        // we also passing the user to be used later on the setting and other things
        mainController.setUser(user);
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Main");
        stage.show();
    }

    private boolean areFieldsEmpty(){
        boolean emptiness = false;
        if (username.getText().toLowerCase().equals("")){
            emptiness = true;
            usernameError();
        }
        if (password.getText().equals("")){
            emptiness = true;
            passwordError();
        }
        return emptiness;
    }

    // =========== Error handler ==============

    private  void usernameError(){
        usernameMesg.setText("Lütfen geçerli bir kullanıcı adı girin!");
        username.setStyle("-fx-border-color: red;");
        System.out.println("No Username entered");
    }

    private  void passwordError(){
        passwordMesg.setText("Lütfen geçerli bir şifre giriniz!");
        password.setStyle("-fx-border-color: red;");
        System.out.println("No Password entered");
    }

    private  void userNotFound(){
        notFoundMesg.setText("Kullanıcı adı veya şifre doğru değil");
        System.out.println("username not found");
    }

    // =========== reset =======================

    private  void resetStyle(){
        usernameMesg.setText("");
        passwordMesg.setText("");
        notFoundMesg.setText("");
        username.setStyle(null);
        password.setStyle(null);
    }

}
