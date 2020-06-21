/*
 * @Datei           Report.java
 * @Autor           Muhammednur Şehebi
 * @Matrikelnummer  170503112
 * @Date            6/20/2020
 */

package sample.model;

import javafx.collections.ObservableList;

import java.time.LocalDate;

public class Report {
    public static final int MAXSCOPE = 100;
    public static final int MINSCOPE = 1;
    public static final LocalDate MINDATE = LocalDate.of(2012, 1, 1);
    public static LocalDate MAXDATE = LocalDate.now();
    public static final String AC = "AC";
    public static final String DC = "DC";


    private User operator;
    private User evaluator;
    private User conformer;
    private Customer customer;
    private StageOfExamination stageOfExamination;
    private SurfaceCondition surfaceCondition;
    private Equipment equipment;
    private ObservableList<InspectionResult> inspectionResults;
    private String inspectionStandard = "";
    private String evaluationStandard = "";
    private String inspectionProcedure = "";
    private int inspectionScope = 0;
    private String drawingNo = "";
    private int numberOfPages = 0;
    private String reportNo = "";
    private LocalDate reportDate = LocalDate.now();
    private String examinationArea = "";
    private String currentType = "";
    private String luxmeter = "";
    private String testMedium = "";
    private String demagnetization = "";
    private String heatTreatment = "";
    private double surfaceTemperature = 0.0;
    private String gaussFieldStrength = "";
    private String equipmentSurfaceCondition = "";
    private String identificationOfLightEquip = "";
    private String liftingTestDateNumber = "";
    private boolean filerWeld = false;
    private boolean buttWeld = false;
    private String standardDeviations = "";
    private LocalDate inspectionDates = LocalDate.now();
    private String descriptionAndAttachments = "";
    private JobOrderNo jobOrderNo;
    private OfferNo offerNo;
    private ProjectName projectName;
    private LocalDate operatorDate;
    private LocalDate evaluatorDate;
    private LocalDate conformerDate;

    public Report(User operator, User evaluator, User conformer, Customer customer, StageOfExamination stageOfExamination, SurfaceCondition surfaceCondition, Equipment equipment, ObservableList<InspectionResult> inspectionResults, String inspectionStandard, String evaluationStandard, String inspectionProcedure, int inspectionScope, String drawingNo, int numberOfPages, String reportNo, LocalDate reportDate, String examinationArea, String currentType, String luxmeter, String testMedium, String demagnetization, String heatTreatment, double surfaceTemperature, String gaussFieldStrength, String equipmentSurfaceCondition, String identificationOfLightEquip, String liftingTestDateNumber, boolean filerWeld, boolean buttWeld, String standardDeviations, LocalDate inspectionDates, String descriptionAndAttachments, JobOrderNo jobOrderNo, OfferNo offerNo, ProjectName projectName, LocalDate operatorDate, LocalDate evaluatorDate, LocalDate conformerDate) {
        this.operator = operator;
        this.evaluator = evaluator;
        this.conformer = conformer;
        this.customer = customer;
        this.stageOfExamination = stageOfExamination;
        this.surfaceCondition = surfaceCondition;
        this.equipment = equipment;
        this.inspectionResults = inspectionResults;
        this.inspectionStandard = inspectionStandard;
        this.evaluationStandard = evaluationStandard;
        this.inspectionProcedure = inspectionProcedure;
        this.inspectionScope = inspectionScope;
        this.drawingNo = drawingNo;
        this.numberOfPages = numberOfPages;
        this.reportNo = reportNo;
        this.reportDate = reportDate;
        this.examinationArea = examinationArea;
        this.currentType = currentType;
        this.luxmeter = luxmeter;
        this.testMedium = testMedium;
        this.demagnetization = demagnetization;
        this.heatTreatment = heatTreatment;
        this.surfaceTemperature = surfaceTemperature;
        this.gaussFieldStrength = gaussFieldStrength;
        this.equipmentSurfaceCondition = equipmentSurfaceCondition;
        this.identificationOfLightEquip = identificationOfLightEquip;
        this.liftingTestDateNumber = liftingTestDateNumber;
        this.filerWeld = filerWeld;
        this.buttWeld = buttWeld;
        this.standardDeviations = standardDeviations;
        this.inspectionDates = inspectionDates;
        this.descriptionAndAttachments = descriptionAndAttachments;
        this.jobOrderNo = jobOrderNo;
        this.offerNo = offerNo;
        this.projectName = projectName;
        this.operatorDate = operatorDate;
        this.evaluatorDate = evaluatorDate;
        this.conformerDate = conformerDate;
    }

    public Report() {
    }



    public static int getMAXSCOPE() {
        return MAXSCOPE;
    }

    public static int getMINSCOPE() {
        return MINSCOPE;
    }

    public static String getAC() {
        return AC;
    }

