package sample.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import sample.handlers.DatabaseHandler;
import sample.handlers.PDFHandler;
import sample.model.Customer;
import sample.model.Equipment;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class MainController {

    private User user;

    private static final String ERORRTEXTFIELDSTYLE = "-fx-border-color: red;";

    @FXML
    private AnchorPane mainPane;

    @FXML
    private ComboBox<String> reportTypeComboBox;

    @FXML
    private ComboBox<Equipment> equipmentComboBox;

    @FXML
    private ComboBox<Customer> costumerComboBox;

    @FXML
    private Button settingsButton;

    @FXML
    private ComboBox<User> operatorCombobox;

    @FXML
    private ComboBox<User> evaluatorComboBox;

    @FXML
    private ComboBox<User> conformerComboBox;

    @FXML
    private TextField reportNoField;

    @FXML
    private DatePicker reportDateField;

    @FXML
    private Label reportNoMesg;

    @FXML
    private Label reportDateMesg;

    @FXML
    private Label helloLabel;

    @FXML
    void initialize() throws SQLException {
        showSettingsButton(); // Todo remove this
        ObservableList<Equipment> equipments = DatabaseHandler.getAllEquipments();
        equipmentComboBox.setItems(equipments);
        ObservableList<Customer> customers = DatabaseHandler.getAllCustomers();
        costumerComboBox.setItems(customers);
        ObservableList<User> conformers = DatabaseHandler.getConformers();
        conformerComboBox.setItems(conformers);
        ObservableList<User> evaluators = DatabaseHandler.getEvaluators();
        evaluatorComboBox.setItems(evaluators);
        ObservableList<User> operators = DatabaseHandler.getAllUsers();
        operatorCombobox.setItems(operators);
        reportDateField.setValue(LocalDate.now());
    }

    // ====== On Action ==============================

    @FXML
    private void reportingOnAction(ActionEvent event) throws IOException, SQLException {
        resetStyle();
        if(!areFieldsEmpty()){
            showReport();
        }
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

    // ====== Emptiness checking functions =======

    private boolean areFieldsEmpty(){
        boolean emptiness = false;
        if(equipmentComboBox.getValue() == null){
            equipmentComboBox.setStyle(ERORRTEXTFIELDSTYLE);
            emptiness = true;
        }
        if(reportNoField.getText().equals("") || !reportNoField.getText().matches("(\\p{Digit}+)")){
            reportNoField.setStyle(ERORRTEXTFIELDSTYLE);
            reportNoMesg.setVisible(true);
            emptiness = true;
        }
        if(reportDateField.getEditor().getText().equals("")){
            reportDateField.setStyle(ERORRTEXTFIELDSTYLE);
            reportDateMesg.setVisible(true);
            emptiness = true;
        }
        if(operatorCombobox.getValue() == null){
            operatorCombobox.setStyle(ERORRTEXTFIELDSTYLE);
            emptiness = true;
        }
        if(evaluatorComboBox.getValue() == null ){
            evaluatorComboBox.setStyle(ERORRTEXTFIELDSTYLE);
            emptiness = true;
        }
        if(conformerComboBox.getValue() == null){
            conformerComboBox.setStyle(ERORRTEXTFIELDSTYLE);
            emptiness = true;
        }
        if(costumerComboBox.getValue() == null){
            costumerComboBox.setStyle(ERORRTEXTFIELDSTYLE);
            emptiness = true;
        }

        return emptiness;
    }

    // ====== Helper Functions ======================

    public void showSettingsButton(){
        // showing settings button mean a admin has signed in
        settingsButton.setVisible(true);
    }

    private void showReport() throws IOException, SQLException {
        Equipment equipment = equipmentComboBox.getValue();
        String reportNo = reportNoField.getText();
        LocalDate reportDate = reportDateField.getValue();
        User operator = operatorCombobox.getValue();
        User evaluator = evaluatorComboBox.getValue();
        User conformer = conformerComboBox.getValue();
        Customer customer = costumerComboBox.getValue();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/report.fxml"));
        Parent root = fxmlLoader.load();
        ReportController reportController = fxmlLoader.getController();

        reportController.setEquipment(equipment);
        reportController.setReportNo(reportNo);
        reportController.setReportDate(reportDate);
        reportController.setOperator(operator);
        reportController.setEvaluator(evaluator);
        reportController.setConformer(conformer);
        reportController.setCustomer(customer);
        reportController.setData();


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

    private void resetStyle(){
        equipmentComboBox.setStyle(null);
        reportNoField.setStyle(null);
        reportNoMesg.setVisible(false);
        reportDateField.setStyle(null);
        reportDateMesg.setVisible(false);
        operatorCombobox.setStyle(null);
        evaluatorComboBox.setStyle(null);
        conformerComboBox.setStyle(null);
        costumerComboBox.setStyle(null);
    }

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
