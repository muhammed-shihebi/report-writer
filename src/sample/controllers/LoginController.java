package sample.controllers;

import javafx.stage.Modality;
import sample.config.Config;
import sample.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;


import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private AnchorPane LoginPane;

    @FXML
    private TextField LoginUsername;

    @FXML
    private PasswordField LoginPassword;

    @FXML
    private Label LoginNotFoundLabel;

    @FXML
    private Button LoginLoginButton;

    @FXML
    private Label UsernameErrorMessage;

    @FXML
    private Label PasswordErrorMassage;

    @FXML
    private Button loginSignUpButton;

    @FXML
    void initialize() {
        // =========== LoginButton clicked ====================
        LoginLoginButton.setOnAction(event -> {

            // reset the style to correspond to the new changes
            resetStyle();
            String username = LoginUsername.getText();
            String password = LoginPassword.getText();
            if(username.equals("") && password.equals("")){
                passwordError();
                usernameError();
            }else if (username.equals("")){
                usernameError();
            }else if (password.equals("")){
                passwordError();
            }else{
                DatabaseHandler databaseHandler = new DatabaseHandler();
                try {
                    User user = databaseHandler.getUser(username, password);
                    // there is a user with this user name and password
                    if(user != null){
                        showMain(user);
                    }else{
                        userNotFound();
                    }
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // =========== SignUpButton clicked ===================
        loginSignUpButton.setOnAction(event -> {
            try {
                showAddEmployee();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }



    // =========== helper functions ===========

    public void showMain(User user) throws IOException {
        // hiding the login window
        LoginPane.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/view/main.fxml"));
        // after loading the fxml file the initialize() method in MainController is called
        fxmlLoader.load();
        // fxmlLoader.getController has the very same object (MainController of the fxml file that we loaded)
        // so from here we could manipulate the objects (Nodes) in that widow
        MainController mainController = fxmlLoader.getController();
        // if the user has level lower then 3 the setting Button will be disabled
        if(user.getLevel() < 3)
            mainController.hideSetting();

        // we also passing the user to be used later on the setting and other things
        mainController.setUser(user);
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Main");
        stage.show();
    }

    public void showAddEmployee() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/view/addUser.fxml"));
        fxmlLoader.load();
        AddUserController addUserController = fxmlLoader.getController();
        addUserController.setMode(Config.SIGNUPMODE);
        addUserController.hideLevel();
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Add Employee");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(LoginPane.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();
    }

    // =========== Error handler ==============

    public void usernameError(){
        UsernameErrorMessage.setText("Please enter a valid username!");
        LoginUsername.setStyle("-fx-border-color: red;");
        System.out.println("No Username entered");
    }

    public void passwordError(){
        PasswordErrorMassage.setText("Please enter a valid Password!");
        LoginPassword.setStyle("-fx-border-color: red;");
        System.out.println("No Password entered");
    }

    public void userNotFound(){
        LoginNotFoundLabel.setText("Username or password is not correct");
        System.out.println("username not found");
    }

    // =========== reset =======================

    public void resetStyle(){
        UsernameErrorMessage.setText("");
        PasswordErrorMassage.setText("");
        LoginNotFoundLabel.setText("");
        LoginUsername.setStyle(null);
        LoginPassword.setStyle(null);
    }

}
