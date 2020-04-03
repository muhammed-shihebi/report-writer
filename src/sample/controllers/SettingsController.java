package sample.controllers;

import sample.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public class SettingsController {

    private User user;

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
        refreshUserTable();
        // ============== User Buttons ================

        setAddEmpButton.setOnAction(event -> {
            try {
                showAddUser();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        });

        setEmpRemButton.setOnAction(event -> {
            ObservableList<User> userSelected;
            userSelected = setEmpTableView.getSelectionModel().getSelectedItems();
            if(!userSelected.isEmpty()){
                try {
                    if(removeAlert(userSelected.get(0))){
                        refreshUserTable();
                    }
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
                    // just one item could be selected -> 0
                    showEditUser(userSelected.get(0));
                    refreshUserTable();
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }else{
                selectItemAlert();
            }
        });

        // ============== General Buttons ============

        setOkButton.setOnAction(event -> {
            settingsPane.getScene().getWindow().hide();
        });

    }


    // ============== User Functions  =============

    private void showAddUser() throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/view/addUser.fxml"));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Add Employee");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(settingsPane.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();
        refreshUserTable();
    }

    private void refreshUserTable() throws SQLException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        Connection con = databaseHandler.getConnection();
        ResultSet resultSet = databaseHandler.getAllUsers(con);
        ObservableList<User> users = FXCollections.observableArrayList();
        while (resultSet.next()){
            users.add(new User(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4),
                    resultSet.getInt(5)));
        }
        setEmpTabColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        setEmpTabColSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        setEmpTabColLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        setEmpTableView.setItems(users);
    }

    private void removeUser(User user) throws SQLException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        if (!databaseHandler.deleteUser(user)){
            onlyAdminAlert();
        }
    }

    private void showEditUser(User user) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/view/editUser.fxml"));
        fxmlLoader.load();


        EditUserController editUserController = fxmlLoader.getController();
        editUserController.setSelectedUser(user);


        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setTitle("Edit Employee");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(settingsPane.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();
        refreshUserTable();
    }

    // ============= Alerts ======================

    private boolean removeAlert(User user) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("The only Admin");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to remove: " + user.getName() + " " + user.getSurname());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(settingsPane.getScene().getWindow());
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent()){
            if(result.get() == ButtonType.OK){
                removeUser(user);
                return true;
            }
        }
        return false; // if user exit without clicking anything or if user clicked cancel
    }

    private void selectItemAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("No User Selected");
        alert.setHeaderText(null);
        alert.setContentText("Please select a user");
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

    // ============== Current user User ===============
    public void setUser(User user) {
        this.user = user;
    }
}
