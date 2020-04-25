package sample.controllers;

import javafx.event.ActionEvent;
import sample.database.DatabaseHandler;
import sample.model.User;
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
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;


public class SettingsController {

    @FXML
    private AnchorPane settingsPane;

    @FXML
    private AnchorPane reportPane;

    @FXML
    private AnchorPane userPane;

    @FXML
    private AnchorPane customerPane;

    @FXML
    private AnchorPane equipmentPane;

    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<User, String> userTabColName;

    @FXML
    private TableColumn<User, String> userTabColSurname;

    @FXML
    private TableColumn<User, Integer> userTabColLevel;

    @FXML
    void initialize() throws SQLException {
        refreshUserTable();
    }


    // ====== Sidebar ================================

    @FXML
    private void OkButtonOnAction(ActionEvent event) {
        settingsPane.getScene().getWindow().hide();
    }

    @FXML
    void reportButtonOnAction(ActionEvent event) {
        reportPane.toFront();
    }

    @FXML
    void customerButtonOnAction(ActionEvent event) {
        customerPane.toFront();
    }

    @FXML
    void userButtonOnAction(ActionEvent event) {
        userPane.toFront();
    }

    @FXML
    private void equipmentButtonOnAction(){
        equipmentPane.toFront();
    }


    // ====== User Functions  ========================
        // ====== On Action ==========================

    @FXML
    private void userAddButtonOnAction(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/userHandler.fxml"));
        Parent root = fxmlLoader.load();
        UserHandlerController userHandlerController = fxmlLoader.getController();
        userHandlerController.setMode(UserHandlerController.ADDMODE);
        Stage stage = new Stage();
        stage.setTitle("Personel Ekle");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(settingsPane.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();
        // refreshing user table after the action is done
        refreshUserTable();
    }

    @FXML
    private void userEditButtonOnAction(ActionEvent event) throws IOException, SQLException {
        ObservableList<User> userSelected;
        userSelected = userTableView.getSelectionModel().getSelectedItems();
        if(!userSelected.isEmpty()){
            // just one item could be selected -> 0
            showEditUser(userSelected.get(0));
        }else{
            selectItemAlert();
        }
    }

    @FXML
    private void userRemButtonOnAction(ActionEvent event) throws SQLException {
        ObservableList<User> userSelected;
        userSelected = userTableView.getSelectionModel().getSelectedItems();
        if(!userSelected.isEmpty()){
            // only if user confirm the removing action in the removeAlert function the user will be removed
            // form the database if it not the only admin in the system
            if(removeAlert(userSelected.get(0))){
                refreshUserTable();
            }
        }else{
            selectItemAlert();
        }
    }

        // ====== Helper Functions ===================

    private void refreshUserTable() throws SQLException {
        // filling out the user table with data from the database
        ObservableList<User> users = DatabaseHandler.getAllUsers();
        userTabColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        userTabColSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        userTabColLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        userTableView.setItems(users);
    }

    private void removeUser(User user) throws SQLException {
        if (!DatabaseHandler.deleteUser(user)){
            // this means the user is trying to delete the only admin in the system witch is not allowed
            onlyAdminAlert();
        }
    }

    private void showEditUser(User user) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/userHandler.fxml"));
        Parent root = fxmlLoader.load();
        UserHandlerController userHandlerController = fxmlLoader.getController();
        userHandlerController.setSelectedUser(user);
        userHandlerController.setEditMode();
        Stage stage = new Stage();
        stage.setTitle("Personel Düzenle");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(settingsPane.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();
        refreshUserTable();
    }

        // ====== Alerts =============================

    private boolean removeAlert(User user) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Uyarı");
        alert.setHeaderText(null);
        alert.setContentText(user.getName() + " " + user.getSurname() +
                " Sistemden kaldırmak istediğinizden emin misiniz?");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(settingsPane.getScene().getWindow());
        ButtonType buttonYes = new ButtonType("Evet");
        ButtonType buttonNo = new ButtonType("Hayır");
        alert.getButtonTypes().setAll(buttonYes, buttonNo);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent()){
            if(result.get() == buttonYes){
                //
                removeUser(user);
                return true;
            }
        }
        return false; // if user exit without clicking anything or if user clicked cancel
    }

    private void selectItemAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Personel Seçilmedi");
        alert.setHeaderText(null);
        alert.setContentText("Lütfen bir personel seçin");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(settingsPane.getScene().getWindow());
        ButtonType buttonYes = new ButtonType("Tamam");
        alert.getButtonTypes().setAll(buttonYes);
        alert.show();
    }

    private void onlyAdminAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Tek Admin");
        alert.setHeaderText(null);
        alert.setContentText("Üç seviye taşıyan tek personel kaldıramazsınız. " +
                "Bunun yerine başka bir 3 seviyeli bir personel ekledikten sonra bunu silebilirsiniz. ");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(settingsPane.getScene().getWindow());
        ButtonType buttonYes = new ButtonType("Tamam");
        alert.getButtonTypes().setAll(buttonYes);
        alert.show();
    }

    // ====== Customer Functions =====================
        // ====== On Action ==========================

    @FXML
    private void customerAddButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/customerHandler.fxml"));
        Parent root = fxmlLoader.load();
        CustomerHandlerController customerHandlerController = fxmlLoader.getController();
        customerHandlerController.setMode(CustomerHandlerController.ADDMODE);
        Stage stage = new Stage();
        stage.setTitle("Müşteri Eklemek");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(settingsPane.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();
        refreshCustomerTable();
    }

    @FXML
    private void customerEditButtonOnAction(ActionEvent event) {
        // todo
    }

    @FXML
    private void customerRemButtonOnAction(ActionEvent event) {
        // todo
    }

        // ====== Helper Functions ===================

    private void refreshCustomerTable(){
        // todo
    }

    // ====== Equipment Functions ====================
        //  ====== On Action =========================

    @FXML
    private void equipmentAddButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/r2EquipmentHandler.fxml"));
        Parent root = fxmlLoader.load();
        R2EquipmentController r2EquipmentController =fxmlLoader.getController();
        r2EquipmentController.setMode(R2EquipmentController.ADDMODE);
        Stage stage = new Stage();
        stage.setTitle("Ekipman Eklemek");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(settingsPane.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();
        refreshEquipmentTable();
    }

    @FXML
    private void equipmentEditButtonOnAction(ActionEvent event) {
        // todo
    }

    @FXML
    private void equipmentRemButtonOnAction(ActionEvent event) {
        // todo
    }

    // ====== Helper Functions =======================

    private void refreshEquipmentTable(){
        // todo
    }
}
