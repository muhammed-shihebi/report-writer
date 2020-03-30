package sample.controllers;

import classes.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private AnchorPane MainPane;

    @FXML
    private Button mainSettingsButton;

    @FXML
    private Label mainSettingLabel;

    private User user;

    @FXML
    void initialize() {
        System.out.println("Initializing the login window");

        // ============= Setting button clicked ==============
        mainSettingsButton.setOnAction(event -> {
            System.out.println("Showing the Setting window and blocking the main window");
            try {
                showSetting();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    // ============== helping functions ===============
    public void hideSetting(){
        mainSettingsButton.setDisable(true);
        mainSettingLabel.setTextFill(Color.web("gray"));
    }

    public void showSetting() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/view/settings.fxml"));
        fxmlLoader.load();
        SettingsController settingsController = fxmlLoader.getController();
        settingsController.setUser(user);
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Settings");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(MainPane.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();
    }

    // ============= Setter ===========================
    public void setUser(User user) {
        this.user = user;
    }
}
