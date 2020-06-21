/*
 * @Datei           InspectionResult.java
 * @Autor           Muhammednur Şehebi
 * @Matrikelnummer  170503112
 * @Date            6/20/2020
 */

package sample.model;

public class InspectionResult {
    public static final String OK = "OK";
    public static final String RED = "RED";
    public static final int MAXSERIALNO = 14;
    public static final int MINSERIALNO = 1;
    private int serialNo = 1;
    private String weldPieceNo= "";
    private double testLength = 0.0;
    private String weldingProcess = "";
    private double thickness = 0.0;
    private String diameter = "";
    private String defectType = "";
    private String defectLoc = "";
    private String result = "";

    public InspectionResult(int serialNo, String weldPieceNo,
                            double testLength, String weldingProcess,
                            double thickness, String diameter,
                            String defectType, String defectLoc, String result) {
        this.serialNo = serialNo;
        this.weldPieceNo = weldPieceNo;
        this.testLength = testLength;
        this.weldingProcess = weldingProcess;
        this.thickness = thickness;
        this.diameter = diameter;
        this.defectType = defectType;
        this.defectLoc = defectLoc;
        this.result = result;
    }

    public String getOK() {
        return OK;
    }

    public String getRED() {
        return RED;
    }

    public int getMAXSERIALNO() {
        return MAXSERIALNO;
    }

    public int getMINSERIALNO() {
        return MINSERIALNO;
    }

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public String getWeldPieceNo() {
        return weldPieceNo;
    }

    public void setWeldPieceNo(String weldPieceNo) {
        this.weldPieceNo = weldPieceNo;
    }

    public double getTestLength() {
        return testLength;
    }

    public void setTestLength(double testLength) {
        this.testLength = testLength;
    }

    public String getWeldingProcess() {
        return weldingProcess;
    }

    public void setWeldingProcess(String weldingProcess) {
        this.weldingProcess = weldingProcess;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getDefectType() {
        return defectType;
    }

    public void setDefectType(String defectType) {
        this.defectType = defectType;
    }

    public String getDefectLoc() {
        return defectLoc;
    }

    public void setDefectLoc(String defectLoc) {
        this.defectLoc = defectLoc;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
