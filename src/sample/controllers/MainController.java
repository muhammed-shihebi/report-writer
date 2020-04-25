package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import sample.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;
import java.util.Optional;

public class MainController {
    
    private User user;

    @FXML
    private Label helloLabel;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button settingsButton;

    @FXML
    private ComboBox<String> reportTypeCbox;

    @FXML
    void initialize() {
        ObservableList<String> reports = FXCollections.observableArrayList(
                "MAGNETIC PARTICLE INSPECTION REPORT"
        );
        reportTypeCbox.setItems(reports);
    }

    // ====== On Action ==============================

    @FXML
    private void reportingOnAction(ActionEvent event) throws IOException {
        // ToDo show reports according to the user selection
        // helper function is used because getting Report will not be always the case
        // when adding new reports
        showReport2();
    }

    @FXML
    private void LogoutButtonOnAction(ActionEvent event) throws Exception {
        // showing alert to confirm the logout action
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Çıkış yapmak");
        alert.setHeaderText(null);
        alert.setContentText("Çıkış yapmak istediğinizden emin misiniz?");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(mainPane.getScene().getWindow());
        ButtonType buttonYes = new ButtonType("Evet");
        ButtonType buttonNo = new ButtonType("Hayır");
        alert.getButtonTypes().setAll(buttonYes, buttonNo);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent()){
            if(result.get() == buttonYes){
                logout();
            }
        }
    }

    @FXML
    private void SettingsButtonOnAction(ActionEvent event) throws IOException {
        // showing the setting window
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/settings.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Settings");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
    }

    // ====== Helper Functions ======================

    public void showSettingsButton(){
        // showing settings button mean a admin has signed in
        settingsButton.setVisible(true);
    }

    private void showReport2() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/report2.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("MAGNETIC PARTICLE INSPECTION REPORT"); // ToDo this should be the name of the report chosen by user
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
    }

    private void logout() throws Exception {
        // set user to make sure the logout action is done
        setUser(new User());
        Main main  = new Main();
        mainPane.getScene().getWindow().hide();
        System.out.println("User logged out.");
        main.start(new Stage());
    }

    // ====== Setters and Getters ====================

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setHelloLabel(){
        // setting hello text to great the current user
        helloLabel.setText(user.getName() + " " + user.getSurname());
    }
}