    public static String getDC() {
        return DC;
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

    public StageOfExamination getStageOfExamination() {
        return stageOfExamination;
    }

    public void setStageOfExamination(StageOfExamination stageOfExamination) {
        this.stageOfExamination = stageOfExamination;
    }

    public SurfaceCondition getSurfaceCondition() {
        return surfaceCondition;
    }

    public void setSurfaceCondition(SurfaceCondition surfaceCondition) {
        this.surfaceCondition = surfaceCondition;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public ObservableList<InspectionResult> getInspectionResults() {
        return inspectionResults;
    }

    public void setInspectionResults(ObservableList<InspectionResult> inspectionResults) {
        this.inspectionResults = inspectionResults;
    }

    public String getInspectionStandard() {
        return inspectionStandard;
    }

    public void setInspectionStandard(String inspectionStandard) {
        this.inspectionStandard = inspectionStandard;
    }

    public String getEvaluationStandard() {
        return evaluationStandard;
    }

    public void setEvaluationStandard(String evaluationStandard) {
        this.evaluationStandard = evaluationStandard;
    }

    public String getInspectionProcedure() {
        return inspectionProcedure;
    }

    public void setInspectionProcedure(String inspectionProcedure) {
        this.inspectionProcedure = inspectionProcedure;
    }

    public int getInspectionScope() {
        return inspectionScope;
    }

    public void setInspectionScope(int inspectionScope) {
        this.inspectionScope = inspectionScope;
    }

    public String getDrawingNo() {
        return drawingNo;
    }

    public void setDrawingNo(String drawingNo) {
        this.drawingNo = drawingNo;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
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

    public String getExaminationArea() {
        return examinationArea;
    }

    public void setExaminationArea(String examinationArea) {
        this.examinationArea = examinationArea;
    }

    public String getCurrentType() {
        return currentType;
    }

    public void setCurrentType(String currentType) {
        this.currentType = currentType;
    }

    public String getLuxmeter() {
        return luxmeter;
    }

    public void setLuxmeter(String luxmeter) {
        this.luxmeter = luxmeter;
    }

    public String getTestMedium() {
        return testMedium;
    }

    public void setTestMedium(String testMedium) {
        this.testMedium = testMedium;
    }

    public String getDemagnetization() {
        return demagnetization;
    }

    public void setDemagnetization(String demagnetization) {
        this.demagnetization = demagnetization;
    }

    public String getHeatTreatment() {
        return heatTreatment;
    }

    public void setHeatTreatment(String heatTreatment) {
        this.heatTreatment = heatTreatment;
    }

    public double getSurfaceTemperature() {
        return surfaceTemperature;
    }

    public void setSurfaceTemperature(double surfaceTemperature) {
        this.surfaceTemperature = surfaceTemperature;
    }

    public String getGaussFieldStrength() {
        return gaussFieldStrength;
    }

    public void setGaussFieldStrength(String gaussFieldStrength) {
        this.gaussFieldStrength = gaussFieldStrength;
    }

    public String getEquipmentSurfaceCondition() {
        return equipmentSurfaceCondition;
    }

    public void setEquipmentSurfaceCondition(String equipmentSurfaceCondition) {
        this.equipmentSurfaceCondition = equipmentSurfaceCondition;
    }

    public String getIdentificationOfLightEquip() {
        return identificationOfLightEquip;
    }

    public void setIdentificationOfLightEquip(String identificationOfLightEquip) {
        this.identificationOfLightEquip = identificationOfLightEquip;
    }

    public String getLiftingTestDateNumber() {
        return liftingTestDateNumber;
    }

    public void setLiftingTestDateNumber(String liftingTestDateNumber) {
        this.liftingTestDateNumber = liftingTestDateNumber;
    }

    public boolean isFilerWeld() {
        return filerWeld;
    }

    public void setFilerWeld(boolean filerWeld) {
        this.filerWeld = filerWeld;
    }

    public boolean isButtWeld() {
        return buttWeld;
    }

    public void setButtWeld(boolean buttWeld) {
        this.buttWeld = buttWeld;
    }

    public String getStandardDeviations() {
        return standardDeviations;
    }

    public void setStandardDeviations(String standardDeviations) {
        this.standardDeviations = standardDeviations;
    }

    public LocalDate getInspectionDates() {
        return inspectionDates;
    }

    public void setInspectionDates(LocalDate inspectionDates) {
        this.inspectionDates = inspectionDates;
    }

    public String getDescriptionAndAttachments() {
        return descriptionAndAttachments;
    }

    public void setDescriptionAndAttachments(String descriptionAndAttachments) {
        this.descriptionAndAttachments = descriptionAndAttachments;
    }

    public JobOrderNo getJobOrderNo() {
        return jobOrderNo;
    }

    public void setJobOrderNo(JobOrderNo jobOrderNo) {
        this.jobOrderNo = jobOrderNo;
    }

    public OfferNo getOfferNo() {
        return offerNo;
    }

    public void setOfferNo(OfferNo offerNo) {
        this.offerNo = offerNo;
    }

    public ProjectName getProjectName() {
        return projectName;
    }

    public void setProjectName(ProjectName projectName) {
        this.projectName = projectName;
    }

    public LocalDate getOperatorDate() {
        return operatorDate;
    }

    public void setOperatorDate(LocalDate operatorDate) {
        this.operatorDate = operatorDate;
    }

    public LocalDate getEvaluatorDate() {
        return evaluatorDate;
    }

    public void setEvaluatorDate(LocalDate evaluatorDate) {
        this.evaluatorDate = evaluatorDate;
    }

    public LocalDate getConformerDate() {
        return conformerDate;
    }

    public void setConformerDate(LocalDate conformerDate) {
        this.conformerDate = conformerDate;
    }
}
