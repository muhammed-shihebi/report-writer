package sample.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.WindowEvent;
import sample.handlers.DatabaseHandler;
import sample.model.Customer;
import sample.model.Equipment;
import sample.model.Report;
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
        Report.MAXDATE = LocalDate.now();
        reportDateField.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(Report.MAXDATE) || item.isBefore(Report.MINDATE));
                    }});

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
        if(!areFieldsLegal()){
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/settings.fxml"));
        Parent root = fxmlLoader.load();
        SettingsController settingsController = fxmlLoader.getController();
        settingsController.setUser(getUser());
        Stage stage = new Stage();
        stage.setTitle("Settings");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.sizeToScene();
        mainPane.getScene().getWindow().hide();
        stage.show();
        stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, event1 -> {
            try {
                showMain(event1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void showMain(WindowEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/main.fxml"));
        Parent root = fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();
        // showing settings if the user is an admin
        if(user.getLevel() >= User.LEVEL3)
            mainController.showSettingsButton();
        mainController.setUser(user);
        mainController.setHelloLabel();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Ana Ekran");
        stage.show();
    }

    // ====== On Key ==============================

    // these functions are made to sense any change in text fields and report errors immediately

    @FXML
    void reportNoFieldOnKey(KeyEvent event) {
        reportNoField.setStyle(null);
        reportNoMesg.setVisible(false);
        if(reportNoField.getText().equals("") || !reportNoField.getText().matches("(\\p{Digit}+)")|| reportNoField.getText().length() > 50){
            reportNoField.setStyle(ERORRTEXTFIELDSTYLE);
            reportNoMesg.setVisible(true);
        }
    }

    // ====== Checking functions =======

    private boolean areFieldsLegal(){
        boolean correctness = false;
        if(equipmentComboBox.getValue() == null){
            equipmentComboBox.setStyle(ERORRTEXTFIELDSTYLE);
            correctness = true;
        }
        if(reportNoField.getText().equals("") || !reportNoField.getText().matches("(\\p{Digit}+)") || reportNoField.getText().length() > 50){
            reportNoField.setStyle(ERORRTEXTFIELDSTYLE);
            reportNoMesg.setVisible(true);
            correctness = true;
        }
        if(reportDateField.getEditor().getText().equals("")){
            reportDateField.setStyle(ERORRTEXTFIELDSTYLE);
            reportDateMesg.setVisible(true);
            correctness = true;
        }
        if(operatorCombobox.getValue() == null){
            operatorCombobox.setStyle(ERORRTEXTFIELDSTYLE);
            correctness = true;
        }
        if(evaluatorComboBox.getValue() == null){
            evaluatorComboBox.setStyle(ERORRTEXTFIELDSTYLE);
            correctness = true;
        }
        if(conformerComboBox.getValue() == null){
            conformerComboBox.setStyle(ERORRTEXTFIELDSTYLE);
            correctness = true;
        }
        if(costumerComboBox.getValue() == null){
            costumerComboBox.setStyle(ERORRTEXTFIELDSTYLE);
            correctness = true;
        }
        return correctness;
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
        User user = this.user;

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
        reportController.setUser(user);
        reportController.setCustomers(DatabaseHandler.getAllCustomers());
        reportController.setOperators(DatabaseHandler.getAllUsers());
        reportController.setEvaluators(DatabaseHandler.getEvaluators());
        reportController.setConformers(DatabaseHandler.getConformers());
        reportController.setData();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("MAGNETIC PARTICLE INSPECTION REPORT");
        stage.setResizable(false);
        stage.sizeToScene();
        mainPane.getScene().getWindow().hide();
        stage.show();
        stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
            try {
                if(ReportController.cancelAlert()){
                    showMain(event);
                }else {
                    // make the event useless
                    event.consume();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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
        helloLabel.setText(user.toString());
    }

}
