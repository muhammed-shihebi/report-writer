package sample.controllers;

import classes.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SettingsController {

    private classes.User user;

    @FXML
    private AnchorPane settingsPane;

    @FXML
    private TableView<User> setEmpTableView;

    @FXML
    private TableColumn<User, String> setEmpTabColName;

    @FXML
    private TableColumn<User, String> setEmpTabColSurname;

    @FXML
    private TableColumn<User, Integer> setEmpTabColLevel;

    @FXML
    private TableColumn<User, String> setEmpTabColTel;

    @FXML
    private TableColumn<User, String> setEmpTabColEmail;

    @FXML
    private Button setEmpRemButton;

    @FXML
    private Button setEmpEditButton;

    @FXML
    private Button setAddEmpButton;

    @FXML
    private Button setOkButton;

    @FXML
    void initialize() throws SQLException {
        // ============== Table ==================
        refreshTable();
        // ============== buttons ================

        setAddEmpButton.setOnAction(event -> {
            try {
                showAdd();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        });

        setEmpRemButton.setOnAction(event -> {
            ObservableList<User> userSelected;
            userSelected = setEmpTableView.getSelectionModel().getSelectedItems();
            if(!userSelected.isEmpty()){
                try {
                    removeUser(userSelected.get(0));
                    refreshTable();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                selectItemAlert();
            }
        });

        setEmpEditButton.setOnAction(event -> {
            ObservableList<User> userSelected;
            userSelected = setEmpTableView.getSelectionModel().getSelectedItems();
            if(!userSelected.isEmpty()){
                try {
                    editUser(userSelected.get(0));
                    refreshTable();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                selectItemAlert();
            }
        });

        setOkButton.setOnAction(event -> {
            settingsPane.getScene().getWindow().hide();
        });
    }


    // ============== Helper functions =====

    private void showAdd() throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/view/addEmployee.fxml"));
        fxmlLoader.load();
        AddEmployeeController addEmployeeController = fxmlLoader.getController();
        addEmployeeController.setUser(user);
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Add Employee");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(settingsPane.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();
        refreshTable();
    }

    private void refreshTable() throws SQLException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        Connection con = databaseHandler.getConnection();
        ResultSet resultSet = databaseHandler.getAllUsers(con);
        ObservableList<User> users = FXCollections.observableArrayList();
        while (resultSet.next()){
            users.add(new User(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4),
                    resultSet.getInt(5), resultSet.getString(6),
                    resultSet.getString(7)));
        }
        setEmpTabColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        setEmpTabColSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        setEmpTabColLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        setEmpTabColTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        setEmpTabColEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        setEmpTableView.setItems(users);
    }

    private void removeUser(User user) throws SQLException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        if (!databaseHandler.deleteUser(user)){
            onlyAdminAlert();
        }
    }

    private void editUser(User user){
        // to be implemented
    }

    // ============= Alerts ======================

    private void selectItemAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("No User Selected");
        alert.setHeaderText(null);
        alert.setContentText("Please select a user to delete it");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(settingsPane.getScene().getWindow());
        alert.showAndWait();
    }

    private void onlyAdminAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("The only Admin");
        alert.setHeaderText(null);
        alert.setContentText("You can not remove the only admin in the system. Instead you" +
                " could add another admin and then delete this");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(settingsPane.getScene().getWindow());
        alert.showAndWait();
    }

    // ============== Setter ===============
    public void setUser(User user) {
        this.user = user;
    }
}
