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
    private AnchorPane mainPane;
    @FXML
    private Button settingsButton;
    @FXML
    private ComboBox<String> reportTypeCbox;
    @FXML
    void initialize() {
        // =================fill out roport=======================
        // TODO the name should be in a xml file
        ObservableList<String> reports = FXCollections.observableArrayList(
                "MAGNETIC PARTICLE INSPECTION REPORT"
        );
        reportTypeCbox.setItems(reports);
    }

    // ============== On Action =================

    @FXML
    private void nextOnAction(ActionEvent event) throws IOException {
        if(true){ // ToDo show reports according to the user selection
            showReport2();
        }
    }

    @FXML
    private void LogoutButtonOnAction(ActionEvent event) throws Exception {
        showLogoutAlert();
    }

    @FXML
    private void SettingsButtonOnAction(ActionEvent event) throws IOException {
        showSetting();
    }


    // ============== helping functions ===============

    public void hideSettings(){
        settingsButton.setVisible(false);
    }

    private void showReport2() throws IOException {
        mainPane.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/report2.fxml"));
        loader.load();
        Report2Controller report2Controller = (Report2Controller) loader.getController();
        report2Controller.setUser(user);
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Report2"); // ToDo this should be the name of the report chosen by user
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(mainPane.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    private void showSetting() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/view/settings.fxml"));
        fxmlLoader.load();
        SettingsController settingsController = fxmlLoader.getController();
        settingsController.setUser(user);
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Settings");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(mainPane.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();
    }

    private void logout() throws Exception {
        setUser(null);
        Main main  = new Main();
        mainPane.getScene().getWindow().hide();
        System.out.println("User logged out.");
        main.start(new Stage());
    }

    // ============= Alerts ===========================

    private void showLogoutAlert() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(mainPane.getScene().getWindow());
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent()){
            if(result.get() == ButtonType.OK){
                logout();
            }
        }
    }

    // ============= Setters and Getters ===========================
    public void setUser(User user) {
        this.user = user;
    }
}
