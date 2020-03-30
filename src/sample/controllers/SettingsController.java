package sample.controllers;

import classes.User;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;


public class SettingsController {

    private classes.User user;

    @FXML
    private TableView<String> employeeTable;

    @FXML
    void initialize() {
    }

    // ============== Setter ===============
    public void setUser(User user) {
        this.user = user;
    }
}
