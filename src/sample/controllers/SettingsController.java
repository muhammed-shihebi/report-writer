package sample.controllers;

import javafx.event.ActionEvent;
import sample.database.DatabaseHandler;
import sample.model.*;
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

    private static final String ERORRTEXTFILESTYLE = "-fx-border-color: red;";

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
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private TableColumn<Customer, String> testPlaceColumn;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableView<Equipment> equipmentTableView;

    @FXML
    private TableColumn<Equipment, String> equipmentColumn;

    @FXML
    private TableColumn<Equipment, Double> poleDistanceColumn;

    @FXML
    private TableColumn<Equipment, String> UVLightIntensityColumn;

    @FXML
    private TableColumn<Equipment, String> distanceOfLightColumn;

    @FXML
    private TextField stageOfExaminationField;

    @FXML
    private TextField surfaceConditionField;

    @FXML
    private TableView<SurfaceCondition> surfaceConditionTableView;

    @FXML
    private TableColumn<SurfaceCondition, String> surfaceConditionColumn;

    @FXML
    private TableView<StageOfExamination> stageOfExaminationTableView;

    @FXML
    private TableColumn<StageOfExamination, String> stageOfExaminationColumn;

    @FXML
    void initialize() throws SQLException {
        refreshUserTable();
        refreshCustomerTable();
        refreshEquipmentTable();
        refreshSurfaceConditionTable();
        refreshStageOfExaminationTable();
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
        alert.setTitle("hiçbir şey seçilmedi");
        alert.setHeaderText(null);
        alert.setContentText("Lütfen bir satır seçin");
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
    private void customerAddButtonOnAction(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/customerHandler.fxml"));
        Parent root = fxmlLoader.load();
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
    private void customerEditButtonOnAction(ActionEvent event) throws IOException, SQLException {
        ObservableList<Customer> selectedCustomer;
        selectedCustomer = customerTableView.getSelectionModel().getSelectedItems();
        if(!selectedCustomer.isEmpty()){
            // just one item could be selected -> 0
            showEditCustomer(selectedCustomer.get(0));
        }else{
            selectItemAlert();
        }
    }

    @FXML
    private void customerRemButtonOnAction(ActionEvent event) throws SQLException {
        ObservableList<Customer> selectedCostomer;
        selectedCostomer = customerTableView.getSelectionModel().getSelectedItems();
        if(!selectedCostomer.isEmpty()){
            if(removeAlert(selectedCostomer.get(0))){
                DatabaseHandler.deleteCustomer(selectedCostomer.get(0));
                refreshCustomerTable();
            }
        }else{
            selectItemAlert();
        }
    }

        // ====== Helper Functions ===================

    private void refreshCustomerTable() throws SQLException {
        ObservableList<Customer> customers = DatabaseHandler.getAllCustomers();
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        testPlaceColumn.setCellValueFactory(new PropertyValueFactory<>("testPlace"));
        customerTableView.setItems(customers);
    }

    private void showEditCustomer(Customer customer) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/customerHandler.fxml"));
        Parent root = fxmlLoader.load();
        CustomerHandlerController customerHandlerController = fxmlLoader.getController();
        customerHandlerController.setEditMode(customer);
        Stage stage = new Stage();
        stage.setTitle("Müşteri Düzenlemek");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(settingsPane.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();
        refreshCustomerTable();
    }

        // ====== Alerts =============================

    private boolean removeAlert(Customer customer){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Uyarı");
        alert.setHeaderText(null);
        alert.setContentText(customer.getName() + " Sistemden kaldırmak istediğinizden emin misiniz?");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(settingsPane.getScene().getWindow());
        ButtonType buttonYes = new ButtonType("Evet");
        ButtonType buttonNo = new ButtonType("Hayır");
        alert.getButtonTypes().setAll(buttonYes, buttonNo);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent()){
            return result.get() == buttonYes;
        }
        return false; // if user exit without clicking anything or if user clicked cancel
    }







    


    // ====== Equipment Functions ====================
        //  ====== On Action =========================

    @FXML
    private void equipmentAddButtonOnAction(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/equipmentHandler.fxml"));
        Parent root = fxmlLoader.load();
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
    private void equipmentEditButtonOnAction(ActionEvent event) throws IOException, SQLException {
        ObservableList<Equipment> selectedEquipment;
        selectedEquipment = equipmentTableView.getSelectionModel().getSelectedItems();
        if(!selectedEquipment.isEmpty()){
            // just one item could be selected -> 0
            showEditEquipment(selectedEquipment.get(0));
        }else{
            selectItemAlert();
        }
    }

    @FXML
    private void equipmentRemButtonOnAction(ActionEvent event) throws SQLException {
        ObservableList<Equipment> selectedEquipment;
        selectedEquipment = equipmentTableView.getSelectionModel().getSelectedItems();
        if(!selectedEquipment.isEmpty()){
            if(removeAlert(selectedEquipment.get(0))){
                DatabaseHandler.deleteEquipment(selectedEquipment.get(0));
                refreshEquipmentTable();
            }
        }else{
            selectItemAlert();
        }
    }

    // ====== Helper Functions =======================

    private void showEditEquipment(Equipment equipment) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/equipmentHandler.fxml"));
        Parent root = fxmlLoader.load();
        EquipmentHandlerController equipmentHandlerController = fxmlLoader.getController();
        equipmentHandlerController.setEditMode(equipment);
        Stage stage = new Stage();
        stage.setTitle("Ekipman düzenlemek");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(settingsPane.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();
        refreshEquipmentTable();
    }

    private void refreshEquipmentTable() throws SQLException {
        ObservableList<Equipment> equipments = DatabaseHandler.getAllEquipments();
        equipmentColumn.setCellValueFactory(new PropertyValueFactory<>("equipment"));
        poleDistanceColumn.setCellValueFactory(new PropertyValueFactory<>("poleDistance"));
        UVLightIntensityColumn.setCellValueFactory(new PropertyValueFactory<>("UVLightIntensity"));
        distanceOfLightColumn.setCellValueFactory(new PropertyValueFactory<>("distanceOfLight"));
        equipmentTableView.setItems(equipments);
    }
    // ====== Alerts =============================

    private boolean removeAlert(Equipment equipment){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Uyarı");
        alert.setHeaderText(null);
        alert.setContentText(equipment.getEquipment() + " Sistemden kaldırmak istediğinizden emin misiniz?");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(settingsPane.getScene().getWindow());
        ButtonType buttonYes = new ButtonType("Evet");
        ButtonType buttonNo = new ButtonType("Hayır");
        alert.getButtonTypes().setAll(buttonYes, buttonNo);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent()){
            return result.get() == buttonYes;
        }
        return false; // if user exit without clicking anything or if user clicked cancel
    }












    // ====== Other Things Functions =====================

    @FXML
    void stageOfExaminationAddButtonOnAction(ActionEvent event) throws SQLException {
        stageOfExaminationField.setStyle(null);
        if(stageOfExaminationField.getText().equals("")){
            stageOfExaminationField.setStyle(ERORRTEXTFILESTYLE);
        }else {
            StageOfExamination stageOfExamination = new StageOfExamination(stageOfExaminationField.getText());
            DatabaseHandler.addStageOfExamination(stageOfExamination);
            refreshStageOfExaminationTable();
            stageOfExaminationField.setText("");
        }
    }

    @FXML
    void stageOfExaminationDeleteButtonOnAction(ActionEvent event) throws SQLException {
        ObservableList<StageOfExamination> stageOfExaminationSelected;
        stageOfExaminationSelected = stageOfExaminationTableView.getSelectionModel().getSelectedItems();
        if(!stageOfExaminationSelected.isEmpty()){
            // just one item could be selected -> 0
            DatabaseHandler.deleteStageOfExamination(stageOfExaminationSelected.get(0));
        }else{
            selectItemAlert();
        }
    }

    @FXML
    void surfaceConditionAddButtonOnAction(ActionEvent event) throws SQLException {
        surfaceConditionField.setStyle(null);
        if(surfaceConditionField.getText().equals("")){
            surfaceConditionField.setStyle(ERORRTEXTFILESTYLE);
        }else {
            SurfaceCondition surfaceCondition = new SurfaceCondition(surfaceConditionField.getText());
            DatabaseHandler.addSurfaceCondition(surfaceCondition);
            refreshSurfaceConditionTable();
            surfaceConditionField.setText("");
        }
    }

    @FXML
    void surfaceConditionDeleteButtonOnAction(ActionEvent event) throws SQLException {
        ObservableList<SurfaceCondition> surfaceConditionSelected;
        surfaceConditionSelected = surfaceConditionTableView.getSelectionModel().getSelectedItems();
        if(!surfaceConditionSelected.isEmpty()){
            // just one item could be selected -> 0
            DatabaseHandler.deleteSurfaceCondition(surfaceConditionSelected.get(0));
        }else{
            selectItemAlert();
        }
    }

    // ======= Helper Functions =====================

    private void refreshStageOfExaminationTable() throws SQLException {
        ObservableList<StageOfExamination> stageOfExaminations = DatabaseHandler.getAllStageOfExaminations();
        stageOfExaminationColumn.setCellValueFactory(new PropertyValueFactory<>("stage"));
        stageOfExaminationTableView.setItems(stageOfExaminations);
    }
    private void refreshSurfaceConditionTable() throws SQLException {
        ObservableList<SurfaceCondition> surfaceConditions = DatabaseHandler.getAllSurfaceConditions();
        surfaceConditionColumn.setCellValueFactory(new PropertyValueFactory<>("condition"));
        surfaceConditionTableView.setItems(surfaceConditions);
    }
}
