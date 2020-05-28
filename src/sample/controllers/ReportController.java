package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;
import sample.handlers.DatabaseHandler;
import sample.handlers.ExcelHandler;
import sample.handlers.PDFHandler;
import sample.model.*;
import sample.model.InspectionResult;
import sample.model.Report;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ReportController {

    private static final String ERORRTEXTFILESTYLE = "-fx-border-color: red;";
    private static final String PDF = "PDF";
    private static final String EXCEL= "EXCEL";
    private Equipment equipment;
    private String reportNo;
    private LocalDate reportDate;
    private User operator;
    private User evaluator;
    private User conformer;
    private Customer customer;
    private User user;

    @FXML
    private AnchorPane generalPane;

    @FXML
    private Pane equipmentPane;

    @FXML
    private Pane personnelPane;

    @FXML
    private Pane inspectionPane;

    @FXML
    private Pane customerAndReportPane;

    @FXML
    private TextField MPCarrierMediumField;

    @FXML
    private TextField poleDistanceField;

    @FXML
    private TextField equipmentField;

    @FXML
    private TextField UVLightIntensityField;

    @FXML
    private TextField magTechField;

    @FXML
    private TextField distanceOfLightField;

    @FXML
    private TextField examinationAreaField;

    @FXML
    private ComboBox<String> currentTypeComboBox;

    @FXML
    private TextField luxmeterField;

    @FXML
    private TextField demagnetizationField;

    @FXML
    private TextField testMediumField;

    @FXML
    private TextField heatTreatmentField;

    @FXML
    private TextField surfaceTemperatureField;

    @FXML
    private TextField gaussFieldStrengthField;

    @FXML
    private TextField equipmentSurfaceConditionField;

    @FXML
    private TextField liftingTestDateNumberField;

    @FXML
    private TextField identificationOfLightEquipField;

    @FXML
    private TextField standardDeviationsField;

    @FXML
    private CheckBox buttWeldCheckBox;

    @FXML
    private CheckBox filerWeldCheckBox;

    @FXML
    private TextField descriptionAndAttachmentsField;

    @FXML
    private DatePicker inspectionDatesDatePicker;

    @FXML
    private TextField operatorNameField;

    @FXML
    private TextField operatorLevelField;

    @FXML
    private TextField evaluatorNameField;

    @FXML
    private TextField evaluatorLevelField;

    @FXML
    private TextField conformerNameField;

    @FXML
    private TextField conformerLevelField;

    @FXML
    private DatePicker operatorDateDatePicker;

    @FXML
    private DatePicker evaluatorDateDatePicker;

    @FXML
    private DatePicker conformerDateDatePicker;

    @FXML
    private ComboBox<Customer> costumerComboBox;

    @FXML
    private ComboBox<ProjectName> projectNameComboBox;

    @FXML
    private TextField testPlaceField;

    @FXML
    private TextField inspectionStandardField;

    @FXML
    private TextField evaluationStandardField;

    @FXML
    private TextField inspectionProcedureField;

    @FXML
    private TextField drawingNoField;

    @FXML
    private TextField reportNoField;

    @FXML
    private ComboBox<Integer> inspectionScopeComboBox;

    @FXML
    private ComboBox<Integer> numberOfPagesComboBox;

    @FXML
    private ComboBox<JobOrderNo> jobOrderNoComboBox;

    @FXML
    private ComboBox<OfferNo> offerNoComboBox;

    @FXML
    private DatePicker reportDateDatePicker;

    @FXML
    private TableView<InspectionResult> inspectionResultTableView;

    @FXML
    private TableColumn<InspectionResult, Integer> serialNoColumn;

    @FXML
    private TableColumn<InspectionResult, String> weldPieceNoColumn;

    @FXML
    private TableColumn<InspectionResult, Double> testLengthColumn;

    @FXML
    private TableColumn<InspectionResult, String> weldingProcessColumn;

    @FXML
    private TableColumn<InspectionResult, Double> thicknessColumn;

    @FXML
    private TableColumn<InspectionResult, String> diameterColumn;

    @FXML
    private TableColumn<InspectionResult, String> defectTypeColumn;

    @FXML
    private TableColumn<InspectionResult, String> defectLocColumn;

    @FXML
    private TableColumn<InspectionResult, Boolean> resultColumn;

    @FXML
    private TextField weldPieceNoField;

    @FXML
    private TextField testLengthField;

    @FXML
    private TextField weldingProcessField;

    @FXML
    private TextField thicknessField;

    @FXML
    private TextField diameterField;

    @FXML
    private TextField defectTypeField;

    @FXML
    private TextField defectLocField;

    @FXML
    private ComboBox<String> resultComboBox; // RED or OK

    @FXML
    private Button pdfButton;

    @FXML
    private Button excdlButton;

    @FXML
    private ComboBox<SurfaceCondition> surfaceConditionComboBox;

    @FXML
    private ComboBox<StageOfExamination> stageOfExaminationComboBox;

    @FXML
    private Button inspectionResultButton;

    @FXML
    private Button customerButton;

    @FXML
    private Button equipmentButton;

    @FXML
    private Button personnelButton;

    @FXML
    void initialize() throws SQLException {
        // Init inspectionScopes
        ObservableList<Integer> inspectionScopes = FXCollections.observableArrayList();
        for(int i = Report.MINSCOPE; i <= Report.MAXSCOPE; i++){
            inspectionScopes.add(i);
        }
        inspectionScopeComboBox.setItems(inspectionScopes);
        inspectionScopeComboBox.setValue(Report.MAXSCOPE); // 100 is the default

        // Init SurfaceCondition
        surfaceConditionComboBox.setItems(DatabaseHandler.getAllSurfaceConditions());

        // Init StageOfExamination
        stageOfExaminationComboBox.setItems(DatabaseHandler.getAllStageOfExaminations());

        // Init numberOfPagesComboBox
        ObservableList<Integer> numberOfPages = inspectionScopes;
        numberOfPagesComboBox.setItems(numberOfPages);
        numberOfPagesComboBox.setValue(1); // 1 is the default

        // Init reportNo
        reportNoField.setText(reportNo);

        // Init reportDate
        reportDateDatePicker.setValue(reportDate);

        // Init currentTypeComboBox
        currentTypeComboBox.getItems().addAll(Report.AC, Report.DC);
        currentTypeComboBox.setValue(Report.AC);

        // Init resultComboBox
        resultComboBox.getItems().addAll(InspectionResult.OK, InspectionResult.RED);

        // Init InspectionResults Columns
        serialNoColumn.setCellValueFactory(new PropertyValueFactory<>("serialNo"));
        weldPieceNoColumn.setCellValueFactory(new PropertyValueFactory<>("weldPieceNo"));
        testLengthColumn.setCellValueFactory(new PropertyValueFactory<>("testLength"));
        weldingProcessColumn.setCellValueFactory(new PropertyValueFactory<>("weldingProcess"));
        thicknessColumn.setCellValueFactory(new PropertyValueFactory<>("thickness"));
        diameterColumn.setCellValueFactory(new PropertyValueFactory<>("diameter"));
        defectTypeColumn.setCellValueFactory(new PropertyValueFactory<>("defectType"));
        defectLocColumn.setCellValueFactory(new PropertyValueFactory<>("defectLoc"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
    }

    // ====== On Action ==============================

    @FXML
    private void customerAndReportOnAction(ActionEvent event) {
        customerAndReportPane.toFront();
    }

    @FXML
    private void equipmentOnAuction(ActionEvent event) {
        equipmentPane.toFront();
    }

    @FXML
    private void inspectionOnAction(ActionEvent event) {
        inspectionPane.toFront();
    }

    @FXML
    private void personnelOnAction(ActionEvent event) {
        personnelPane.toFront();
    }

    @FXML
    private void cancelOnAction(ActionEvent event) throws IOException {
        if(cancelAlert()){
            generalPane.getScene().getWindow().hide();
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
    }

    @FXML
    void excelButtonOnAction(ActionEvent event) {
        Report report = getReport();
        if(report != null){
            String path = getPath(EXCEL);
            if(path != null)
                try {
                    ExcelHandler.getExcel(report, path);
                }catch (IOException e){
                    cantSaveFileAlert(e);
                }
        }
    }

    @FXML
    void pdfButtonOnAction(ActionEvent event){
        Report report = getReport();
        if(report != null){
            String path = getPath(PDF);
            if(path != null)
                try {
                    PDFHandler.getPDF(report, path);
                }catch (IOException e){
                    cantSaveFileAlert(e);
                }
        }
    }

    @FXML
    void InspectionResultAddButtonOnAction(ActionEvent event) {
        if(inspectionResultTableView.getItems().size() >= InspectionResult.MAXSERIALNO){
            outOfBoundaryAlert();
        }else{
            if(!areInsResFieldsEmpty()){
                InspectionResult inspectionResult = new InspectionResult(
                        inspectionResultTableView.getItems().size() + 1,
                        weldPieceNoField.getText(),
                        Double.parseDouble(testLengthField.getText()),
                        weldingProcessField.getText(),
                        Double.parseDouble(thicknessField.getText()),
                        diameterField.getText(),
                        defectTypeField.getText(),
                        defectLocField.getText(),
                        resultComboBox.getValue()

                );
                inspectionResultTableView.getItems().add(inspectionResult);
                inspectionResultTableView.setStyle(null);
                inspectionResultButton.setStyle(null);
            }
        }
    }

    @FXML
    void InspectionResultDeleteButtonOnAction(ActionEvent event) {
        InspectionResult item =  inspectionResultTableView.getSelectionModel().getSelectedItem();
        if(item != null){
            inspectionResultTableView.getItems().remove(item);
            // update serialNo
            for(int i = 1; i <= inspectionResultTableView.getItems().size(); i++){
                inspectionResultTableView.getItems().get(i-1).setSerialNo(i);
            }
        }else {
            selectItemAlert();
        }
    }

    // ====== Helper Functions =======================

    public void setData() {
        // Users Data
        operatorNameField.setText(operator.toString());
        operatorLevelField.setText(String.valueOf(operator.getLevel()));
        operatorDateDatePicker.setValue(reportDate);

        evaluatorNameField.setText(evaluator.toString());
        evaluatorLevelField.setText(String.valueOf(evaluator.getLevel()));
        evaluatorDateDatePicker.setValue(reportDate);

        conformerNameField.setText(conformer.toString());
        conformerLevelField.setText(String.valueOf(conformer.getLevel()));
        conformerDateDatePicker.setValue(reportDate);

        // Equipment Data
        poleDistanceField.setText(String.valueOf(equipment.getPoleDistance()));
        equipmentField.setText(equipment.getEquipment());
        MPCarrierMediumField.setText(equipment.getMPCarrierMedium());
        magTechField.setText(equipment.getMagTech());
        UVLightIntensityField.setText(equipment.getUVLightIntensity());
        distanceOfLightField.setText(equipment.getDistanceOfLight());

        // Costumer Data
        costumerComboBox.setValue(customer);
        testPlaceField.setText(customer.getTestPlace());
        jobOrderNoComboBox.setItems(customer.getJobOrderNos());
        projectNameComboBox.setItems(customer.getProjectNames());
        offerNoComboBox.setItems(customer.getOfferNos());

        // Other Data
        reportDateDatePicker.setValue(reportDate);
        reportNoField.setText(reportNo);
        inspectionDatesDatePicker.setValue(reportDate);
    }

    public Report getReport(){
        Report report = null;
        boolean check = !areEquipmentFieldsEmpty();
        check = !isInsResTableViewEmpty() && check;
        check = !areCostumerFieldsEmpty() && check;
        if (check){
            customer = costumerComboBox.getValue();
            customer.setTestPlace(testPlaceField.getText());
            equipment = new Equipment(
                    Double.parseDouble(poleDistanceField.getText()),
                    equipmentField.getText(),
                    MPCarrierMediumField.getText(),
                    magTechField.getText(),
                    UVLightIntensityField.getText(),
                    distanceOfLightField.getText()
            );
            report = new Report(
                    operator,
                    evaluator,
                    conformer,
                    customer,
                    stageOfExaminationComboBox.getValue(),
                    surfaceConditionComboBox.getValue(),
                    equipment,
                    inspectionResultTableView.getItems(),
                    inspectionStandardField.getText(),
                    evaluationStandardField.getText(),
                    inspectionProcedureField.getText(),
                    inspectionScopeComboBox.getValue(),
                    drawingNoField.getText(),
                    numberOfPagesComboBox.getValue(),
                    reportNo,
                    reportDateDatePicker.getValue(),
                    examinationAreaField.getText(),
                    currentTypeComboBox.getValue(),
                    luxmeterField.getText(),
                    testMediumField.getText(),
                    demagnetizationField.getText(),
                    heatTreatmentField.getText(),
                    Double.parseDouble(surfaceTemperatureField.getText()),
                    gaussFieldStrengthField.getText(),
                    equipmentSurfaceConditionField.getText(),
                    identificationOfLightEquipField.getText(),
                    liftingTestDateNumberField.getText(),
                    filerWeldCheckBox.isSelected(),
                    buttWeldCheckBox.isSelected(),
                    standardDeviationsField.getText(),
                    inspectionDatesDatePicker.getValue(),
                    descriptionAndAttachmentsField.getText(),
                    jobOrderNoComboBox.getValue(),
                    offerNoComboBox.getValue(),
                    projectNameComboBox.getValue(),
                    operatorDateDatePicker.getValue(),
                    evaluatorDateDatePicker.getValue(),
                    conformerDateDatePicker.getValue()
            );
        }
        return report;
    }

    public String getPath(String str){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter;
        if(str.equals(EXCEL))
            extFilter = new FileChooser.ExtensionFilter("Excel (*.xlsx)", "*.xlsx");
        else
            extFilter = new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().addAll(extFilter);
        fileChooser.setInitialFileName("Report");
        File file = fileChooser.showSaveDialog(generalPane.getScene().getWindow());
        if(file == null)
            return null;
        System.out.println(file.getPath());
        return file.getPath();
    }

    // ====== emptiness Checking =====================

    public boolean areInsResFieldsEmpty(){
        // reset style of fields
        weldPieceNoField.setStyle(null);
        testLengthField.setStyle(null);
        weldingProcessField.setStyle(null);
        thicknessField.setStyle(null);
        defectTypeField.setStyle(null);
        defectLocField.setStyle(null);
        resultComboBox.setStyle(null);

        boolean emptiness = false;
        if(weldPieceNoField.getText().equals("")  || !PDFHandler.isStringLegal(weldPieceNoField.getText())){
            weldPieceNoField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(testLengthField.getText().equals("") ||
                !EquipmentHandlerController.isDouble(testLengthField.getText())){
            testLengthField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(weldingProcessField.getText().equals("")|| !PDFHandler.isStringLegal(weldingProcessField.getText())){
            weldingProcessField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(thicknessField.getText().equals("") ||
                !EquipmentHandlerController.isDouble(thicknessField.getText())){
            thicknessField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(resultComboBox.getValue() != null && resultComboBox.getValue().equals(InspectionResult.RED) &&
                (defectTypeField.getText().equals("") || !PDFHandler.isStringLegal(defectTypeField.getText()))){
            defectTypeField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(resultComboBox.getValue() != null && resultComboBox.getValue().equals(InspectionResult.RED) &&
                (defectLocField.getText().equals("")|| !PDFHandler.isStringLegal(defectLocField.getText()))){
            defectLocField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(resultComboBox.getValue() == null){
            resultComboBox.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        return emptiness;
    }

    public boolean areEquipmentFieldsEmpty(){
        // reset styles
        poleDistanceField.setStyle(null);
        equipmentField.setStyle(null);
        MPCarrierMediumField.setStyle(null);
        magTechField.setStyle(null);
        UVLightIntensityField.setStyle(null);
        distanceOfLightField.setStyle(null);
        examinationAreaField.setStyle(null);
        luxmeterField.setStyle(null);
        surfaceTemperatureField.setStyle(null);
        gaussFieldStrengthField.setStyle(null);
        equipmentSurfaceConditionField.setStyle(null);
        identificationOfLightEquipField.setStyle(null);
        liftingTestDateNumberField.setStyle(null);
        standardDeviationsField.setStyle(null);
        equipmentButton.setStyle(null);

        boolean emptiness = false;
        if(poleDistanceField.getText().equals("") ||
                !EquipmentHandlerController.isDouble(poleDistanceField.getText())){
            poleDistanceField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(equipmentField.getText().equals("")|| !PDFHandler.isStringLegal(equipmentField.getText())){
            equipmentField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(MPCarrierMediumField.getText().equals("")|| !PDFHandler.isStringLegal(MPCarrierMediumField.getText())){
            MPCarrierMediumField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(magTechField.getText().equals("")|| !PDFHandler.isStringLegal(magTechField.getText())){
            magTechField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(UVLightIntensityField.getText().equals("")|| !PDFHandler.isStringLegal(UVLightIntensityField.getText())){
            UVLightIntensityField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(distanceOfLightField.getText().equals("")|| !PDFHandler.isStringLegal(distanceOfLightField.getText())){
            distanceOfLightField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(examinationAreaField.getText().equals("")|| !PDFHandler.isStringLegal(examinationAreaField.getText())){
            examinationAreaField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(luxmeterField.getText().equals("")|| !PDFHandler.isStringLegal(luxmeterField.getText())){
            luxmeterField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }

        if(surfaceTemperatureField.getText().equals("") ||
                !EquipmentHandlerController.isDouble(poleDistanceField.getText())){
            surfaceTemperatureField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(gaussFieldStrengthField.getText().equals("")|| !PDFHandler.isStringLegal(gaussFieldStrengthField.getText())){
            gaussFieldStrengthField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(equipmentSurfaceConditionField.getText().equals("")|| !PDFHandler.isStringLegal(equipmentSurfaceConditionField.getText())){
            equipmentSurfaceConditionField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(identificationOfLightEquipField.getText().equals("")|| !PDFHandler.isStringLegal(identificationOfLightEquipField.getText())){
            identificationOfLightEquipField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(liftingTestDateNumberField.getText().equals("")|| !PDFHandler.isStringLegal(liftingTestDateNumberField.getText())){
            liftingTestDateNumberField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(standardDeviationsField.getText().equals("")|| !PDFHandler.isStringLegal(standardDeviationsField.getText())){
            standardDeviationsField.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }

        if(emptiness){
            equipmentButton.setStyle(ERORRTEXTFILESTYLE);
        }

        return emptiness;
    }

    public boolean areCostumerFieldsEmpty(){
        jobOrderNoComboBox.setStyle(null);
        projectNameComboBox.setStyle(null);
        offerNoComboBox.setStyle(null);
        testPlaceField.setStyle(null);
        inspectionStandardField.setStyle(null);
        evaluationStandardField.setStyle(null);
        inspectionProcedureField.setStyle(null);
        stageOfExaminationComboBox.setStyle(null);
        surfaceConditionComboBox.setStyle(null);
        customerButton.setStyle(null);

        boolean emptiness = false;

        if(jobOrderNoComboBox.getValue() == null){
            jobOrderNoComboBox.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(projectNameComboBox.getValue() == null){
            projectNameComboBox.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(offerNoComboBox.getValue() == null){
            offerNoComboBox.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(testPlaceField.getText().equals("") || !PDFHandler.isStringLegal(testPlaceField.getText())){
            testPlaceField.setStyle(ERORRTEXTFILESTYLE);
            emptiness =true;
        }

        if(inspectionStandardField.getText().equals("")|| !PDFHandler.isStringLegal(inspectionStandardField.getText())){
            inspectionStandardField.setStyle(ERORRTEXTFILESTYLE);
            emptiness =true;
        }
        if(evaluationStandardField.getText().equals("")|| !PDFHandler.isStringLegal(evaluationStandardField.getText())){
            evaluationStandardField.setStyle(ERORRTEXTFILESTYLE);
            emptiness =true;
        }
        if(inspectionProcedureField.getText().equals("")|| !PDFHandler.isStringLegal(inspectionProcedureField.getText())){
            inspectionProcedureField.setStyle(ERORRTEXTFILESTYLE);
            emptiness =true;
        }

        if(stageOfExaminationComboBox.getValue() == null){
            stageOfExaminationComboBox.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if(surfaceConditionComboBox.getValue() == null){
            surfaceConditionComboBox.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }

        if(emptiness){
            customerButton.setStyle(ERORRTEXTFILESTYLE);
        }

        return emptiness;
    }

    public boolean isInsResTableViewEmpty(){
        inspectionResultTableView.setStyle(null);
        inspectionResultButton.setStyle(null);
        boolean emptiness = false;
        if(inspectionResultTableView.getItems().size() == 0){
            inspectionResultTableView.setStyle(ERORRTEXTFILESTYLE);
            emptiness = true;
        }
        if (emptiness){
            inspectionResultButton.setStyle(ERORRTEXTFILESTYLE);
        }
        return emptiness;
    }

    // ====== Alerts ====================================

    public static boolean cancelAlert(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Uyarı");
        alert.setHeaderText(null);
        alert.setContentText("Girdiğiniz tüm bilgiler kaydedilmeyecek. İptal etmek istediğinden emin misiniz?");
        ButtonType buttonYes = new ButtonType("Evet");
        ButtonType buttonNo = new ButtonType("Hayır");
        alert.getButtonTypes().setAll(buttonYes, buttonNo);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == buttonYes){
            return true;
        }
        return false; // if user exit without clicking anything or if user clicked cancel do nothing
    }

    private void outOfBoundaryAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("sınır dışı");
        alert.setHeaderText(null);
        alert.setContentText("14'ten fazla muayene sonuçu ekleyemezsiniz.");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(generalPane.getScene().getWindow());
        ButtonType buttonYes = new ButtonType("Tamam");
        alert.getButtonTypes().setAll(buttonYes);
        alert.showAndWait();
    }

    private void selectItemAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hiçbir şey seçilmedi");
        alert.setHeaderText(null);
        alert.setContentText("Lütfen bir satır seçin");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(generalPane.getScene().getWindow());
        ButtonType buttonYes = new ButtonType("Tamam");
        alert.getButtonTypes().setAll(buttonYes);
        alert.show();
    }

    private void cantSaveFileAlert(IOException e){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dosya kaydedilemedi");
        alert.setHeaderText(null);
        alert.setContentText(e.toString());
        ButtonType buttonYes = new ButtonType("Tamam");
        alert.getButtonTypes().setAll(buttonYes);
        alert.show();
    }

    // ======= Setters and Getters ======================


    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public String getReportNo() {
        return reportNo;
    }

    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public User getOperator() {
        return operator;
    }

    public void setOperator(User operator) {
        this.operator = operator;
    }

    public User getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(User evaluator) {
        this.evaluator = evaluator;
    }

    public User getConformer() {
        return conformer;
    }

    public void setConformer(User conformer) {
        this.conformer = conformer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
